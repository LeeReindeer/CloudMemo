package xyz.leezoom.cloudmemo.memolist

interface ListPresenter {
  fun init()

  fun refresh(page: String, order: Boolean = true)

  fun loadAll(page: String, order: Boolean = true)

  fun addMemo()

  fun deleteMemo(id: String, position: Int)
}