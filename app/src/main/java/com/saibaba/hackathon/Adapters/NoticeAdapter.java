package com.saibaba.hackathon.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;
import com.saibaba.hackathon.HomeDesc;
import com.saibaba.hackathon.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.MyViewholder> {

    Context context;
    ArrayList<ModelNotice> post;
    private PopupWindow popupWindow;
    private String poi;
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public NoticeAdapter(Context c, ArrayList<ModelNotice> p) {
        context = c;
        post=p;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        MyViewholder v=new MyViewholder(LayoutInflater.from(context).inflate(R.layout.element_notices, parent, false));
        return v;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewholder holder, final int i) {
        holder.name.setText(post.get(i).getName());
        holder.desc.setText(post.get(i).getContent());
        holder.post.setText(post.get(i).getPost());
        holder.time.setText(getTimeAgo(Long.parseLong(post.get(i).getTime())));
        if(!post.get(i).getImageurl().equals("")) {
            holder.imageView.setVisibility(View.VISIBLE);
            Glide.with(context).load(post.get(i).getImageurl()).into(holder.imageView);

        }
    }

    @Override
    public int getItemCount() {
        return post.size();
    }
    @Override
    public long getItemId(int position)
    {
        return position;
    }
    @Override
    public int getItemViewType(int pos)
    {
        return pos;
    }

    class MyViewholder extends RecyclerView.ViewHolder {
        TextView name, desc,time,post;
        ImageView imageView;
        public MyViewholder(View itemview) {
            super(itemview);
            name = (TextView) itemview.findViewById(R.id.tv_username_blog_item);
            desc= (TextView) itemview.findViewById(R.id.tv_content_blog_item);
            time=(TextView) itemview.findViewById(R.id.tv_postTime_blog_item);
            post=(TextView) itemview.findViewById(R.id.tv_post_blog_item2);
            imageView=itemview.findViewById(R.id.img_noticeadapter);
        }
    }

    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }
}


