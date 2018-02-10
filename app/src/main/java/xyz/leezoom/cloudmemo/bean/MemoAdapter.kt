package xyz.leezoom.cloudmemo.bean

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.mikepenz.community_material_typeface_library.CommunityMaterial
import com.mikepenz.iconics.IconicsDrawable
import xyz.leezoom.androidutilcode.adapter.ListViewAdapter
import xyz.leezoom.cloudmemo.R
import java.text.SimpleDateFormat
import java.util.*

class MemoAdapter(context: Context, list: ArrayList<Memo>) : ListViewAdapter<Memo, MemoAdapter.ViewHolder>(context, list) {


  override val layoutId: Int
    get() = R.layout.memo_item_layout

  override val viewHolder: ViewHolder
    get() = ViewHolder()

  override fun bindView(holder: ViewHolder, view: View?) {
    assert(view != null)
    holder.contentText = view!!.findViewById(R.id.mm_content)
    holder.timeText = view.findViewById(R.id.mm_time)
    holder.lockImage = view.findViewById(R.id.lock_image)
    holder.visibilityImage = view.findViewById(R.id.visibility_image)
  }

  override fun bindData(holder: ViewHolder, aMemo: Memo, position: Int) {
    if (aMemo.description.length <= 15) {
      holder.contentText!!.text = aMemo.description
    } else {
      holder.contentText!!.text = String.format("%s...", aMemo.description.substring(0, 15))
    }

    val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
    holder.timeText!!.text = dateFormat.format(aMemo.createdAt)
    if (aMemo.isLocked) {
      holder.lockImage!!.setImageDrawable(IconicsDrawable(context)
              .icon(CommunityMaterial.Icon.cmd_lock_outline)
              .color(ContextCompat.getColor(context, R.color.colorPrimary))
              .sizeDp(24))
    } else {
      holder.lockImage!!.setImageDrawable(IconicsDrawable(context)
              .icon(CommunityMaterial.Icon.cmd_lock_open)
              .color(ContextCompat.getColor(context, R.color.colorPrimary))
              .sizeDp(24))
    }
    if (aMemo.visibility) {
      holder.visibilityImage!!.setImageDrawable(IconicsDrawable(context)
              .icon(CommunityMaterial.Icon.cmd_eye)
              .color(ContextCompat.getColor(context, R.color.colorPrimary))
              .sizeDp(24))
    } else {
      holder.visibilityImage!!.setImageDrawable(IconicsDrawable(context)
              .icon(CommunityMaterial.Icon.cmd_eye_off)
              .color(ContextCompat.getColor(context, R.color.colorPrimary))
              .sizeDp(24))
    }
    //TODO switch status in list
    /*
    holder.lockImage.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

      }
    });*/
  }

  inner class ViewHolder : ListViewAdapter.ListViewHolder() {
    var contentText: TextView? = null
    var timeText: TextView? = null
    var lockImage: ImageView? = null
    var visibilityImage: ImageView? = null
  }

}
