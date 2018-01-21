package xyz.leezoom.cloudmemo.memolist

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.avos.avoscloud.*
import kotlinx.android.synthetic.main.activity_main.*
import xyz.leezoom.cloudmemo.R
import xyz.leezoom.cloudmemo.bean.Memo
import xyz.leezoom.cloudmemo.bean.MemoAdapter
import xyz.leezoom.cloudmemo.addmemo.EditAddActivity
import xyz.leezoom.cloudmemo.login.LoginActivity

class ListActivity : AppCompatActivity(), ListView {

  private lateinit var memoAdapter: MemoAdapter
  private lateinit var memoList: ArrayList<Memo>
  private lateinit var presenter: ListPresenter

  override fun onRefresh(status: Boolean, list: ArrayList<Memo>) {
    if (status) {
      memoList.clear()
      memoList.addAll(list)
      memoAdapter.notifyDataSetChanged()
    }
    refresh_layout.isRefreshing = false
  }

  override fun onAdd() {
    //TODO("not implemented")
  }

  override fun onDelete(status: Boolean, pos: Int) {
    //TODO("not implemented")
  }

  //first time load
  override fun onLoad(status: Boolean, list: ArrayList<Memo>) {
    if (status) {
      memoList.addAll(list)
      memoAdapter.notifyDataSetChanged()
    }
    refresh_layout.isRefreshing = false
  }

  override fun onError(msg: String?) {
    refresh_layout.isRefreshing = false
    if (!TextUtils.isEmpty(msg)) {
      Toast.makeText(this, "refresh error, $msg", Toast.LENGTH_SHORT).show()
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    initData()
    initView()
    //testCloud()
  }


  private fun initView() {
    list_view.adapter = memoAdapter
    main_fb.setOnClickListener {
      startActivityForResult(Intent(this@ListActivity, EditAddActivity::class.java), 1)
    }
    refresh_layout.setOnRefreshListener {
      presenter.refresh()
    }
  }

  private fun initData() {
    presenter = ListPresenterImpl(this, this)
    memoList = ArrayList()
    memoAdapter = MemoAdapter(this, R.layout.memo_item_layout, memoList)
    refresh_layout.isRefreshing = true
    presenter.loadAll()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == 1) {
      if (resultCode == Activity.RESULT_OK) {
        presenter.refresh()
      } else {

      }
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
        startActivity(Intent(this, LoginActivity::class.java))
        this.finish()
        return true
      }

      R.id.menu_about -> {
        return true
      }

      else -> return super.onOptionsItemSelected(item)
    }
  }

  @Deprecated("test")
  private fun testCloud() {
    val testClass = AVObject("TestObject")
    testClass.put("words", "Hello world again again!")
    testClass.saveInBackground(object : SaveCallback() {
      override fun done(e: AVException?) {
        if (e == null) {
          Toast.makeText(this@ListActivity, "succeed", Toast.LENGTH_SHORT).show()
          Log.d("Saved", "succeed!")
          Log.d("id", testClass.objectId)
        }
      }
    })
  }
}
