package xyz.leezoom.cloudmemo.login

import com.avos.avoscloud.AVUser

interface LoginView {
  fun onLogin(user: AVUser?, status: Boolean)

  fun onRegister(status: Boolean)

  fun onError(status: Int)
}