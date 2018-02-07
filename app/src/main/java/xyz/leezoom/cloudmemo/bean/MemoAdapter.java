package xyz.leezoom.cloudmemo.bean;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;

import xyz.leezoom.androidutilcode.adapter.ListViewAdapter;
import xyz.leezoom.cloudmemo.R;

public class MemoAdapter extends ListViewAdapter<Memo, MemoAdapter.ViewHolder> {

  private Context context;

  public MemoAdapter(@NotNull Context context, @NotNull ArrayList<Memo> list) {
    super(context, list);
    this.context = context;
  }


  @Override
  protected int getLayoutId() {
    return R.layout.memo_item_layout;
  }

  @NotNull
  @Override
  protected ViewHolder getViewHolder() {
    return new ViewHolder();
  }

  @Override
  protected void bindView(@NotNull ViewHolder holder, @org.jetbrains.annotations.Nullable View view) {
    assert view != null;
    holder.contentText = view.findViewById(R.id.mm_content);
    holder.timeText = view.findViewById(R.id.mm_time);
    holder.lockImage = view.findViewById(R.id.lock_image);
    holder.visibilityImage = view.findViewById(R.id.visibility_image);
  }

  @Override
  protected void bindData(@NotNull ViewHolder holder, Memo aMemo, int position) {
    if (aMemo.getDescription().length() <= 15) {
      holder.contentText.setText(aMemo.getDescription());
    } else {
      holder.contentText.setText(String.format("%s...", aMemo.getDescription().substring(0, 15)));
    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    holder.timeText.setText(dateFormat.format(aMemo.getCreatedAt()));
    if (aMemo.isLocked()) {
      holder.lockImage.setImageDrawable(new IconicsDrawable(context)
              .icon(CommunityMaterial.Icon.cmd_lock_outline)
              .color(ContextCompat.getColor(context, R.color.colorPrimary))
              .sizeDp(24));
    } else {
      holder.lockImage.setImageDrawable(new IconicsDrawable(context)
              .icon(CommunityMaterial.Icon.cmd_lock_open)
              .color(ContextCompat.getColor(context, R.color.colorPrimary))
              .sizeDp(24));
    }
    if (aMemo.getVisibility()) {
      holder.visibilityImage.setImageDrawable(new IconicsDrawable(context)
              .icon(CommunityMaterial.Icon.cmd_eye)
              .color(ContextCompat.getColor(context, R.color.colorPrimary))
              .sizeDp(24));
    } else {
      holder.visibilityImage.setImageDrawable(new IconicsDrawable(context)
              .icon(CommunityMaterial.Icon.cmd_eye_off)
              .color(ContextCompat.getColor(context, R.color.colorPrimary))
              .sizeDp(24));
    }
    //TODO switch status in list
    /*
    holder.lockImage.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

      }
    });*/
  }

  class ViewHolder extends ListViewAdapter.ListViewHolder {
    TextView contentText;
    TextView timeText;
    ImageView lockImage;
    ImageView visibilityImage;
  }
}
