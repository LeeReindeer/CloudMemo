package xyz.leezoom.cloudmemo.addmemo

import android.content.Context
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVObject
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

  override fun update(memo: Memo) {
    //update by id
    val aMemo = AVObject.createWithoutData(Memo::class.java, memo.objectId)
    aMemo.description = memo.description
    aMemo.isLocked = memo.isLocked
    aMemo.visibility = memo.visibility
    save(aMemo)
  }

  override fun loadMemo(memo: Memo) {
    editAddView.onLoad(memo)
  }
}