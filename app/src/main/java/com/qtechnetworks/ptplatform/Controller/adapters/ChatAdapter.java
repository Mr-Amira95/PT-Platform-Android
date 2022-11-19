package com.qtechnetworks.ptplatform.Controller.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.impl.model.Preference;
import androidx.work.impl.utils.PreferenceUtils;

import com.bumptech.glide.Glide;
import com.qtechnetworks.ptplatform.Model.Beans.Chat.Datum;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Fragment.ChooseCoachFragment;
import com.qtechnetworks.ptplatform.View.Fragment.ContactFragment;
import com.qtechnetworks.ptplatform.View.Fragment.FollowUsFragment;
import com.qtechnetworks.ptplatform.View.Fragment.NewsFragment;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Datum> data;

    public ChatAdapter(Context context, ArrayList<Datum> data) {
        this.context=context;
        this.data=data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chat,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {

        Datum current = data.get(position);
        holder.message.setText(current.getMessage());

        if (current.getSenderId() == PreferencesUtils.getUser(context).getId()){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(100,0,0,0);
            params.weight = 1.0f;
            params.gravity = Gravity.END;
            holder.message.setLayoutParams(params);

            holder.message.setGravity(Gravity.END);
            holder.message.setBackgroundResource(R.drawable.background_radius_20_title);

        } else {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,0,100,0);
            holder.message.setLayoutParams(params);
            params.weight = 1.0f;
            params.gravity = Gravity.START;

            holder.message.setGravity(Gravity.START);
            holder.message.setBackgroundResource(R.drawable.background_radius_20);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.message);

        }
    }

}