package com.qtechnetworks.ptplatform.View.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.qtechnetworks.ptplatform.Controller.adapters.LogAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.FavoriteandWorkout.FavoriteandWorkout;
import com.qtechnetworks.ptplatform.Model.Beans.FoodHome.Foodhome;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class HistoryWorkoutFragment extends Fragment implements CallBack {

    RecyclerView workoutRecyclerview;
    TextView selectedDate, title;
    DatePickerDialog StartTime;

    String flag;

    public HistoryWorkoutFragment(String flag) {
        this.flag = flag;
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

        if (flag.equalsIgnoreCase("Workout"))
            getWorkoutHistory(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        else if (flag.equalsIgnoreCase("Exercises"))
            getExercisesHistory(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        // Inflate the layout for this fragment
        return view;
    }

    private void clicks() {

        selectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartTime.show();
            }
        });

    }

    private void initial(View view) {
        title= view.findViewById(R.id.title);
        workoutRecyclerview= view.findViewById(R.id.workout_recyclerview);
        selectedDate= view.findViewById(R.id.selected_date);

        if (flag.equalsIgnoreCase("Workout"))
            title.setText("Workouts History");
        else if (flag.equalsIgnoreCase("Exercises"))
            title.setText("Exercises History");

        GridLayoutManager layoutManagerhorizantalleader = new GridLayoutManager(getContext(), 2);
        layoutManagerhorizantalleader.setOrientation(LinearLayoutManager.VERTICAL);
        workoutRecyclerview.setLayoutManager(layoutManagerhorizantalleader);

        final Calendar newCalendar = Calendar.getInstance();
        StartTime = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                selectedDate.setText(year + "-" +(monthOfYear+1) + "-" + dayOfMonth);

                if (flag.equalsIgnoreCase("Workout"))
                    getWorkoutHistory(year + "-" +(monthOfYear+1) + "-" + dayOfMonth);
                else if (flag.equalsIgnoreCase("Exercises"))
                    getExercisesHistory(year + "-" +(monthOfYear+1) + "-" + dayOfMonth);
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void getWorkoutHistory(String date) {

        HashMap<String ,Object> params=new HashMap<>();

        params.put("date",date);
        params.put("skip","0");
        params.put("coach_id", PreferencesUtils.getCoach(getContext()).getId());

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.Add_Workout_URL, AppConstants.Add_Workout_TAG, FavoriteandWorkout.class, params);

    }

    private void getExercisesHistory(String date) {

        HashMap<String ,Object> params=new HashMap<>();

        params.put("date",date);
        params.put("skip","0");
        params.put("coach_id", PreferencesUtils.getCoach(getContext()).getId());

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.Add_Log_URL, AppConstants.Add_Log_TAG, FavoriteandWorkout.class, params);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        if (isSuccess) {

            FavoriteandWorkout favoriteandWorkout = (FavoriteandWorkout) result;

            if (favoriteandWorkout.getData().size() > 0){
                LogAdapter logAdapter = new LogAdapter(getContext(), favoriteandWorkout.getData());
                workoutRecyclerview.setAdapter(logAdapter);
            } else {
                LogAdapter logAdapter = new LogAdapter(getContext(), favoriteandWorkout.getData());
                workoutRecyclerview.setAdapter(logAdapter);
                Toast.makeText(getContext(), "There are no videos to show", Toast.LENGTH_SHORT).show();
            }
        } else {

        }

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}