

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

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

@Suppress("unused")
abstract class BaseRecycleAdapter<T, VH : BaseRecycleAdapter.BaseRecyclerHolder>(list: ArrayList<T>) : RecyclerView.Adapter<VH>() {

  private val TAG = "BaseRecycleAdapter"
  public var dataList: ArrayList<T> = list
  public var listener: OnRecyclerItemClickListener? = null

  @get:LayoutRes
  protected abstract val layoutId: Int

  protected abstract fun getViewHolder(view: View): VH

  override fun onBindViewHolder(holder: VH, position: Int) {
    holder.bindData(position)
  }

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VH {
    val view = LayoutInflater.from(parent!!.context).inflate(layoutId, parent, false)
    return getViewHolder(view)
  }

  override fun getItemCount(): Int = dataList.size

  abstract class  BaseRecyclerHolder(val view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bindData(position: Int)
  }

  public interface OnRecyclerItemClickListener {
    fun onClick(view: View, pos: Int)
    fun onLongClick(view: View, pos: Int)
  }
}