package xyz.leezoom.cloudmemo.addmemo

import android.content.Context
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVObject
import com.avos.avoscloud.SaveCallback
import xyz.leezoom.cloudmemo.bean.Memo
import xyz.leezoom.cloudmemo.bean.MemoL

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

  override fun update(id: String, memoL: MemoL) {
    //update by id
    val memo = AVObject.createWithoutData(Memo::class.java, id)
    memo.title = memoL.title
    memo.description = memoL.description
    memo.words = memoL.words
    save(memo)
  }

  override fun loadMemo(memo: Memo) {
    editAddView.onLoad(memo)
  }
}