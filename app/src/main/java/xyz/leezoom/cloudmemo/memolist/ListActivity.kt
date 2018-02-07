package xyz.leezoom.cloudmemo.memolist

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.content.edit
import com.avos.avoscloud.AVUser
import com.mikepenz.community_material_typeface_library.CommunityMaterial
import com.mikepenz.iconics.IconicsDrawable
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import xyz.leezoom.androidcode.adapter.KTTextAdapter
import xyz.leezoom.androidutilcode.ui.ABaseActivity
import xyz.leezoom.androidutilcode.util.toast
import xyz.leezoom.cloudmemo.AboutActivity
import xyz.leezoom.cloudmemo.App
import xyz.leezoom.cloudmemo.R
import xyz.leezoom.cloudmemo.addmemo.EditAddActivity
import xyz.leezoom.cloudmemo.bean.Memo
import xyz.leezoom.cloudmemo.bean.MemoAdapter
import xyz.leezoom.cloudmemo.login.LoginActivity

class ListActivity : ABaseActivity(), ListView {

  private val TAG = "ListActivity"

  override val layoutId: Int
    get() = R.layout.activity_main

  override val toolbarId: Int
    get() = R.id.tool_bar

  private lateinit var memoAdapter: MemoAdapter
  private lateinit var memoList: ArrayList<Memo>
  private lateinit var presenter: ListPresenter
  private val REFRESH_CODE = 1

  override fun onRefresh(status: Boolean, list: ArrayList<Memo>) {
    if (status) {
      memoList.clear()
      memoList.addAll(list)
      memoAdapter.notifyDataSetChanged()
    }
    setToolbarTitle()
    refresh_layout.isRefreshing = false
  }

  override fun onAdd() {}

  override fun onDelete(status: Boolean, pos: Int) {
    if (status) {
      //not refresh from net
      Toast.makeText(this, "deleted", Toast.LENGTH_SHORT).show()
      memoList.removeAt(pos)
      memoAdapter.notifyDataSetChanged()
    }
  }

  //first time load
  override fun onLoad(status: Boolean, list: ArrayList<Memo>) {
    if (status) {
      memoList.addAll(list)
      memoAdapter.notifyDataSetChanged()
    }
    refresh_layout.isRefreshing = false
    setToolbarTitle()
  }

  override fun onError(message: String?) {
    refresh_layout.isRefreshing = false
    if (!message.isNullOrBlank()) {
      Toast.makeText(this, "refresh error, $message", Toast.LENGTH_SHORT).show()
    }
  }

  override fun initData() {
    presenter = ListPresenterImpl(this, this)
    memoList = ArrayList()
    memoAdapter = MemoAdapter(this, memoList)
    refresh_layout.isRefreshing = true
    App.setCurrentPage(defaultSharedPreferences.getString("CurrentPage", AVUser.getCurrentUser().username))
    Log.d(TAG, "Init Current page: ${App.getCurrentPage()}")
    presenter.loadAll(App.getCurrentPage())

  }

  private fun showPages() {
      val pageList = defaultSharedPreferences.getStringSet("pages", emptySet()).toCollection(ArrayList())
      Log.d(TAG, "pageList: $pageList")
      alert {
        title = "Pages"
        customView {
          listView {
            this.layoutParams = ViewGroup.LayoutParams(this.measuredWidth, this.measuredHeight).apply {
              setPadding(16, 8, 16, 16)
            }
            adapter = KTTextAdapter(this@ListActivity, pageList)
            (adapter as KTTextAdapter).notifyDataSetChanged()

            setOnItemClickListener { parent, view, position, id ->
              defaultSharedPreferences.edit {
                if (pageList[position] != App.getCurrentPage()) {
                  App.setCurrentPage(pageList[position])
                  putString("CurrentPage", pageList[position])
                  presenter.refresh(pageList[position])
                }
              }
            }
          }
        }
      }.show()
  }

  private fun setToolbarTitle() {
    toolbar!!.title = if (App.getCurrentPage() != AVUser.getCurrentUser().objectId) {
      App.getCurrentPage()
    } else {
      AVUser.getCurrentUser().username
    }
  }

  override fun initView() {
    setToolbarTitle()
    toolbar!!.setOnClickListener {
      showPages()
    }

    list_view.adapter = memoAdapter
    list_view.setOnItemClickListener { parent, view, position, id ->
      Log.d("Click", "$position")
      val intent = Intent(this@ListActivity, EditAddActivity::class.java)
      intent.putExtra("memo", memoList[position])
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val pair = Pair<View, String>(view.findViewById(R.id.mm_content), getString(R.string.transition_content))
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair)
        startActivityForResult(intent, REFRESH_CODE, options.toBundle())
      } else {
        startActivityForResult(intent, REFRESH_CODE)
      }
    }

    /*
    list_view.onItemLongClickListener = AdapterView.OnItemLongClickListener { parent, view, position, id ->

      Toast.makeText(this, "delete $position, ${memoList[position].description}", Toast.LENGTH_SHORT).show()
      presenter.deleteMemo(memoList[position].objectId, position)
      true
    }*/
    main_fb.setImageDrawable(IconicsDrawable(this)
            .icon(CommunityMaterial.Icon.cmd_plus_one)
            .color(Color.WHITE)
            .sizeDp(24))
    main_fb.setOnClickListener {
      startActivityForResult(Intent(this@ListActivity, EditAddActivity::class.java), REFRESH_CODE)
    }
    refresh_layout.setOnRefreshListener {
      Log.d(TAG, "currentPage: ${App.getCurrentPage()}")
      presenter.refresh(App.getCurrentPage())
    }

  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    when (requestCode) {
      REFRESH_CODE -> {
        if (resultCode == Activity.RESULT_OK) {
          Log.d(TAG, "onActivityResult: REFRESH")
          presenter.refresh(App.getCurrentPage())
        }
      }
      else -> toast("Canceled")
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when(item!!.itemId) {
      R.id.menu_out -> {
        AVUser.logOut()
        defaultSharedPreferences.edit {
          putString("CurrentPage", "")
          putStringSet("page", emptySet())
        }
        startActivity(Intent(this, LoginActivity::class.java))
        this.finish()
        return true
      }

      R.id.menu_about -> {
        startActivity<AboutActivity>()
        return true
      }

      R.id.menu_page -> {
        alert ("Add a page"){
          var page = ""
          val pageSet = defaultSharedPreferences.getStringSet("pages", emptySet())
          var editView: EditText? = null

          customView {
            editView = editText()
          }

          yesButton {
            page = editView!!.text.toString()
            Log.d(TAG, "page: $page")
            if (page.isNotBlank() && editView != null) {
              defaultSharedPreferences.edit {
                pageSet.add(page)
                Log.d(TAG, "add page: $pageSet")
                putStringSet("pages", pageSet)
                putString("CurrentPage", page)
              }
              App.setCurrentPage(page)
              presenter.refresh(page)
            }
          }
          cancelButton {  }
        }.show()
        return true
      }

      else -> return super.onOptionsItemSelected(item)
    }
  }

}
