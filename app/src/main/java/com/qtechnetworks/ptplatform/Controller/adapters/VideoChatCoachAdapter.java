package com.qtechnetworks.ptplatform.Controller.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.qtechnetworks.ptplatform.Model.Beans.VideoChat.Datum;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;
import com.qtechnetworks.ptplatform.View.Fragment.NewsSingleFragment;

import java.util.List;


public class VideoChatCoachAdapter extends RecyclerView.Adapter<VideoChatCoachAdapter.ViewHolder>  {

    private Context context;
    private List<Datum> data;

    public VideoChatCoachAdapter(Context context, List<Datum> data) {

        this.data = data;
        this.context=context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_video_chat_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Datum current= data.get(position);

        holder.cancelSessionBtn.setVisibility(View.INVISIBLE);
        holder.sessionName.setText(current.getCoachTimeReservation().getUser().getFirstName());
        holder.dateTime.setText(current.getDate() + " / " + current.getTime());

        holder.joinSessionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(current.getCoachTimeReservation().getZoom().getData().getStartUrl()));
                try{
                    context.startActivity(intent);}
                catch(Exception e) {
                    Toast.makeText(view.getContext(), "Can't start Video chat", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView sessionName, dateTime;
        public Button joinSessionBtn, cancelSessionBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sessionName=itemView.findViewById(R.id.session_name);
            joinSessionBtn=itemView.findViewById(R.id.join_session_btn);
            dateTime=itemView.findViewById(R.id.session_date_time);
            cancelSessionBtn=itemView.findViewById(R.id.cancel_session_btn);

        }
    }
}
