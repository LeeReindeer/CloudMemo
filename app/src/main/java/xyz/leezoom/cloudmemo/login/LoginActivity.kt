package xyz.leezoom.cloudmemo.login

import android.view.View
import androidx.content.edit
import com.avos.avoscloud.AVUser
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.defaultSharedPreferences
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import xyz.leezoom.androidutilcode.ui.ABaseActivity
import xyz.leezoom.cloudmemo.App
import xyz.leezoom.cloudmemo.R
import xyz.leezoom.cloudmemo.memolist.ListActivity

class LoginActivity : ABaseActivity(), LoginView {

  override val layoutId: Int
    get() = R.layout.activity_login

  override val toolbarId: Int
    get() = R.id.tool_bar

  private var loginOrRegister = true
  private lateinit var loginPresenter: LoginPresenter


  /**codes:
   *
   * 2   ->  0 error 0 warn.
   *
   * 100 -> net error
   *
   * 200 ->  Username is missing or empty
   * 2000 -> Username 格式错误
   * 201 ->  Password is missing or empty.
   * 2011 -> Password 格式错误
   * 202 ->  Username has already been taken.
   * 203 ->  Email has already been taken.
   * 204 ->  The email is missing, and must be specified.
   * 2044 -> Email 格式错误
   * 210 ->  The username and password mismatch.
   * 211 ->  Could not find user.
   */

  override fun onLogin(user: AVUser?, status: Int) {
    showProgress(false)
    when (status) {
      0, 1, 100 -> toast("Network error")
      2 -> {
        this@LoginActivity.finish()
        App.currentPage = user!!.objectId
        defaultSharedPreferences.edit {
          putString(App.CURRENT_PAGE, user.objectId)
          putStringSet(App.PAGES, setOf(user.objectId))
        }
        toast("Hey ${user.username}")
        startActivity<ListActivity>()
      }
      200 -> input_layout_username.error = getString(R.string.username_empty_error)
      201 -> input_layout_pass.error = getString(R.string.pass_empty_error)
      202 -> input_layout_username.error = getString(R.string.username_taken_error)

      210 -> {
        input_layout_username.error = getString(R.string.account_pass_not_match)
        input_layout_pass.error = getString(R.string.account_pass_not_match)
      }
      211 -> input_layout_username.error = getString(R.string.account_not_exit_error)
    }
  }

  override fun onRegister(status: Int) {
    loginOrRegister = false
    when (status) {
      0, 1, 100 -> toast("Network error")
      2 -> {
        toast(getString(R.string.toast_register_success))
        input_layout_email.visibility = View.GONE
        loginOrRegister = true
        loginPresenter.doLogin(username_edit.text.toString(), pass_edit.text.toString())
      }
      200 -> input_layout_username.error = getString(R.string.username_empty_error)
      2000 -> input_layout_username.error = getString(R.string.account_not_valid_error)
      201 -> input_layout_pass.error = getString(R.string.pass_empty_error)
      2011 -> input_layout_pass.error = getString(R.string.pass_not_valid_error)
      202 -> input_layout_username.error = getString(R.string.username_taken_error)
      203 -> input_layout_email.error = getString(R.string.email_taken_error)
      204 -> input_layout_email.error = getString(R.string.email_empty_error)
      2044 -> input_layout_email.error = getString(R.string.email_not_valid_error)
    }
    showProgress(false)
  }

  override fun initData() {
    loginPresenter = LoginPresenterImpl(this@LoginActivity, this)
  }

  override fun initView() {
    title = getText(R.string.app_name)
    login_button.setOnClickListener {
      if (loginOrRegister) {
        showProgress(true)
        val userName = username_edit.text.toString()
        val pass = pass_edit.text.toString()

        loginPresenter.doLogin(userName, pass)
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
        //do register
        showProgress(true)
        val userName = username_edit.text.toString()
        val pass = pass_edit.text.toString()
        val email = email_edit.text.toString()
        loginPresenter.doRegister(userName, pass, email)
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


}
