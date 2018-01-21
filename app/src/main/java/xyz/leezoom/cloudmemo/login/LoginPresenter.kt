package xyz.leezoom.cloudmemo.login

interface LoginPresenter {

  fun init()

  fun doLogin(userName: String, pass: String)

  fun doRegister(userName: String, pass: String, email: String)
}