package xyz.leezoom.cloudmemo.memolist

import android.content.Context
import com.avos.avoscloud.*
import xyz.leezoom.cloudmemo.bean.Memo
import xyz.leezoom.androidutilcode.util.LogUtil

class ListPresenterImpl (private val context: Context,
                         private val listView: ListView) : ListPresenter {

  private val TAG = "ListPresenterImpl"

  override fun init() {}

  override fun refresh(page: String) {
    val query: AVQuery<Memo> = AVObject.getQuery(Memo::class.java)
    val query1: AVQuery<Memo> = AVQuery.getQuery(Memo::class.java)
    query.cachePolicy = AVQuery.CachePolicy.NETWORK_ELSE_CACHE
    query1.cachePolicy = AVQuery.CachePolicy.NETWORK_ELSE_CACHE
    query.maxCacheAge = 3600 * 1000 * 24 * 2
    query1.maxCacheAge = 3600 * 1000 * 24 * 2
    query.orderByDescending("createdAt")
    query1.orderByDescending("createdAt")

    if (page == AVUser.getCurrentUser().objectId) {
      query.whereEqualTo("user", AVUser.getCurrentUser()).whereEqualTo("page", AVUser.getCurrentUser().objectId)
      query.findInBackground(object : FindCallback<Memo>() {
        override fun done(list: List<Memo>?, e: AVException?) {
          LogUtil.d(TAG, "Single Query: refresh")
          if (e == null && list != null) {
            val aList :ArrayList<Memo> = ArrayList()
            aList.addAll(list)
            listView.onRefresh(true, aList)
          } else {
            e!!.printStackTrace()
            listView.onError(e.message)
          }
        }
      })
    } else {
      query.whereEqualTo("page", page).whereEqualTo("visibility", true)
      query1.whereEqualTo("page", page).whereEqualTo("user", AVUser.getCurrentUser())
      val orQuery = AVQuery.or(listOf(query, query1))
      orQuery.maxCacheAge = 3600 * 1000 * 24 * 2
      orQuery.orderByDescending("createdAt")
      orQuery.findInBackground(object : FindCallback<Memo>(){
        override fun done(list: List<Memo>?, e: AVException?) {
          LogUtil.d(TAG, "Or Query: refresh")
          if (e == null && list != null) {
            val aList :ArrayList<Memo> = ArrayList()
            aList.addAll(list)
            listView.onRefresh(true, aList)
          } else {
            e!!.printStackTrace()
            listView.onError(e.message)
          }
          return
        }
      })
    }
  }

  override fun loadAll(page: String) {
    val query: AVQuery<Memo> = AVObject.getQuery(Memo::class.java)
    val query1: AVQuery<Memo> = AVQuery.getQuery(Memo::class.java)
    query.cachePolicy = AVQuery.CachePolicy.NETWORK_ELSE_CACHE
    query1.cachePolicy = AVQuery.CachePolicy.NETWORK_ELSE_CACHE
    query.maxCacheAge = 3600 * 1000 * 24 * 2
    query1.maxCacheAge = 3600 * 1000 * 24 * 2
    query.orderByDescending("createdAt")
    query1.orderByDescending("createdAt")

    if (page == AVUser.getCurrentUser().objectId) {
      query.whereEqualTo("user", AVUser.getCurrentUser()).whereEqualTo("page", AVUser.getCurrentUser().objectId)
      query.findInBackground(object : FindCallback<Memo>() {
        override fun done(list: List<Memo>?, e: AVException?) {
          LogUtil.d(TAG, "Single Query: load")
          if (e == null && list != null) {
            val aList :ArrayList<Memo> = ArrayList()
            aList.addAll(list)
            listView.onLoad(true, aList)
          } else {
            e!!.printStackTrace()
            listView.onError(e.message)
          }
        }
      })
    } else {
      query.whereEqualTo("page", page).whereEqualTo("visibility", true)
      query1.whereEqualTo("page", page).whereEqualTo("user", AVUser.getCurrentUser())
      val orQuery = AVQuery.or(listOf(query, query1))
      orQuery.maxCacheAge = 3600 * 1000 * 24 * 2
      orQuery.orderByDescending("createdAt")
      orQuery.findInBackground(object : FindCallback<Memo>(){
        override fun done(list: List<Memo>?, e: AVException?) {
          LogUtil.d(TAG, "Or Query: load")
          if (e == null && list != null) {
            val aList :ArrayList<Memo> = ArrayList()
            aList.addAll(list)
            listView.onLoad(true, aList)

          } else {
            e!!.printStackTrace()
            listView.onError(e.message)
          }
          return
        }
      })
    }
  }

  override fun deleteMemo(id: String, position: Int) {
    val memo = AVObject.createWithoutData(Memo::class.java, id)
    memo.deleteInBackground(object : DeleteCallback() {
      override fun done(e: AVException?) {
        listView.onDelete(e == null, position)
      }
    })
  }

  override fun addMemo() {}

}