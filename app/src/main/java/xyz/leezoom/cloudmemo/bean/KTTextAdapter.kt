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

package xyz.leezoom.androidcode.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import com.avos.avoscloud.AVUser
import kotlinx.android.synthetic.main.text_item.view.*
import xyz.leezoom.androidutilcode.adapter.ListViewAdapter
import xyz.leezoom.cloudmemo.R

class KTTextAdapter(context: Context, list: ArrayList<String>) : ListViewAdapter<String, KTTextAdapter.TextViewHolder>(context, list) {

  override val layoutId: Int
    get() = R.layout.text_item
  override val viewHolder: TextViewHolder
    get() = TextViewHolder()


  override fun bindView(holder: TextViewHolder, view: View?) {
    holder.text = view!!.text_view
  }

  override fun bindData(holder: TextViewHolder, data: String, position: Int) {
    if (data == AVUser.getCurrentUser().objectId) {
      holder.text.text = AVUser.getCurrentUser().username
    } else {
      holder.text.text = data
    }
  }

  class TextViewHolder : ListViewAdapter.ListViewHolder() {
    lateinit var text: TextView
  }

}