package com.qtechnetworks.ptplatform.Controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.qtechnetworks.ptplatform.R;


public class ExerciseHRecordAdapter extends RecyclerView.Adapter<ExerciseHRecordAdapter.ViewHolder>  {

    private Context mContext;

    public ExerciseHRecordAdapter(Context mContext) {
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_exercise_record,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {

        return 5;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView recordDate, recordWeight, recordRepetition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recordDate=itemView.findViewById(R.id.date);
            recordWeight=itemView.findViewById(R.id.weight);
            recordRepetition=itemView.findViewById(R.id.repetition);
        }
    }

}
