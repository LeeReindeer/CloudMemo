package xyz.leezoom.cloudmemo

import android.app.Application
import android.util.Log

import com.avos.avoscloud.AVAnalytics
import com.avos.avoscloud.AVOSCloud
import com.avos.avoscloud.AVObject

import cat.ereza.customactivityoncrash.config.CaocConfig
import com.avos.avoscloud.AVUser
import xyz.leezoom.androidutilcode.util.LogUtil
import xyz.leezoom.cloudmemo.bean.Memo
import xyz.leezoom.cloudmemo.memolist.ListActivity

class App : Application() {


  override fun onCreate() {
    super.onCreate()
    AVObject.registerSubclass(Memo::class.java)
    //TODO replace with your own appId and appKey
    AVOSCloud.initialize(this, "KmNc4aXzpcpJFJ7VeCCyMPpu-gzGzoHsz", "RoeS2vn2Mm16cfsEKYJBmhxp")
    AVOSCloud.setDebugLogEnabled(false)
    AVAnalytics.enableCrashReport(this, true)
    AVAnalytics.setSessionContinueMillis((60 * 1000).toLong())

    initCrashActivity()

    LogUtil.setCurrentLevel(LogUtil.ERROR)
  }

  private fun initCrashActivity() {
    CaocConfig.Builder.create()
            .showErrorDetails(true)
            .showRestartButton(true)
            .trackActivities(true)
            .logErrorOnRestart(true)
            .restartActivity(ListActivity::class.java)
            .apply()
  }

  companion object {
    var currentPage: String = if (AVUser.getCurrentUser() != null) AVUser.getCurrentUser().objectId else ""
    val CURRENT_PAGE = "CurrentPage"
    val PAGES = "pages"
  }
}
