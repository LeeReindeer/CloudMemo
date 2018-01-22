package xyz.leezoom.cloudmemo.bean;

import android.os.Parcel;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

@AVClassName("Memo")
public class Memo extends AVObject {
  //String title;
  //String description;
  //int words;
  //private MemoL memoL;
  public static final Creator CREATOR = AVObjectCreator.instance;

  public Memo() {
  }

  //make Parcelable
  public Memo(Parcel in) {
    super(in);
  }

  public Memo(String title, String description, int words, AVUser user) {
    put("title", title);
    put("description", description);
    put("words", words);
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

  public String getTitle() {
    return getString("title");
  }

  public void setTitle(String title) {
    put("title", title);
    //memoL.setTitle(title);
  }

  public String getDescription() {
    return getString("description");
  }

  public void setDescription(String description) {
    put("description", description);
    //memoL.setDescription(description);
  }

  public int getWords() {
    return getInt("words");
  }

  public void setWords(int words) {
    put("words", words);
    //memoL.setWords(words);
  }


  public MemoL toLocal() {
    return new MemoL(getString("title"), getString("description"), getInt("words"));
  }

  /*
  public MemoL getMemoL() {
    return memoL;
  }
  */
}
