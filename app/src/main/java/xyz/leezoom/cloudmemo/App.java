package xyz.leezoom.cloudmemo;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;

import xyz.leezoom.cloudmemo.bean.Memo;

public class App extends Application{

  private static String currentPage;

  @Override
  public void onCreate() {
    super.onCreate();
    AVObject.registerSubclass(Memo.class);
    //TODO replace with your own appId and appKey, and turn of debug mod
    AVOSCloud.initialize(this, "KmNc4aXzpcpJFJ7VeCCyMPpu-gzGzoHsz", "RoeS2vn2Mm16cfsEKYJBmhxp");
    AVOSCloud.setDebugLogEnabled(true);
  }

  public static String getCurrentPage() {
    return currentPage;
  }

  public static void setCurrentPage(String currentPage) {
    App.currentPage = currentPage;
  }
}
