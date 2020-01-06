package com.saibaba.hackathon.Home1;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saibaba.hackathon.R;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private ArrayList<ModalHome> list1;
    public HomeAdapter(Context context,ArrayList<ModalHome> list1)
    {
        this.list1=list1;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name,desc;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name=(TextView)itemView.findViewById(R.id.namerecycle1);
            this.name=(TextView)itemView.findViewById(R.id.descrecycle1);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_home,viewGroup,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        viewHolder.name.setText(list1.get(i).getName());
        viewHolder.desc.setText(list1.get(i).getDesc());
    }

    @Override
    public int getItemCount() {
        return list1.size();
    }
}
