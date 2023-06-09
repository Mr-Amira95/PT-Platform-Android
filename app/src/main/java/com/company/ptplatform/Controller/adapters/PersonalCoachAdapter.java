package com.company.ptplatform.Controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.company.ptplatform.Model.Beans.PersonalCoach.Datum;
import com.company.ptplatform.R;

import java.util.ArrayList;

public class PersonalCoachAdapter extends RecyclerView.Adapter<PersonalCoachAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Datum> data;

    public PersonalCoachAdapter(Context context, ArrayList<Datum> data) {
        this.context=context;
        this.data=data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_personal_coach,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonalCoachAdapter.ViewHolder holder, int position) {

        Datum current = data.get(position);

        holder.username.setText(current.getName());
        holder.endDate.setText(current.getEndDate());
        holder.startDate.setText(current.getStartDate());
        holder.packageName.setText(current.getPackageName());
        if (current.getType().equalsIgnoreCase("personal_training"))
            holder.packageType.setText( "(PT)");
        else
            holder.packageType.setText( "(" +current.getType() + ")");

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView username, endDate, startDate, packageName, packageType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            endDate = itemView.findViewById(R.id.end_date);
            startDate = itemView.findViewById(R.id.start_date);
            packageName = itemView.findViewById(R.id.package_name);
            packageType = itemView.findViewById(R.id.package_type);
        }
    }

}
