package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;

public class HistoryFragment extends Fragment {

    Button workoutHistory, exerciseHistory, nutritionHistory;
    public static String userID;

    public HistoryFragment() {
        // Required empty public constructor
    }

    public HistoryFragment(String userID) {
        this.userID = userID;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        initials(view);
        click();


        // Inflate the layout for this fragment
        return view;
    }

    private void click() {
        workoutHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setFragment(new HistoryWorkoutFragment());

            }
        });

        exerciseHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setFragment(new HistoryExerciseFragment());
            }
        });

        nutritionHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setFragment(new HistoryNutritionFragment());

            }
        });

    }

    private void setFragment(Fragment fragment) {

        Bundle args = new Bundle();
        fragment.setArguments(args);

        ((MainActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").addToBackStack(null).commit();

    }

    private void initials (View view) {
        workoutHistory = view.findViewById(R.id.workout_histry);
        exerciseHistory = view.findViewById(R.id.exercise_history);
        nutritionHistory = view.findViewById(R.id.nutrition_histrory);
    }
}