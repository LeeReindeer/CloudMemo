package xyz.leezoom.cloudmemo.addmemo

import xyz.leezoom.cloudmemo.bean.Memo

interface EditAddPresenter {

  fun init()

  fun save(memo: Memo)

  fun update(memo: Memo)

  fun loadMemo(memo: Memo)

}