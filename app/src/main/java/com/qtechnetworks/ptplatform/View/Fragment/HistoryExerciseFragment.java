package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.qtechnetworks.ptplatform.Controller.adapters.ExerciseHRecordAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.ExerciseHistoryAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.VideoItemAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.WorkoutHistoryAdapter;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Dialogs.AddLogDialog;

import java.util.ArrayList;

public class HistoryExerciseFragment extends Fragment {

    RecyclerView exercisesRecyclerview;

    public HistoryExerciseFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_exercise_history, container, false);

        initial(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void initial(View view) {

        exercisesRecyclerview = view.findViewById(R.id.exercises_recyclerview);

        LinearLayoutManager layoutManagerhorizantalleader = new LinearLayoutManager(getContext());
        layoutManagerhorizantalleader.setOrientation(LinearLayoutManager.VERTICAL);
        exercisesRecyclerview.setLayoutManager(layoutManagerhorizantalleader);

        ExerciseHistoryAdapter exerciseHistoryAdapter = new ExerciseHistoryAdapter(getContext());
        exercisesRecyclerview.setAdapter(exerciseHistoryAdapter);
    }

}