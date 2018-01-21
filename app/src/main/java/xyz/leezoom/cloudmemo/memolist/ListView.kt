package xyz.leezoom.cloudmemo.memolist

import xyz.leezoom.cloudmemo.bean.Memo

interface ListView {
  fun onRefresh(status: Boolean, list: ArrayList<Memo>)

  fun onDelete(status: Boolean, pos: Int)

  fun onAdd()

  fun onLoad(status: Boolean, list: ArrayList<Memo>)

  fun onError(message: String?)
}