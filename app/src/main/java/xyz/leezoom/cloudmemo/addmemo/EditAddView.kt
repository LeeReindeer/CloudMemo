package xyz.leezoom.cloudmemo.addmemo

import xyz.leezoom.cloudmemo.bean.Memo

interface EditAddView {

  fun onSave(status: Boolean)

  fun onLoad(memo: Memo)

}