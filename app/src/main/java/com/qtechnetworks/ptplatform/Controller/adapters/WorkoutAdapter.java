package com.qtechnetworks.ptplatform.Controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qtechnetworks.ptplatform.Model.Beans.workout.*;
import com.qtechnetworks.ptplatform.R;

import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.ViewHolder>{

    private Context context;
    List<Category> data;
    RecyclerView exercise_recyclerview;

    public WorkoutAdapter(Context context, List<Category> data) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_exircises,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Category current= data.get(position);

        if (current.getTitle() != null)
            holder.title_text.setText(current.getTitle().toString());

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(context);
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        exercise_recyclerview.setLayoutManager(linearLayoutManager3);

        ChestAndBicepsWorkoutAdapter adapter = new ChestAndBicepsWorkoutAdapter (context,current.getExercises());
        exercise_recyclerview.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title_text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title_text=itemView.findViewById(R.id.title_text);
            exercise_recyclerview=itemView.findViewById(R.id.exercise_recyclerview);
        }
    }

}
