package xyz.leezoom.cloudmemo.addmemo

import android.content.Context
import com.avos.avoscloud.AVException
import com.avos.avoscloud.SaveCallback
import xyz.leezoom.cloudmemo.bean.Memo

class EditAddPresenterImpl(private val context: Context,
                           private val editAddView: EditAddView) : EditAddPresenter {


  override fun init() {}

  override fun save(memo: Memo) {
    memo.saveInBackground(object : SaveCallback() {
      override fun done(e: AVException?) {
        editAddView.onSave(e == null)
      }
    })
  }

  override fun update() {
    //TODO("not implemented")
  }

  override fun loadMemo(memo: Memo) {
    editAddView.onLoad(memo)
  }
}