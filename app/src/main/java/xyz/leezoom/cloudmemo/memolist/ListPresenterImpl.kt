package xyz.leezoom.cloudmemo.memolist

import android.content.Context
import com.avos.avoscloud.*
import xyz.leezoom.cloudmemo.bean.Memo

class ListPresenterImpl (private val context: Context,
                         private val listView: ListView) : ListPresenter {


  override fun init() {}

  override fun refresh() {
    val query: AVQuery<Memo> = AVObject.getQuery(Memo::class.java)
    query.whereEqualTo("user", AVUser.getCurrentUser())
    query.findInBackground(object : FindCallback<Memo>() {
      override fun done(list: List<Memo>?, e: AVException?) {
        if (e == null && list != null) {
          val aList :ArrayList<Memo> = ArrayList()
          aList.addAll(list)
          listView.onRefresh(true, aList)
        } else {
          e!!.printStackTrace()
          listView.onError(e!!.message)
        }
      }
    })
  }

  override fun loadAll() {
    val query: AVQuery<Memo> = AVObject.getQuery(Memo::class.java)
    query.whereEqualTo("user", AVUser.getCurrentUser())
    query.findInBackground(object : FindCallback<Memo>() {
      override fun done(list: List<Memo>?, e: AVException?) {
       if (e == null && list != null) {
         val aList :ArrayList<Memo> = ArrayList()
         aList.addAll(list)
         listView.onLoad(true, aList)
       } else {
         e!!.printStackTrace()
         listView.onError(e!!.message)
       }
      }
    })
  }

  override fun deleteMemo(id: String, position: Int) {
    val memo = AVObject.createWithoutData(Memo::class.java, id)
    memo.deleteInBackground(object : DeleteCallback() {
      override fun done(e: AVException?) {
        listView.onDelete(e == null, position)
      }
    })
  }

  override fun addMemo() {
    //TODO("not implemented")
    //startActivity(context, Intent(context, EditAddActivity::class.java), null)
  }

}