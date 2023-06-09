package com.company.ptplatform.Controller.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.company.ptplatform.Model.Beans.calender.Datum;
import com.company.ptplatform.R;
import com.company.ptplatform.View.Fragment.CalendarFragment;

import java.util.ArrayList;
import java.util.List;

public class CoachTimesAdapter extends RecyclerView.Adapter<CoachTimesAdapter.ViewHolder>  {

    private Context context;
    private List<Datum> datum;
    private List<TextView> textViewList = new ArrayList<>();


    public CoachTimesAdapter(Context context, List<Datum> datum) {

        this.datum = datum;
        this.context=context;
    }

    @NonNull
    @Override
    public CoachTimesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_title,parent,false);

        return new CoachTimesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoachTimesAdapter.ViewHolder holder, int position) {

        Datum current= datum.get(position);

        holder.title.setText(current.getTime());

        if(current.getIsAvailable()){
            holder.title.setTextColor(Color.WHITE);
        } else {
            holder.title.setBackgroundResource(R.drawable.background_empty);
            holder.title.setTextColor(Color.RED);
        }

        textViewList.add(holder.title);

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i=0; i<textViewList.size(); i++) {
                    textViewList.get(i).setBackgroundResource(R.drawable.background_empty);
                }

                if (current.getIsAvailable()){
                    CalendarFragment.SELECTED_TIME = current.getId();
                    CalendarFragment.SELECTED_TIME_STRING=  current.getTime();
                    holder.title.setBackgroundResource(R.drawable.background_radius_20_title);
                } else {
                    CalendarFragment.SELECTED_TIME = null;
                    CalendarFragment.SELECTED_TIME_STRING=  "";
                }
            }
        });
    }

    private void setFragment(int frameLayout, Fragment fragment, AppCompatActivity activity) {
        FragmentTransaction fragmentTransaction= activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout, fragment).commit();
    }

    @Override
    public int getItemCount() {
        return datum.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title);

        }
    }

}
