package com.qtechnetworks.ptplatform.Controller.adapters;

import static com.qtechnetworks.ptplatform.View.Fragment.FoodAddFragment.Foodname_spinner;
import static com.qtechnetworks.ptplatform.View.Fragment.FoodAddFragment.calories_text;
import static com.qtechnetworks.ptplatform.View.Fragment.FoodAddFragment.carb_text;
import static com.qtechnetworks.ptplatform.View.Fragment.FoodAddFragment.fat_text;
import static com.qtechnetworks.ptplatform.View.Fragment.FoodAddFragment.protine_text;
import static com.qtechnetworks.ptplatform.View.Fragment.FoodAddFragment.weightnumber_edit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.qtechnetworks.ptplatform.Model.Beans.Food.Datum;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Dialogs.FoodDialog;
import com.qtechnetworks.ptplatform.View.Fragment.FoodAddFragment;

import java.util.ArrayList;
import java.util.List;


public class ExerciseHistoryAdapter extends RecyclerView.Adapter<ExerciseHistoryAdapter.ViewHolder>  {

    private Context mContext;
    private ArrayList <RecyclerView> recyclerViews = new ArrayList<>();

    public ExerciseHistoryAdapter(Context mContext) {
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_exercise_history,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        recyclerViews.add(holder.exerciseRecords);

        ExerciseHRecordAdapter exerciseHRecordAdapter = new ExerciseHRecordAdapter(mContext);
        holder.exerciseRecords.setAdapter(exerciseHRecordAdapter);

        holder.exerciseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i =0; i<3; i++){
                    recyclerViews.get(i).setVisibility(View.GONE);
                }

                switch (holder.exerciseRecords.getVisibility()) {
                    case View.GONE:
                        holder.exerciseRecords.setVisibility(View.VISIBLE);
                        break;
                    case View.VISIBLE:
                        holder.exerciseRecords.setVisibility(View.GONE);
                        break;
                }

            }
        });
    }

    @Override
    public int getItemCount() {

        return 3;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public RoundedImageView exerciseImg;
        public TextView exerciseName;
        public RecyclerView exerciseRecords;
        public LinearLayout exerciseLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            exerciseImg=itemView.findViewById(R.id.exercise_icon);
            exerciseName=itemView.findViewById(R.id.exercise_name);
            exerciseRecords = itemView.findViewById(R.id.records_recyclerview);
            exerciseLayout=itemView.findViewById(R.id.exercise_layout);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            exerciseRecords.setLayoutManager(linearLayoutManager);


        }
    }

}
