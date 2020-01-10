package com.saibaba.hackathon.chatbot;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saibaba.hackathon.R;

import java.util.List;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.CustomViewHolder> {

    List<ModelChat> responseMessages;
    Context context;
    public void setData(List<ModelChat> list)
    {
        this.responseMessages.clear();
        responseMessages.addAll(list);
        notifyDataSetChanged();
    }
    class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public CustomViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }

    public AdapterChat(List<ModelChat> responseMessages, Context context) {
        this.responseMessages = responseMessages;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if(responseMessages.get(position).isMe()){
            return R.layout.chatbot_me;
        }
        return R.layout.chatbot_server;
    }

    @Override
    public int getItemCount() {
        return  responseMessages.size();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.textView.setText(responseMessages.get(position).getText());
    }
}
