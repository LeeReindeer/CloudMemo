package xyz.leezoom.cloudmemo.bean;

import com.avos.avoscloud.AVUser;

import java.util.Date;

//save memo in local
public class MemoL {
  private String title;
  private String description;
  private int words;
  private AVUser user;
  private Date createAt;

  public MemoL(String title, String description, int words) {
    this.title = title;
    this.description = description;
    this.words = words;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getWords() {
    return words;
  }

  public void setWords(int words) {
    this.words = words;
  }

  public Date getCreateAt() {
    return createAt;
  }

  public void setCreateAt(Date createAt) {
    this.createAt = createAt;
  }
}
