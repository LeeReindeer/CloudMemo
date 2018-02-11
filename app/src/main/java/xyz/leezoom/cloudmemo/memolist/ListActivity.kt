package xyz.leezoom.cloudmemo.memolist

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.ActionMenuView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.content.edit
import com.avos.avoscloud.AVAnalytics
import com.avos.avoscloud.AVUser
import com.mikepenz.community_material_typeface_library.CommunityMaterial
import com.mikepenz.iconics.IconicsDrawable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_header_layout.view.*
import org.jetbrains.anko.*
import xyz.leezoom.androidutilcode.util.LogUtil
import xyz.leezoom.androidutilcode.util.toast
import xyz.leezoom.cloudmemo.AboutActivity
import xyz.leezoom.cloudmemo.App
import xyz.leezoom.cloudmemo.R
import xyz.leezoom.cloudmemo.addmemo.EditAddActivity
import xyz.leezoom.cloudmemo.bean.KTTextAdapter
import xyz.leezoom.cloudmemo.bean.Memo
import xyz.leezoom.cloudmemo.bean.MemoAdapter
import xyz.leezoom.cloudmemo.login.LoginActivity


class ListActivity : AppCompatActivity(), ListView, NavigationView.OnNavigationItemSelectedListener {

  private val TAG = "ListActivity"

  private lateinit var actionMenu: ActionMenuView
  private lateinit var pageList: ArrayList<String>
  private lateinit var spinner: Spinner

  private var descendOrder = true
  private var memoAdapter: MemoAdapter? = null
  private var memoList: ArrayList<Memo> = ArrayList()
  private var presenter: ListPresenter? = null
  private val REFRESH_CODE = 1

  override fun onRefresh(status: Boolean, list: ArrayList<Memo>) {
    if (status) {
      memoList.clear()
      memoList.addAll(list)
      memoAdapter!!.notifyDataSetChanged()
    }
    //FIXME
    //setToolbarTitle()
    refresh_layout.isRefreshing = false
  }

  override fun onAdd() {}

  override fun onDelete(status: Boolean, pos: Int) {
    if (status) {
      //not refresh from net
      Toast.makeText(this, "deleted", Toast.LENGTH_SHORT).show()
      memoList.removeAt(pos)
      memoAdapter!!.notifyDataSetChanged()
    }
  }

  //first time load
  override fun onLoad(status: Boolean, list: ArrayList<Memo>) {
    if (status) {
      memoList.addAll(list)
      memoAdapter!!.notifyDataSetChanged()
    }
    refresh_layout.isRefreshing = false
    //FIXME
    //setToolbarTitle()
  }

  override fun onError(message: String?) {
    refresh_layout.isRefreshing = false
    if (!message.isNullOrBlank()) {
      toast("refresh error, $message")
    }
  }

  private fun setDrawer() {
    if (supportActionBar != null) {
      supportActionBar!!.setDisplayHomeAsUpEnabled(true)
      supportActionBar!!.setHomeAsUpIndicator(IconicsDrawable(this)
              .icon(CommunityMaterial.Icon.cmd_menu)
              .color(Color.WHITE)
              .sizeDp(18))
    }
    nav_view.setNavigationItemSelectedListener(this)
    val header = nav_view.getHeaderView(0)
    header.nav_header_name.text = AVUser.getCurrentUser().username
  }

