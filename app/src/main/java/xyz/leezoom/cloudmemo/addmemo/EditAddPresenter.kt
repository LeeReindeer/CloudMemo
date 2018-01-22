package xyz.leezoom.cloudmemo.addmemo

import xyz.leezoom.cloudmemo.bean.Memo
import xyz.leezoom.cloudmemo.bean.MemoL

interface EditAddPresenter {

  fun init()

  fun save(memo: Memo)

  fun update(id: String, memoL: MemoL)

  fun loadMemo(memo: Memo)

}