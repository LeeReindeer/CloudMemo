package xyz.leezoom.cloudmemo;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;

import xyz.leezoom.cloudmemo.bean.Memo;

public class App extends Application{

  @Override
  public void onCreate() {
    super.onCreate();
    AVObject.registerSubclass(Memo.class);
    //TODO replace with your own appId and appKey, and turn of debug mod
    AVOSCloud.initialize(this, "", "");
    AVOSCloud.setDebugLogEnabled(true);
  }
}
