package xyz.leezoom.cloudmemo.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.avos.avoscloud.AVUser
import kotlinx.android.synthetic.main.activity_login.*
import xyz.leezoom.cloudmemo.memolist.ListActivity
import xyz.leezoom.cloudmemo.R

class LoginActivity : AppCompatActivity(), LoginView {

  private var loginOrRegister = true
  private lateinit var loginPresenter: LoginPresenter

  override fun onLogin(user: AVUser?, status: Boolean) {
    showProgress(false)
    if (status) {
      this@LoginActivity.finish()
      Toast.makeText(this@LoginActivity, "Login succeed", Toast.LENGTH_SHORT).show()
      startActivity(Intent(this@LoginActivity, ListActivity::class.java))
    } else {
      Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_SHORT).show()
    }
  }

  override fun onRegister(status: Boolean) {
    if (status) {
      Toast.makeText(this@LoginActivity, "Register succeed", Toast.LENGTH_SHORT).show()
      email_edit.visibility = View.GONE
      loginOrRegister = true
    } else {
      Toast.makeText(this@LoginActivity, "Register failed ", Toast.LENGTH_SHORT).show()
    }
  }

  override fun onError(status: Int) {
    when(status) {
      //格式错误
      -1 -> email_edit.error = "Check your email or password"
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    initData()
    initView()
    if (checkLogin()) {
      startActivity(Intent(this@LoginActivity, ListActivity::class.java))
      this.finish()
    }
  }

  private fun initView() {
    //do login/register
    do_button.setOnClickListener {
      showProgress(true)
      val userName = username_edit.text.toString()
      val pass = pass_edit.text.toString()
      val email = email_edit.text.toString()
      if (loginOrRegister) {
        //login
        loginPresenter.doLogin(userName, pass)
      } else {
        loginPresenter.doRegister(userName, pass, email)
      }
    }

    switch_button.setOnClickListener {
      if (loginOrRegister) {
        //to register
        email_edit.visibility = View.VISIBLE
      } else {
        //back login
        email_edit.visibility = View.GONE
      }
      loginOrRegister = !loginOrRegister
    }
  }

  private fun initData() {
    loginPresenter = LoginPresenterImpl(this@LoginActivity, this)
  }

  private fun showProgress(status: Boolean) {
    if (status) {
      login_container.visibility = View.GONE
      progressbar.visibility = View.VISIBLE
    } else {
      login_container.visibility = View.VISIBLE
      progressbar.visibility = View.GONE
    }
  }

  private fun checkLogin(): Boolean = (AVUser.getCurrentUser() != null)

}
