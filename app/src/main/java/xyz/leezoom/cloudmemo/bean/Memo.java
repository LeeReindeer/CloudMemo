package xyz.leezoom.cloudmemo.bean;

import android.os.Parcel;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

@AVClassName("Memo")
public class Memo extends AVObject {
  //String description;
  //private MemoL memoL;
  //String pageName;
  //boolean locked;
  //boolean privated;
  //AVUser user

  public static final Creator CREATOR = AVObjectCreator.instance;

  public Memo() {
  }

  //make Parcelable
  public Memo(Parcel in) {
    super(in);
  }

  public Memo(String description, String pageName, boolean locked, boolean visibility, AVUser user) {
    put("description", description);
    put("page", pageName);
    put("locked", locked);
    put("visibility", visibility);
    put("user", user);
    //memoL.setTitle(title);
    //memoL.setDescription(description);
    //memoL.setWords(words);
  }

  public AVUser getUser() {
    return getAVUser("user");
  }

  public void setUser(AVUser user) {
    put("user", user);
  }

  public String getDescription() {
    return getString("description");
  }

  public void setDescription(String description) {
    put("description", description);
    //memoL.setDescription(description);
  }

  public String getPage() {
    return getString("page");
  }

  public void setPage(String pageName) {
    put("page", pageName);
  }

  public boolean isLocked() {
    return getBoolean("locked");
  }

  public void setLocked(boolean locked) {
    put("locked", locked);
  }

  public boolean getVisibility() {
    return getBoolean("visibility");
  }

  public void setVisibility(boolean visibility) {
    put("visibility", visibility);
  }

  public MemoL toLocal() {
    return new MemoL(getString("title"), getString("description"), getInt("words"), getUser(), getCreatedAt());
  }

  /*
  public MemoL getMemoL() {
    return memoL;
  }
  */
}
