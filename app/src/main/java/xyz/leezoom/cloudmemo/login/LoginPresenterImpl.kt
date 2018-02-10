package xyz.leezoom.cloudmemo.login

import android.content.Context
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVUser
import com.avos.avoscloud.LogInCallback
import com.avos.avoscloud.SignUpCallback

class LoginPresenterImpl (private var context: Context,
                          private var loginView: LoginView): LoginPresenter {

  override fun init() {}

  override fun doLogin(userName: String, pass: String) {

    var loginStatus = 2

    if (userName.isNotEmpty() && pass.isNotEmpty()) {
      AVUser.logInInBackground(userName, pass, object : LogInCallback<AVUser>() {
        override fun done(user: AVUser?, e: AVException?) {
          if (e != null || user == null) {
            loginStatus = e!!.code
          }
          loginView.onLogin(user, loginStatus)
        }
      })
    } else if (userName.isEmpty()) {
      loginView.onLogin(null, 200)
    } else if (pass.isEmpty()) {
      loginView.onLogin(null, 201)
    }
  }

  override fun doRegister(userName: String, pass: String, email: String) {
    var registerStatus = 2
    val user = AVUser()
    user.username = userName
    user.setPassword(pass)
    user.email = email
    if (userName.isNotEmpty() && pass.isNotEmpty() && email.isNotEmpty()) {
      when {
        userName.length !in 3..18 -> {
          loginView.onRegister(2000)
          return
        }

        pass.length !in 8..18 -> {
          loginView.onRegister(2011)
          return
        }

        !email.contains("@") -> {
          loginView.onRegister(2044)
          return
        }

        else -> {
          user.signUpInBackground(object : SignUpCallback() {
            override fun done(e: AVException?) {
              if (e != null) {
                registerStatus = e.code
              }
              loginView.onRegister(registerStatus)
            }
          })
        }

      }
    } else if (userName.isEmpty()) {
      loginView.onRegister(200)
    } else if (pass.isEmpty()) {
      loginView.onRegister(201)
    } else if (email.isEmpty()) {
      loginView.onRegister(204)
    }

  }
}