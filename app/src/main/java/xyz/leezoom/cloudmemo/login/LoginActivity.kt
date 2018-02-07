package xyz.leezoom.cloudmemo.login

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.content.edit
import com.avos.avoscloud.AVUser
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.defaultSharedPreferences
import org.jetbrains.anko.startActivity
import xyz.leezoom.androidutilcode.ui.ABaseActivity
import xyz.leezoom.cloudmemo.App
import xyz.leezoom.cloudmemo.R
import xyz.leezoom.cloudmemo.memolist.ListActivity

class LoginActivity : ABaseActivity(), LoginView {

  override val layoutId: Int
    get() = R.layout.activity_login

  override val toolbarId: Int
    get() = -1

  private var loginOrRegister = true
  private lateinit var loginPresenter: LoginPresenter

  override fun onLogin(user: AVUser?, status: Boolean) {
    showProgress(false)
    if (status) {
      this@LoginActivity.finish()
      App.setCurrentPage(user!!.objectId)
      defaultSharedPreferences.edit {
        putString("CurrentPage", user.objectId)
        putStringSet("pages", setOf(user.objectId))
      }
      Toast.makeText(this@LoginActivity, "Hey ${user.username}", Toast.LENGTH_SHORT).show()
      startActivity<ListActivity>()
    } else {
      Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_SHORT).show()
    }
  }

  override fun onRegister(status: Boolean) {
    if (status) {
      Toast.makeText(this@LoginActivity, "Register succeed", Toast.LENGTH_SHORT).show()
      input_layout_email.visibility = View.GONE
      loginOrRegister = true
    } else {
      Toast.makeText(this@LoginActivity, "Register failed ", Toast.LENGTH_SHORT).show()
    }
  }

  override fun onError(status: Int) {
    when(status) {
      //格式错误
      -1 -> input_layout_email.error = "Check your email or password"
    }
  }

  override fun initData() {
    loginPresenter = LoginPresenterImpl(this@LoginActivity, this)
    if (checkLogin()) {
      startActivity(Intent(this@LoginActivity, ListActivity::class.java))
      this.finish()
    }
  }

  override fun initView() {
    title = getText(R.string.app_name)
    login_button.setOnClickListener {
      if (loginOrRegister) {
        showProgress(true)
        val userName = username_edit.text.toString()
        val pass = pass_edit.text.toString()
        if(userName.isNotEmpty() && pass.isNotEmpty()) {
          loginPresenter.doLogin(userName, pass)
        } else {
          showProgress(false)
          input_layout_username.error = "Username is requested"
          input_layout_pass.error = "Password is requested"
        }
      } else {
        input_layout_email.visibility = View.GONE
        loginOrRegister = !loginOrRegister
      }
    }

    register_button.setOnClickListener {
      if (loginOrRegister) {
        //to register
        register_button.text = getString(R.string.register_hint)
        input_layout_email.visibility = View.VISIBLE
      } else {
        //do register and login
        showProgress(true)
        val userName = username_edit.text.toString()
        val pass = pass_edit.text.toString()
        val email = email_edit.text.toString()
        if (userName.isNotEmpty() && pass.isNotEmpty() && email.isNotEmpty()) {
          loginPresenter.doRegister(userName, pass, email)
        }
      }
      loginOrRegister = !loginOrRegister
    }
  }



  private fun showProgress(status: Boolean) {
    if (status) {
      buttons_container.visibility = View.GONE
      progressbar.visibility = View.VISIBLE
    } else {
      buttons_container.visibility = View.VISIBLE
      progressbar.visibility = View.GONE
    }
  }

  private fun checkLogin(): Boolean = (AVUser.getCurrentUser() != null)

}
