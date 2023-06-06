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
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class HistoryWorkoutFragment extends Fragment implements CallBack {

    RecyclerView workoutRecyclerview;
    TextView selectedDate, title;
    DatePickerDialog StartTime;

    public HistoryWorkoutFragment() {

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

        if (PreferencesUtils.getUserType().equalsIgnoreCase("Trainee")) {
            getWorkoutHistoryTrainee(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        } else if (PreferencesUtils.getUserType().equalsIgnoreCase("coach")) {
            getWorkoutHistoryCoach(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        }

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

        GridLayoutManager layoutManagerhorizantalleader = new GridLayoutManager(getContext(), 2);
        layoutManagerhorizantalleader.setOrientation(LinearLayoutManager.VERTICAL);
        workoutRecyclerview.setLayoutManager(layoutManagerhorizantalleader);

        final Calendar newCalendar = Calendar.getInstance();
        StartTime = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                selectedDate.setText(year + "-" +(monthOfYear+1) + "-" + dayOfMonth);

                if (PreferencesUtils.getUserType().equalsIgnoreCase("Trainee")) {
                    getWorkoutHistoryTrainee(year + "-" +(monthOfYear+1) + "-" + dayOfMonth);
                } else if (PreferencesUtils.getUserType().equalsIgnoreCase("Trainee")) {
                    getWorkoutHistoryCoach(year + "-" +(monthOfYear+1) + "-" + dayOfMonth);
                }
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void getWorkoutHistoryTrainee(String date) {

        HashMap<String ,Object> params=new HashMap<>();

        params.put("date",date);
        params.put("skip","0");
        params.put("coach_id", PreferencesUtils.getCoach(getContext()).getId());

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.Add_Workout_URL, AppConstants.Add_Workout_TAG, FavoriteandWorkout.class, params);

    }

    private void getWorkoutHistoryCoach(String date) {

        HashMap<String ,Object> params=new HashMap<>();

        params.put("date",date);
        params.put("skip","0");
        params.put("user_id", HistoryFragment.userID);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.Add_Workout_URL, AppConstants.Add_Workout_TAG, FavoriteandWorkout.class, params);

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
                Toast.makeText(getContext(), R.string.there_are_no_videos_to_show, Toast.LENGTH_SHORT).show();
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