package xyz.leezoom.cloudmemo.memolist

import android.content.Context
import com.avos.avoscloud.*
import xyz.leezoom.androidutilcode.util.LogUtil
import xyz.leezoom.cloudmemo.bean.Memo

class ListPresenterImpl (private val context: Context,
                         private val listView: ListView) : ListPresenter {

  private val TAG = "ListPresenterImpl"

  override fun init() {}

  override fun refresh(page: String, order: Boolean) {
    val query: AVQuery<Memo> = AVObject.getQuery(Memo::class.java)
    val query1: AVQuery<Memo> = AVQuery.getQuery(Memo::class.java)
    query.cachePolicy = AVQuery.CachePolicy.NETWORK_ELSE_CACHE
    query1.cachePolicy = AVQuery.CachePolicy.NETWORK_ELSE_CACHE
    query.maxCacheAge = 3600 * 1000 * 24 * 2
    query1.maxCacheAge = 3600 * 1000 * 24 * 2
    if (order) {
      //older date is at last
      query.orderByDescending("updatedAt")
      query1.orderByDescending("updatedAt")
    } else {
      query.orderByAscending("updatedAt")
      query1.orderByAscending("updatedAt")
    }

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

      if (order) {
        orQuery.orderByDescending("updatedAt")
      } else {
        orQuery.orderByAscending("updatedAt")
      }

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

  override fun loadAll(page: String, order: Boolean) {
    val query: AVQuery<Memo> = AVObject.getQuery(Memo::class.java)
    val query1: AVQuery<Memo> = AVQuery.getQuery(Memo::class.java)
    query.cachePolicy = AVQuery.CachePolicy.NETWORK_ELSE_CACHE
    query1.cachePolicy = AVQuery.CachePolicy.NETWORK_ELSE_CACHE
    query.maxCacheAge = 3600 * 1000 * 24 * 2
    query1.maxCacheAge = 3600 * 1000 * 24 * 2
    if (order) {
      //older date is at last
      query.orderByDescending("updatedAt")
      query1.orderByDescending("updatedAt")
    } else {
      query.orderByAscending("updatedAt")
      query1.orderByAscending("updatedAt")
    }

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

      if (order) {
        orQuery.orderByDescending("updatedAt")
      } else {
        orQuery.orderByAscending("updatedAt")
      }

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