package xyz.leezoom.cloudmemo.addmemo

import android.app.Activity
import android.graphics.Color
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.content.edit
import com.avos.avoscloud.AVUser
import com.mikepenz.community_material_typeface_library.CommunityMaterial
import com.mikepenz.iconics.IconicsDrawable
import kotlinx.android.synthetic.main.activity_edit_add.*
import org.jetbrains.anko.defaultSharedPreferences
import xyz.leezoom.androidutilcode.ui.ABaseActivity
import xyz.leezoom.androidutilcode.util.LogUtil
import xyz.leezoom.androidutilcode.util.toast
import xyz.leezoom.cloudmemo.App
import xyz.leezoom.cloudmemo.R
import xyz.leezoom.cloudmemo.bean.Memo

class EditAddActivity : ABaseActivity(), EditAddView {

  private val TAG = "EditAddActivity"

  override val layoutId: Int
    get() = R.layout.activity_edit_add
  override val toolbarId: Int
    get() = R.id.tool_bar

  private lateinit var presenter: EditAddPresenter
  private var memo: Memo? = null

  private var lockStatus = false
  private var visibility = true

  override fun onSave(status: Boolean) {
    if (status) {
      setResult(Activity.RESULT_OK)
      this.finish()
    } else {
      defaultSharedPreferences.edit {
        putString("lost_data", ad_content_edit.text.toString())
      }
      Toast.makeText(this@EditAddActivity, "Sorry, can't save memo", Toast.LENGTH_SHORT).show()
      setResult(Activity.RESULT_CANCELED)
      finish()
    }
  }

  override fun onLoad(memo: Memo) {
    ad_content_edit.setText(memo.description)
  }

  private fun checkLoad(): Memo? {
    return intent.getParcelableExtra("memo") as Memo?
  }

  override fun initData() {
    LogUtil.d(TAG, "initData")
    presenter = EditAddPresenterImpl(this, this)
    memo = checkLoad()
    if (memo != null) {
      presenter.loadMemo(memo!!)
      lockStatus = memo!!.isLocked
      visibility = memo!!.visibility
    }
  }

  override fun initView() {
    supportActionBar!!.title = "Edit"
    ad_fb.setImageDrawable(IconicsDrawable(this)
            .icon(CommunityMaterial.Icon.cmd_send)
            .color(Color.WHITE)
            .sizeDp(24))

    ad_fb.setOnClickListener {
      LogUtil.d(TAG, "click: ")
      doSaveOrUp()
    }

    if (memo != null && memo!!.isLocked) {
      ad_content_edit.isFocusable = false
      ad_content_edit.isFocusableInTouchMode = false
    } else {
      ad_content_edit.isFocusableInTouchMode = true
      ad_content_edit.isFocusable = true
      ad_fb.isClickable = true
    }
  }

  private fun doSaveOrUp() {
    val content = ad_content_edit.text.toString()
    val memo: Memo?
    memo = if (App.currentPage == AVUser.getCurrentUser().objectId) {
      Memo(content, AVUser.getCurrentUser().objectId, lockStatus, visibility, AVUser.getCurrentUser())
    } else {
      Memo(content, App.currentPage, lockStatus, visibility, AVUser.getCurrentUser())
    }
    LogUtil.d(TAG, "Try save: ")
    if (content.isNotBlank()) {
      if (this.memo == null) {
        LogUtil.d("Save", memo.description)
        presenter.save(memo)
      } else {
        if (this.memo!!.description != content
                || this.memo!!.isLocked != lockStatus
                || this.memo!!.visibility != visibility) {
          this.memo!!.description = content
          this.memo!!.isLocked = lockStatus
          this.memo!!.visibility = visibility

          LogUtil.w("Update", "${memo.visibility}")
          presenter.update(this.memo!!)
        } else {
          LogUtil.d(TAG, "Nothing changed")
        }
      }
    } else {
      toast(R.string.toast_empty_content)
    }
  }

  override fun onBackPressed() {
    doSaveOrUp()
    super.onBackPressed()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    LogUtil.d(TAG, "onCreateOptionsMenu")
    menuInflater.inflate(R.menu.editor_menu, menu)
    switchIcon(menu!!.getItem(0))
    switchIcon(menu.getItem(1))
    return true
  }

  private fun switchIcon(item: MenuItem) {

    when(item.itemId) {
      R.id.menu_editor_lock -> {
        if (lockStatus) {
          item.icon = IconicsDrawable(this)
                  .icon(CommunityMaterial.Icon.cmd_lock_outline)
                  .color(Color.WHITE)
                  .sizeDp(24)
          //item.icon = ContextCompat.getDrawable(this, R.drawable.ic_lock_outline_black_24dp)
        } else {
          item.icon = IconicsDrawable(this)
                  .icon(CommunityMaterial.Icon.cmd_lock_open)
                  .color(Color.WHITE)
                  .sizeDp(24)
          //item.icon = ContextCompat.getDrawable(this, R.drawable.ic_lock_open_black_24dp)
        }
      }

      R.id.menu_editor_visibility -> {
        if (visibility) {
          item.icon = IconicsDrawable(this)
                  .icon(CommunityMaterial.Icon.cmd_eye)
                  .color(Color.WHITE)
                  .sizeDp(24)
        //item.icon = ContextCompat.getDrawable(this, R.drawable.ic_visibility_black_24dp)
        } else {
          item.icon = IconicsDrawable(this)
                  .icon(CommunityMaterial.Icon.cmd_eye_off)
                  .color(Color.WHITE)
                  .sizeDp(24)
        //item.icon = ContextCompat.getDrawable(this, R.drawable.ic_visibility_off_black_24dp)
        }
      }
    }
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when(item!!.itemId) {
      R.id.menu_editor_lock -> {
        if (memo != null) {
          if (memo!!.user.objectId != AVUser.getCurrentUser().objectId) {
            toast(R.string.toast_no_permission)
            return false
          }
        }
        lockStatus = !lockStatus
        LogUtil.d(TAG, "lockStatus: $lockStatus")
        if (lockStatus) {
          ad_content_edit.isFocusable = false
          ad_content_edit.isFocusableInTouchMode = false
        } else {
          ad_content_edit.isFocusable = true
          ad_content_edit.isFocusableInTouchMode = true
        }

        switchIcon(item)
        return true
      }
      R.id.menu_editor_visibility -> {
        if (memo != null) {
          if (memo!!.user.objectId != AVUser.getCurrentUser().objectId) {
            toast(R.string.toast_no_permission)
            return false
          }
        }
        visibility = !visibility
        LogUtil.d(TAG, "visibility: $visibility")
        switchIcon(item)
        return true
      }
      else -> return false
    }
  }

}
