package xyz.leezoom.cloudmemo.addmemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.avos.avoscloud.AVUser
import kotlinx.android.synthetic.main.activity_edit_add.*
import xyz.leezoom.cloudmemo.R
import xyz.leezoom.cloudmemo.bean.Memo

class EditAddActivity : AppCompatActivity(), EditAddView {

  private lateinit var presenter: EditAddPresenter
  private var memo: Memo? = null

  override fun onSave(status: Boolean) {
    if (status) {
      Toast.makeText(this@EditAddActivity, "Saved", Toast.LENGTH_SHORT).show()
      this.finish()
    } else {
      Toast.makeText(this@EditAddActivity, "Sorry, can't save memo", Toast.LENGTH_SHORT).show()
    }
  }

  override fun onLoad(memo: Memo) {
    ad_title_edit.setText(memo.title)
    ad_content_edit.setText(memo.description)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_edit_add)
    title = "Edit"
    initData()

    memo = checkLoad()
    if (memo != null) {
      presenter.loadMemo(memo!!)
    }
    initView(memo == null)
  }

  private fun checkLoad(): Memo? {
    return intent.getParcelableExtra("memo") as Memo?
  }

  private fun initData() {
    presenter = EditAddPresenterImpl(this, this)
  }

  private fun initView(status: Boolean) {
    ad_fb.setOnClickListener {
      val title = ad_title_edit.text.toString()
      val content = ad_content_edit.text.toString()
      val words = content.length
      val memo = Memo(title, content, words, AVUser.getCurrentUser())
      if (title.isNotEmpty()) {
        if (status) {
          Log.w("Save", memo.title)
          presenter.save(memo)
        } else {
          Log.w("Update", memo.title)
          presenter.update(this.memo!!.objectId, memo.toLocal())
        }
      } else {
        Toast.makeText(this@EditAddActivity, "Title is empty", Toast.LENGTH_SHORT).show()
      }
    }
  }
}
