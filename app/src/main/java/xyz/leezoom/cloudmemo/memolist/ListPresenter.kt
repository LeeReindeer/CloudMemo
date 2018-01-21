package xyz.leezoom.cloudmemo.memolist

interface ListPresenter {
  fun init()

  fun refresh()

  fun loadAll()

  fun addMemo()

  fun deleteMemo(id: String)
}