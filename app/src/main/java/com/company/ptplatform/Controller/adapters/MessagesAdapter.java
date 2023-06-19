package com.company.ptplatform.Controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.company.ptplatform.Model.Beans.Chat.Message;
import com.company.ptplatform.Model.utilits.PreferencesUtils;
import com.company.ptplatform.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MessagesAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Message> messageArrayList;

    int ITEM_SEND=1;
    int ITEM_RECEIVE=2;

    public MessagesAdapter(Context context, ArrayList<Message> messageArrayList) {
        this.context = context;
        this.messageArrayList = messageArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == ITEM_SEND) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_chat, parent, false);
            return new senderViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_chat_receiver, parent, false);
            return new receiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Message current = messageArrayList.get(position);

        Date date = new Date(Long.parseLong(current.getTime()));
        String timeFormatted = new SimpleDateFormat("hh:mm a").format(date);

        if (holder.getClass()==senderViewHolder.class){
            senderViewHolder viewHolder = (senderViewHolder) holder;
            viewHolder.messages.setText(current.getMessage());
            viewHolder.messageTime.setText(timeFormatted);
        } else {
            receiverViewHolder viewHolder = (receiverViewHolder) holder;
            viewHolder.messages.setText(current.getMessage());
            viewHolder.messageTime.setText(timeFormatted);
        }

//        if (current.getSenderId().equalsIgnoreCase(PreferencesUtils.getUser(context).getId().toString())){
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.setMargins(100,0,0,0);
//            params.weight = 1.0f;
//            params.gravity = Gravity.END;
//            holder.messages.setLayoutParams(params);
//
//            holder.messages.setGravity(Gravity.END);
//            holder.messages.setBackgroundResource(R.drawable.background_radius_20_title);
//            holder.messages.setTextColor(context.getColor(R.color.white));
//        } else {
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.setMargins(0,0,100,0);
//            holder.messages.setLayoutParams(params);
//            params.weight = 1.0f;
//            params.gravity = Gravity.START;
//
//            holder.messages.setGravity(Gravity.START);
//            holder.messages.setBackgroundResource(R.drawable.background_radius_20);
//            holder.messages.setTextColor(context.getColor(R.color.white));
//        }

    }

    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }


    @Override
    public int getItemViewType(int position) {
        Message messages = messageArrayList.get(position);
        if (messages.getSenderId().equalsIgnoreCase(PreferencesUtils.getUser(context).getId().toString())) {
            return ITEM_SEND;
        } else {
            return ITEM_RECEIVE;
        }
    }


    public static class  senderViewHolder extends RecyclerView.ViewHolder {

        TextView messages, messageTime;

        public senderViewHolder(@NonNull View itemView) {
            super(itemView);
            messages = itemView.findViewById(R.id.message);
            messageTime = itemView.findViewById(R.id.time);
        }
    }
    public static class receiverViewHolder extends RecyclerView.ViewHolder {

        TextView messages, messageTime;

        public receiverViewHolder(@NonNull View itemView) {
            super(itemView);
            messages = itemView.findViewById(R.id.message);
            messageTime = itemView.findViewById(R.id.time);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView messages, messageTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            messages = itemView.findViewById(R.id.message);
            messageTime = itemView.findViewById(R.id.time);

        }
    }
}
