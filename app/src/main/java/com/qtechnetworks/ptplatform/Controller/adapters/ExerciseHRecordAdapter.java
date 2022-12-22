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
import com.qtechnetworks.ptplatform.Model.Beans.Log.Datum;
import com.qtechnetworks.ptplatform.Model.Beans.Log.LogResults;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Dialogs.NoteDialog;


public class ExerciseHRecordAdapter extends RecyclerView.Adapter<ExerciseHRecordAdapter.ViewHolder>  {

    private Context mContext;
    private LogResults logResults;

    public ExerciseHRecordAdapter(Context mContext, LogResults logResults) {
        this.mContext=mContext;
        this.logResults=logResults;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_exercise_record,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Datum current = logResults.getData().get(position);

        holder.recordWeight.setText(String.valueOf(current.getWeight()) + " " + String.valueOf(current.getWeightUnit()));
        holder.recordDate.setText(String.valueOf(current.getCreatedAt()));
        holder.recordRepetition.setText(String.valueOf(current.getRepetition()));

        holder.note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteDialog noteDialog = new NoteDialog(mContext, current.getNote());
                noteDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return logResults.getData().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView recordDate, recordWeight, recordRepetition, note;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recordDate=itemView.findViewById(R.id.date);
            recordWeight=itemView.findViewById(R.id.weight);
            recordRepetition=itemView.findViewById(R.id.repetition);
            note=itemView.findViewById(R.id.note);
        }
    }

}
