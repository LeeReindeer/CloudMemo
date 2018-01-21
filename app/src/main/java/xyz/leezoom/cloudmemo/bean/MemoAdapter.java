package xyz.leezoom.cloudmemo.bean;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;

import xyz.leezoom.cloudmemo.R;

public class MemoAdapter extends ArrayAdapter<Memo> {

  private int resourceId;
  private Context context;

  public MemoAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Memo> memos) {
    super(context, resource, memos);
    this.context = context;
    this.resourceId = resource;
  }


  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    Memo memo = getItem(position);
    ViewHolder viewHolder;
    if (convertView == null) {
      convertView = LayoutInflater.from(context).inflate(resourceId, parent,false);
      viewHolder = new ViewHolder();
      viewHolder.timeText = (TextView)convertView.findViewById(R.id.mm_time);
      viewHolder.titleText = (TextView)convertView.findViewById(R.id.mm_title);
      viewHolder.contentText = (TextView)convertView.findViewById(R.id.mm_content);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) convertView.getTag();
    }

    viewHolder.titleText.setText(memo.getTitle());
    viewHolder.contentText.setText(memo.getDescription());
    //SimpleDateFormat ft = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.CHINA);
    viewHolder.timeText.setText(memo.getCreatedAt().toString());
    return convertView;
    //return super.getView(position, convertView, parent);
  }

  @Override
  public int getCount() {
    return super.getCount();
  }

  class ViewHolder {
    TextView titleText;
    TextView contentText;
    TextView timeText;
  }
}
