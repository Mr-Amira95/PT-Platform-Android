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
import com.qtechnetworks.ptplatform.Controller.adapters.NutritionHistoryAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.TitleAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.WorkoutHistoryAdapter;
import com.qtechnetworks.ptplatform.R;

import java.util.ArrayList;
import java.util.List;

public class ProgressFragment extends Fragment {

    private TextView weightTitle, weightValue, muscleTitle, muscleValue, fatTitle, fatValue, waterTitle, waterValue, activeCaloriesTitle, activeCaloriesValue, stepsTitle, stepsValue;
    private TextView sunWorkout, monWorkout, tueWorkout, wedWorkout, thuWorkout, friWorkout, satWorkout;
    private TextView sunNutrition, monNutrition, tueNutrition, wedNutrition, thuNutrition, friNutrition, satNutrition;
    private RecyclerView workoutRecyclerView, nutritionRecyclerview;
    private WorkoutHistoryAdapter workoutHistoryAdapter;
    private NutritionHistoryAdapter nutritionHistoryAdapter;

    private List<TextView> workoutDays, nutritionDays;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);

        initials(view);

        weightTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (weightValue.getVisibility() == View.GONE)
                    weightValue.setVisibility(View.VISIBLE);
                else
                    weightValue.setVisibility(View.GONE);
            }
        });

        muscleTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (muscleValue.getVisibility() == View.GONE)
                    muscleValue.setVisibility(View.VISIBLE);
                else
                    muscleValue.setVisibility(View.GONE);
            }
        });

        fatTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fatValue.getVisibility() == View.GONE)
                    fatValue.setVisibility(View.VISIBLE);
                else
                    fatValue.setVisibility(View.GONE);
            }
        });

        waterTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (waterValue.getVisibility() == View.GONE)
                    waterValue.setVisibility(View.VISIBLE);
                else
                    waterValue.setVisibility(View.GONE);
            }
        });

        activeCaloriesTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activeCaloriesValue.getVisibility() == View.GONE)
                    activeCaloriesValue.setVisibility(View.VISIBLE);
                else
                    activeCaloriesValue.setVisibility(View.GONE);
            }
        });

        stepsTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stepsValue.getVisibility() == View.GONE)
                    stepsValue.setVisibility(View.VISIBLE);
                else
                    stepsValue.setVisibility(View.GONE);
            }
        });

        workoutDays.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0; i<workoutDays.size(); i++){
                    if (i == 0){
                        workoutDays.get(i).setBackgroundResource(R.drawable.button_background);
                    } else {
                        workoutDays.get(i).setBackgroundResource(R.drawable.background_empty);
                    }
                }
            }
        });

        workoutDays.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0; i<workoutDays.size(); i++){
                    if (i == 1){
                        workoutDays.get(i).setBackgroundResource(R.drawable.button_background);
                    } else {
                        workoutDays.get(i).setBackgroundResource(R.drawable.background_empty);
                    }
                }
            }
        });

        workoutDays.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0; i<workoutDays.size(); i++){
                    if (i == 2){
                        workoutDays.get(i).setBackgroundResource(R.drawable.button_background);
                    } else {
                        workoutDays.get(i).setBackgroundResource(R.drawable.background_empty);
                    }
                }
            }
        });

        workoutDays.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0; i<workoutDays.size(); i++){
                    if (i == 3){
                        workoutDays.get(i).setBackgroundResource(R.drawable.button_background);
                    } else {
                        workoutDays.get(i).setBackgroundResource(R.drawable.background_empty);
                    }
                }
            }
        });

        workoutDays.get(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0; i<workoutDays.size(); i++){
                    if (i == 4){
                        workoutDays.get(i).setBackgroundResource(R.drawable.button_background);
                    } else {
                        workoutDays.get(i).setBackgroundResource(R.drawable.background_empty);
                    }
                }
            }
        });

        workoutDays.get(5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0; i<workoutDays.size(); i++){
                    if (i == 5){
                        workoutDays.get(i).setBackgroundResource(R.drawable.button_background);
                    } else {
                        workoutDays.get(i).setBackgroundResource(R.drawable.background_empty);
                    }
                }
            }
        });

        workoutDays.get(6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0; i<workoutDays.size(); i++){
                    if (i == 6){
                        workoutDays.get(i).setBackgroundResource(R.drawable.button_background);
                    } else {
                        workoutDays.get(i).setBackgroundResource(R.drawable.background_empty);
                    }
                }
            }
        });

        nutritionDays.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0; i<nutritionDays.size(); i++){
                    if (i == 0){
                        nutritionDays.get(i).setBackgroundResource(R.drawable.button_background);
                    } else {
                        nutritionDays.get(i).setBackgroundResource(R.drawable.background_empty);
                    }
                }
            }
        });

        nutritionDays.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0; i<nutritionDays.size(); i++){
                    if (i == 1){
                        nutritionDays.get(i).setBackgroundResource(R.drawable.button_background);
                    } else {
                        nutritionDays.get(i).setBackgroundResource(R.drawable.background_empty);
                    }
                }
            }
        });

        nutritionDays.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0; i<nutritionDays.size(); i++){
                    if (i == 2){
                        nutritionDays.get(i).setBackgroundResource(R.drawable.button_background);
                    } else {
                        nutritionDays.get(i).setBackgroundResource(R.drawable.background_empty);
                    }
                }
            }
        });

        nutritionDays.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0; i<nutritionDays.size(); i++){
                    if (i == 3){
                        nutritionDays.get(i).setBackgroundResource(R.drawable.button_background);
                    } else {
                        nutritionDays.get(i).setBackgroundResource(R.drawable.background_empty);
                    }
                }
            }
        });

        nutritionDays.get(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0; i<nutritionDays.size(); i++){
                    if (i == 4){
                        nutritionDays.get(i).setBackgroundResource(R.drawable.button_background);
                    } else {
                        nutritionDays.get(i).setBackgroundResource(R.drawable.background_empty);
                    }
                }
            }
        });

        nutritionDays.get(5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0; i<nutritionDays.size(); i++){
                    if (i == 5){
                        nutritionDays.get(i).setBackgroundResource(R.drawable.button_background);
                    } else {
                        nutritionDays.get(i).setBackgroundResource(R.drawable.background_empty);
                    }
                }
            }
        });

        nutritionDays.get(6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0; i<nutritionDays.size(); i++){
                    if (i == 6){
                        nutritionDays.get(i).setBackgroundResource(R.drawable.button_background);
                    } else {
                        nutritionDays.get(i).setBackgroundResource(R.drawable.background_empty);
                    }
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void initials(View view) {
        weightTitle = view.findViewById(R.id.weight_title);
        weightValue = view.findViewById(R.id.weight_value);
        muscleTitle = view.findViewById(R.id.muscle_title);
        muscleValue = view.findViewById(R.id.muscle_value);
        fatTitle = view.findViewById(R.id.fat_title);
        fatValue = view.findViewById(R.id.fat_value);
        waterTitle = view.findViewById(R.id.water_title);
        waterValue = view.findViewById(R.id.water_value);
        activeCaloriesTitle = view.findViewById(R.id.active_calories_title);
        activeCaloriesValue = view.findViewById(R.id.active_calories_value);
        stepsTitle = view.findViewById(R.id.steps_title);
        stepsValue = view.findViewById(R.id.steps_value);

        workoutRecyclerView = view.findViewById(R.id.workout_history_recyclerview);
        nutritionRecyclerview = view.findViewById(R.id.nutrition_history_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        workoutRecyclerView.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        nutritionRecyclerview.setLayoutManager(linearLayoutManager1);

        workoutHistoryAdapter = new WorkoutHistoryAdapter(getContext());
        workoutRecyclerView.setAdapter(workoutHistoryAdapter);

        nutritionHistoryAdapter = new NutritionHistoryAdapter(getContext());
        nutritionRecyclerview.setAdapter(nutritionHistoryAdapter);

        workoutDays = new ArrayList<>();
        sunWorkout = view.findViewById(R.id.sunday);
        workoutDays.add(sunWorkout);
        monWorkout = view.findViewById(R.id.monday);
        workoutDays.add(monWorkout);
        tueWorkout = view.findViewById(R.id.tuesday);
        workoutDays.add(tueWorkout);
        wedWorkout = view.findViewById(R.id.wednesday);
        workoutDays.add(wedWorkout);
        thuWorkout = view.findViewById(R.id.thursday);
        workoutDays.add(thuWorkout);
        friWorkout = view.findViewById(R.id.friday);
        workoutDays.add(friWorkout);
        satWorkout = view.findViewById(R.id.saterday);
        workoutDays.add(satWorkout);

        nutritionDays = new ArrayList<>();
        sunNutrition = view.findViewById(R.id.sunday_nutrition);
        nutritionDays.add(sunNutrition);
        monNutrition = view.findViewById(R.id.monday_nutrition);
        nutritionDays.add(monNutrition);
        tueNutrition = view.findViewById(R.id.tuesday_nutrition);
        nutritionDays.add(tueNutrition);
        wedNutrition = view.findViewById(R.id.wednesday_nutrition);
        nutritionDays.add(wedNutrition);
        thuNutrition = view.findViewById(R.id.thursday_nutrition);
        nutritionDays.add(thuNutrition);
        friNutrition = view.findViewById(R.id.friday_nutrition);
        nutritionDays.add(friNutrition);
        satNutrition = view.findViewById(R.id.saterday_nutrition);
        nutritionDays.add(satNutrition);
    }
}