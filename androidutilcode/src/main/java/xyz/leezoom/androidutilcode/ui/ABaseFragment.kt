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
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

@Suppress("unused")
abstract class ABaseFragment : Fragment() {

  private val TAG = "ABaseFragment"

  @get: LayoutRes
  abstract val layoutId: Int

  abstract fun initView(view: View?, savedInstanceState: Bundle?)

  abstract fun initData(view: View?)

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater!!.inflate(layoutId, container, false)
    initData(view)
    return view
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    initView(view, savedInstanceState)
  }
}