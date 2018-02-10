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

package xyz.leezoom.androidutilcode.adapter

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import java.util.*

@Suppress("unused")
abstract class ListViewAdapter<T, VH : ListViewAdapter.ListViewHolder>(protected var context: Context, protected var list: ArrayList<T>)
  : BaseAdapter() {

  @get:LayoutRes
  protected abstract val layoutId: Int

  protected abstract val viewHolder: VH

  protected abstract fun bindView(holder: VH, view: View?)

  protected abstract fun bindData(holder: VH, data: T, position: Int)

  override fun getCount(): Int {
    return list.size
  }

  override fun getItem(position: Int): T {
    return list[position]
  }

  override fun getItemId(position: Int): Long {
    return position.toLong()
  }

  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    var convertView1 = convertView
    val holder: VH
    if (convertView1 == null) {
      convertView1 = LayoutInflater.from(context).inflate(layoutId, parent, false)
      holder = viewHolder
      bindView(holder, convertView1)
      convertView1!!.tag = holder
    } else {

      holder = convertView1.tag as VH
    }
    bindData(holder, list[position], position)
    return convertView1
  }

  abstract class ListViewHolder
}
