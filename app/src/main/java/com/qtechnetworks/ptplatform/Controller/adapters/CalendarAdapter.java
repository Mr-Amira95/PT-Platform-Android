package com.qtechnetworks.ptplatform.Controller.adapters;

import android.app.Activity;
import android.content.Context;
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

import com.bumptech.glide.Glide;
import com.qtechnetworks.ptplatform.Model.Beans.CoachCalendarResults.Datum;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Fragment.ChooseCoachFragment;
import com.qtechnetworks.ptplatform.View.Fragment.ContactFragment;
import com.qtechnetworks.ptplatform.View.Fragment.FollowUsFragment;
import com.qtechnetworks.ptplatform.View.Fragment.NewsFragment;

import java.util.ArrayList;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {

    // Done

    private Context context;
    private ArrayList<Datum> data;

    public CalendarAdapter(Context context, ArrayList<Datum> data) {
        this.context=context;
        this.data=data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_calendar_coach,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarAdapter.ViewHolder holder, int position) {

        Datum current = data.get(position);

        holder.name.setText(current.getUserName());
        holder.date.setText(current.getDate());
        holder.time.setText(current.getTime());
        holder.status.setText(current.getStatus());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name, date, time, status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            status = itemView.findViewById(R.id.status);
        }
    }

}
