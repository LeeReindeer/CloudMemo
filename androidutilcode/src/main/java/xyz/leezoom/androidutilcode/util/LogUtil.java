package xyz.leezoom.androidutilcode.util;

import android.util.Log;

public class LogUtil {

  public static final int VERBOSE = 2;
  public static final int DEBUG = 3;
  public static final int INFO = 4;
  public static final int WARN = 5;
  public static final int ERROR = 6;

  public static final int NONE = 8;
  private static int CURRENT_LEVEL = VERBOSE;

  private LogUtil() {
  }

  public static void v(String TAG, Object o) {
    if (CURRENT_LEVEL <= VERBOSE) {
      Log.v(TAG, o.toString());
    }
  }

  public static void d(String TAG, Object o) {
    if (CURRENT_LEVEL <= DEBUG) {
      Log.d(TAG, o.toString());
    }
  }

  public static void i(String TAG, Object o) {
    if (CURRENT_LEVEL <= INFO) {
      Log.i(TAG, o.toString());
    }
  }

  public static void w(String TAG, Object o) {
    if (CURRENT_LEVEL <= WARN) {
      Log.d(TAG, o.toString());
    }
  }

  public static void e(String TAG, Object o) {
    if (CURRENT_LEVEL <= ERROR) {
      Log.e(TAG, o.toString());
    }
  }

  public static int getCurrentLevel() {
    return CURRENT_LEVEL;
  }

  public static void setCurrentLevel(int currentLevel) {
    CURRENT_LEVEL = currentLevel;
  }
}
