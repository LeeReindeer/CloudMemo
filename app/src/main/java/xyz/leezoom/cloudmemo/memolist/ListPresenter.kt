package xyz.leezoom.cloudmemo.memolist

interface ListPresenter {
  fun init()

  fun refresh(page: String)

  fun loadAll(page: String)

  fun addMemo()

  fun deleteMemo(id: String, position: Int)
}