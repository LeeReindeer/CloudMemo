package xyz.leezoom.cloudmemo.login

import android.content.Context
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVUser
import com.avos.avoscloud.LogInCallback
import com.avos.avoscloud.SignUpCallback

class LoginPresenterImpl (private var context: Context,
                          private var loginView: LoginView): LoginPresenter {

  override fun init() {
    //loginView =
  }

  override fun doLogin(userName: String, pass: String) {
    if (userName.isNotEmpty() && pass.isNotEmpty()) {
      AVUser.logInInBackground(userName, pass, object : LogInCallback<AVUser>() {
        override fun done(user: AVUser?, e: AVException?) {
          var loginStatus = true
          if (e != null || user == null) {
            loginStatus = false
          }
          loginView.onLogin(user, loginStatus)
        }
      })
    } else {
      loginView.onError(-1)
    }
  }

  override fun doRegister(userName: String, pass: String, email: String) {
    var user = AVUser()
    user.username = userName
    user.setPassword(pass)
    user.email = email
    if (userName.isNotEmpty() && pass.length >= 8 && email.contains("@")) {
      user.signUpInBackground(object : SignUpCallback() {
        override fun done(e: AVException?) {
          var registerStatus = true
          if (e != null) {
            registerStatus = false
          }
          loginView.onRegister(registerStatus)
        }
      })
    } else {
      loginView.onError(-1)
    }

  }
}