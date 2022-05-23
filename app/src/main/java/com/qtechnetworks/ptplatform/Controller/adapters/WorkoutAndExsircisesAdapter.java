package com.qtechnetworks.ptplatform.Controller.adapters;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.os.Bundle;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qtechnetworks.ptplatform.Model.Beans.Exercises.Category;
import com.qtechnetworks.ptplatform.Model.Beans.Exercises.Datum;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;
import com.qtechnetworks.ptplatform.View.Fragment.ExercisesSingleFragment;
import com.qtechnetworks.ptplatform.View.Fragment.PlansSingleFragment;
import com.qtechnetworks.ptplatform.View.Fragment.SupplementSingleFragment;
import com.qtechnetworks.ptplatform.View.Fragment.WorkoutSingleFragment;

import java.util.List;

public class WorkoutAndExsircisesAdapter extends RecyclerView.Adapter<WorkoutAndExsircisesAdapter.ViewHolder>  {

    private Context context;
    private String flag;
    List<Category> data;

    ChestAndBicepsAdapter adapter;


    public WorkoutAndExsircisesAdapter(Context context, String flag, List<Category> data) {

        this.context = context;
        this.flag = flag;
        this.data=data;
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

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(context);
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.exercise_recyclerview.setLayoutManager(linearLayoutManager3);

        adapter=new ChestAndBicepsAdapter(context,flag,current.getExercises());
        holder.exercise_recyclerview.setAdapter(adapter);

        holder.title_text.setText(current.getTitle().toString());


    }

    private void setFragment(Fragment fragment) {

        Bundle args = new Bundle();
        fragment.setArguments(args);

        ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").addToBackStack(null).commit();

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title_text;
        public RecyclerView exercise_recyclerview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title_text=itemView.findViewById(R.id.title_text);
            exercise_recyclerview=itemView.findViewById(R.id.exercise_recyclerview);
        }
    }

}
