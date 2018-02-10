package xyz.leezoom.cloudmemo.bean

import android.os.Parcel
import android.os.Parcelable

import com.avos.avoscloud.AVClassName
import com.avos.avoscloud.AVObject
import com.avos.avoscloud.AVUser

@AVClassName("Memo")
class Memo : AVObject {

  var user: AVUser
    get() = getAVUser("user")
    set(user) = put("user", user)

  //memoL.setDescription(description);
  var description: String
    get() = getString("description")
    set(description) = put("description", description)

  var page: String
    get() = getString("page")
    set(pageName) = put("page", pageName)

  var isLocked: Boolean
    get() = getBoolean("locked")
    set(locked) = put("locked", locked)

  var visibility: Boolean
    get() = getBoolean("visibility")
    set(visibility) = put("visibility", visibility)

  constructor()

  //make Parcelable
  constructor(`in`: Parcel) : super(`in`)

  constructor(description: String, pageName: String, locked: Boolean, visibility: Boolean, user: AVUser) {
    put("description", description)
    put("page", pageName)
    put("locked", locked)
    put("visibility", visibility)
    put("user", user)
  }

  fun toLocal(): MemoL {
    return MemoL(getString("title"), getString("description"), getInt("words"), user, getCreatedAt())
  }

  companion object {
    //String description;
    //private MemoL memoL;
    //String pageName;
    //boolean locked;
    //boolean privated;
    //AVUser user
    val CREATOR: Parcelable.Creator<*> = AVObject.AVObjectCreator.instance
  }
}