  private fun checkLogin(): Boolean = (AVUser.getCurrentUser() == null)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    initToolbar()
    initData()
    initView()
  }

  @SuppressLint("MissingSuperCall")
  override fun onPause() {
    super.onPause()
    AVAnalytics.onPause(this)
  }

  @SuppressLint("MissingSuperCall")
  override fun onResume() {
    super.onResume()
    AVAnalytics.onPause(this)
  }

  override fun onBackPressed() {
    if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
      drawer_layout.closeDrawer(GravityCompat.START)
    } else {
      super.onBackPressed()
    }
  }

  private fun initToolbar() {
    val toolbar = findViewById<Toolbar>(R.id.tool_bar)
    actionMenu = toolbar.findViewById(R.id.action_menu_view)
    actionMenu.setOnMenuItemClickListener { menuItem ->
      onOptionsItemSelected(menuItem)
    }
    setSupportActionBar(toolbar)
    supportActionBar!!.title = null
    supportActionBar!!.setDisplayHomeAsUpEnabled(true)
  }

  private fun initData() {
    if (checkLogin()) {
      LogUtil.d(TAG, "Null user: ")
      toast(getString(R.string.toast_login_first))
      this.finish()
      startActivity<LoginActivity>()
    } else {
      presenter = ListPresenterImpl(this, this)
      memoAdapter = MemoAdapter(this, memoList)
      refresh_layout.isRefreshing = true
      App.currentPage = defaultSharedPreferences.getString(App.CURRENT_PAGE, App.userId)
      initPageList()
      LogUtil.d(TAG, "Init Current page: ${App.currentPage}")
      presenter!!.loadAll(App.currentPage, descendOrder)
    }
  }

  private fun initPageList() {
    pageList = defaultSharedPreferences.getStringSet(App.PAGES, emptySet()).toCollection(ArrayList())
    pageList.sort()
    if (pageList.contains(App.userId)) {
      val index = pageList.indexOf(App.userId)
      pageList[index] = pageList[0]
      pageList[0] = App.userId
    }
  }

  @SuppressLint("RestrictedApi")
  private fun initView() {
    //setToolbarTitle()
    supportActionBar!!.title = null
    //supportActionBar {
    // showPages()
    //}
    setDrawer()

    list_view.adapter = memoAdapter
    list_view.setOnItemClickListener { parent, view, position, id ->
      LogUtil.d("Click", "$position")
      val intent = Intent(this@ListActivity, EditAddActivity::class.java)
      intent.putExtra("memo", memoList[position])
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val pair = Pair<View, String>(view.findViewById(R.id.mm_content), getString(R.string.transition_content))
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair)
        startActivityForResult(intent, REFRESH_CODE, options.toBundle())
      } else {
        startActivityForResult(intent, REFRESH_CODE)
      }
    }

    /*
    list_view.onItemLongClickListener = AdapterView.OnItemLongClickListener { parent, view, position, id ->

      Toast.makeText(this, "delete $position, ${memoList[position].description}", Toast.LENGTH_SHORT).show()
      presenter.deleteMemo(memoList[position].objectId, position)
      true
    }*/
    main_fb.setImageDrawable(IconicsDrawable(this)
            .icon(CommunityMaterial.Icon.cmd_pencil)
            .color(Color.WHITE)
            .sizeDp(24))
    main_fb.setOnClickListener {
      startActivityForResult(Intent(this@ListActivity, EditAddActivity::class.java), REFRESH_CODE)
    }
    refresh_layout.setOnRefreshListener {
      LogUtil.d(TAG, "currentPage: ${App.currentPage}")
      presenter!!.refresh(App.currentPage, descendOrder)
    }

  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    when (requestCode) {
      REFRESH_CODE -> {
        if (resultCode == Activity.RESULT_OK) {
          LogUtil.d(TAG, "onActivityResult: REFRESH")
          presenter!!.refresh(App.currentPage, descendOrder)
        }
      }
      else -> toast("Canceled")
    }
  }

  //Menu start
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu, actionMenu.menu)

    val item = actionMenu.menu.findItem(R.id.menu_spinner)
    spinner = item.actionView as Spinner
    refreshMenuSpinner(App.currentPage)
    return true
  }

  private fun refreshMenuSpinner(page: String) {
    initPageList()
    val adapter = KTTextAdapter(this, pageList)
    spinner.adapter = adapter
    spinner.setPopupBackgroundResource(R.color.colorPrimary)
    spinner.onItemSelectedListener = ItemSelectedListener()
    //goto new add page
    (0 until adapter.count)
            .filter { spinner.getItemAtPosition(it).toString() == page }
            .forEach { spinner.setSelection(it) }
  }

  inner class ItemSelectedListener : AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
      defaultSharedPreferences.edit {
        if (pageList[position] != App.currentPage) {
          App.currentPage = pageList[position]
          putString(App.CURRENT_PAGE, pageList[position])
          presenter!!.refresh(pageList[position], descendOrder)
        }
      }
    }
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when(item!!.itemId) {

      android.R.id.home -> {
        drawer_layout.openDrawer(GravityCompat.START)
        return true
      }

      R.id.menu_out -> {
        alert(getString(R.string.alert_logout_confirm)) {
          okButton {
            AVUser.logOut()
            App.userId = ""
            defaultSharedPreferences.edit {
              putString(App.CURRENT_PAGE, "")
              putStringSet("page", emptySet())
            }
            startActivity<LoginActivity>()
            this@ListActivity.finish()
          }
          cancelButton { }
        }.show()
        return true
      }

      R.id.menu_world -> {
        alert (getString(R.string.alert_add_world)){
          var page = ""
          val pageSet = defaultSharedPreferences.getStringSet(App.PAGES, emptySet())
          var editView: EditText? = null

          customView {
            editView = editText{
              hint = this@ListActivity.getString(R.string.hint_try_input)
            }
          }

          yesButton {
            page = editView!!.text.toString()
            LogUtil.d(TAG, "page: $page")
            if (page.isNotBlank()) {
              if (page == AVUser.getCurrentUser().username || page == AVUser.getCurrentUser().objectId) {
                toast("")
              }
              defaultSharedPreferences.edit {
                pageSet.add(page)
                LogUtil.d(TAG, "add page: $pageSet")
                putStringSet(App.PAGES, pageSet)
                putString(App.CURRENT_PAGE, page)
              }
              App.currentPage = page
              refreshMenuSpinner(page)
              presenter!!.refresh(page, descendOrder)
            }
          }
          cancelButton {  }
        }.show()
        return true
      }

      R.id.menu_sort -> {
        descendOrder = !descendOrder
        presenter!!.refresh(App.currentPage, descendOrder)
        return true
      }

      else -> return super.onOptionsItemSelected(item)
    }
  }
  //Menu end

  //Nav menu start
  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    //TODO("not implemented")
    when (item.itemId) {
      R.id.nav_menu_about -> {
        startActivity<AboutActivity>()
        return true
      }
      else -> return false
    }
  }
  //Nav menu end
}
