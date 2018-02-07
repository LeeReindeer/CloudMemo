/*
 *        Copyright 2017 LeeReindeer <reindeerlee.work@gmail.com>
 *
 *        Licensed under the Apache License, Version 2.0 (the "License");
 *        you may not use this file except in compliance with the License.
 *        You may obtain a copy of the License at
 *
 *            http://www.apache.org/licenses/LICENSE-2.0
 *
 *        Unless required by applicable law or agreed to in writing, software
 *        distributed under the License is distributed on an "AS IS" BASIS,
 *        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *        See the License for the specific language governing permissions and
 *        limitations under the License.
 */

package xyz.leezoom.androidutilcode.ui

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

@Suppress("unused")
abstract class ABaseActivity : AppCompatActivity() {

  private val TAG = "ABaseActivity"
  protected val NOT_TOOLBAR = -1

  protected var toolbar: Toolbar? =null

  @get:LayoutRes
  abstract val layoutId: Int

  @get: IdRes
  abstract val toolbarId: Int

  protected open fun beforeCreate() {}

  abstract fun initData()

  abstract fun initView()

  override fun onCreate(savedInstanceState: Bundle?) {
    beforeCreate()
    super.onCreate(savedInstanceState)
    setContentView(layoutId)
    if (toolbarId != -1) {
      toolbar = findViewById(toolbarId)
      setSupportActionBar(toolbar)
    }

    initData()
    initView()
  }
}