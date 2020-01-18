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
import com.saibaba.hackathon.HomeDesc;
import com.saibaba.hackathon.R;

import java.util.ArrayList;

public class Model_Adapter extends RecyclerView.Adapter<Model_Adapter.MyViewholder> {

    Context context;
    ArrayList<Model> post;
    private PopupWindow popupWindow;
    private String poi;

    public Model_Adapter(Context c, ArrayList<Model> p) {
        context = c;
        post = p;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        MyViewholder v=new MyViewholder(LayoutInflater.from(context).inflate(R.layout.elementhome, parent, false));
        return v;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewholder holder, final int i) {
        holder.name.setText(post.get(i).getName());
        holder.desc.setText(post.get(i).getDesc());
        holder.image.setImageDrawable(post.get(i).getImg());
        int colorRes = 0;
        switch(i % 9) {
            case 0: colorRes = R.color.list_color8;
                break;
            case 1: colorRes = R.color.list_color2;
                break;
            case 2: colorRes = R.color.list_color3;
                break;
            case 3: colorRes = R.color.list_color4;
                break;
            case 4: colorRes = R.color.list_color5;
                break;
            case 5: colorRes = R.color.list_color6;
                break;
            case 6: colorRes = R.color.list_color7;
                break;
            case 7:colorRes=R.color.list_color9;
                break;
            case 8:colorRes=R.color.list_color10;
                break;
        }
        holder.relativeLayout.setBackground(context.getResources().getDrawable(colorRes));
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, HomeDesc.class);
                intent.putExtra("Name",post.get(i).getName());
                context.startActivity(intent);
            }
        });
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
        TextView name, desc;
        RelativeLayout relativeLayout;
        ImageView image;
        public MyViewholder(View itemview) {
            super(itemview);
            name = (TextView) itemview.findViewById(R.id.namerecycle1);
            desc= (TextView) itemview.findViewById(R.id.descrecycle1);
            relativeLayout=itemview.findViewById(R.id.rellay_home);
            image=itemview.findViewById(R.id.iconrecycle1);
        }
    }
}


