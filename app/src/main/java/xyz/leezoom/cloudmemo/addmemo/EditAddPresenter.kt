package xyz.leezoom.cloudmemo.addmemo

import xyz.leezoom.cloudmemo.bean.Memo

interface EditAddPresenter {

  fun init()

  fun save(memo: Memo)

  // TODO: 21/01/18  update memo
  fun update()

  fun loadMemo(memo: Memo)

}