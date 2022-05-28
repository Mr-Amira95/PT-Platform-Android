package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qtechnetworks.ptplatform.Controller.adapters.NewsAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.WorkoutHistoryAdapter;
import com.qtechnetworks.ptplatform.R;

import java.util.ArrayList;

public class HistoryWorkoutFragment extends Fragment {

    RecyclerView workoutRecyclerview;
    TextView sun, mon, tue, wed, thu, fri, sat;
    ArrayList <TextView> daysList = new ArrayList<>();

    public HistoryWorkoutFragment() {
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
        View view = inflater.inflate(R.layout.fragment_history_workout, container, false);

        initial(view);
        clicks();

        // Inflate the layout for this fragment
        return view;
    }

    private void clicks() {
        sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBackground(0);
            }
        });

        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBackground(1);
            }
        });

        tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBackground(2);
            }
        });

        wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBackground(3);
            }
        });

        thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBackground(4);
            }
        });

        fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBackground(5);
            }
        });

        sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBackground(6);
            }
        });

    }

    private void setBackground(int index) {
        for (int i=0; i<daysList.size(); i++){
            if ( i == index){
                daysList.get(i).setBackgroundResource(R.drawable.button_background);
            } else {
                daysList.get(i).setBackgroundResource(R.color.tran);
            }
        }
    }

    private void initial(View view) {
        workoutRecyclerview= view.findViewById(R.id.workout_recyclerview);

        sun = view.findViewById(R.id.sun);
        mon = view.findViewById(R.id.mon);
        tue = view.findViewById(R.id.tue);
        wed = view.findViewById(R.id.wed);
        thu = view.findViewById(R.id.thu);
        fri = view.findViewById(R.id.fri);
        sat = view.findViewById(R.id.sat);

        daysList.add(sun);
        daysList.add(mon);
        daysList.add(tue);
        daysList.add(wed);
        daysList.add(thu);
        daysList.add(fri);
        daysList.add(sat);

        LinearLayoutManager layoutManagerhorizantalleader = new LinearLayoutManager(getContext());
        layoutManagerhorizantalleader.setOrientation(LinearLayoutManager.VERTICAL);
        workoutRecyclerview.setLayoutManager(layoutManagerhorizantalleader);

        WorkoutHistoryAdapter workoutHistoryAdapter = new WorkoutHistoryAdapter(getContext());
        workoutRecyclerview.setAdapter(workoutHistoryAdapter);

    }

}