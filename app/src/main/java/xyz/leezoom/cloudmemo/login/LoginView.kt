package xyz.leezoom.cloudmemo.login

import com.avos.avoscloud.AVUser

interface LoginView {
  fun onLogin(user: AVUser?, status: Int)

  fun onRegister(status: Int)

}