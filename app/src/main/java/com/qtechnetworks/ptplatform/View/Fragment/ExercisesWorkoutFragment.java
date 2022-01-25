package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qtechnetworks.ptplatform.Controller.adapters.ChestAndBicepsAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.TitleAdapter;
import com.qtechnetworks.ptplatform.R;

public class ExercisesWorkoutFragment extends Fragment {

    RecyclerView categoryRecyclerView, chestRecyclerview, bicepsRecyclerview, crossFitRecyclerview, homeWorkoutsRecyclerview;
    ChestAndBicepsAdapter adapter;
    TitleAdapter titleAdapter;
    TextView title, chestCrosTxt, bicepsHomeWorkoutsTxt;

    String flag = "";

    public ExercisesWorkoutFragment(String flag) {
        this.flag = flag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercises_workouts, container, false);

        initial(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void initial(View view) {
        chestRecyclerview= view.findViewById(R.id.chest_cross_recyclerview);
        bicepsRecyclerview= view.findViewById(R.id.biceps_homeworkout_recyclerview);

        chestCrosTxt = view.findViewById(R.id.chest_cross_title);
        bicepsHomeWorkoutsTxt = view.findViewById(R.id.biceps_homeworkout_title);
        categoryRecyclerView= view.findViewById(R.id.category_recyclerView);

        title= view.findViewById(R.id.title);
        title.setText(flag);

        if (flag.equals("Workout")){
            chestCrosTxt.setText("Cross Fit");
            bicepsHomeWorkoutsTxt.setText("Home Workouts");
        } else if (flag.equals("Exercises")){
            chestCrosTxt.setText("Chest");
            bicepsHomeWorkoutsTxt.setText("Biceps");
        }

        LinearLayoutManager layoutManagerhorizantalleader = new LinearLayoutManager(getContext());
        layoutManagerhorizantalleader.setOrientation(LinearLayoutManager.HORIZONTAL);
        chestRecyclerview.setLayoutManager(layoutManagerhorizantalleader);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        bicepsRecyclerview.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(linearLayoutManager3);

        adapter = new ChestAndBicepsAdapter(getContext(), flag);
        chestRecyclerview.setAdapter(adapter);
        bicepsRecyclerview.setAdapter(adapter);

        titleAdapter = new TitleAdapter(getContext());
        categoryRecyclerView.setAdapter(titleAdapter);


    }

}