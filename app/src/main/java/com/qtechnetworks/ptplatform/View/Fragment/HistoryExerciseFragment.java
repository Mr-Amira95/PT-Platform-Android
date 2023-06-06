package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.qtechnetworks.ptplatform.Controller.adapters.ExerciseHistoryAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.WorkoutHistory.WorkoutHistoryResults;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class HistoryExerciseFragment extends Fragment implements CallBack {

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

        if (PreferencesUtils.getUserType().equalsIgnoreCase("trainee"))
            getExercisesTrainee();
        else if (PreferencesUtils.getUserType().equalsIgnoreCase("coach"))
            getExercisesCoach();

        //Inflate the layout for this fragment
        return view;
    }

    private void getExercisesCoach() {
        HashMap<String ,Object> params=new HashMap<>();

        params.put("user_id", HistoryFragment.userID);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.Add_Log_URL, AppConstants.Add_Log_TAG, WorkoutHistoryResults.class, params);
    }

    private void getExercisesTrainee() {
        HashMap<String ,Object> params=new HashMap<>();

        params.put("coach_id", PreferencesUtils.getCoach(getContext()).getId());

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.Add_Log_URL, AppConstants.Add_Log_TAG, WorkoutHistoryResults.class, params);
    }

    private void initial (View view) {

        exercisesRecyclerview = view.findViewById(R.id.exercises_recyclerview);

        LinearLayoutManager layoutManagerhorizantalleader = new LinearLayoutManager(getContext());
        layoutManagerhorizantalleader.setOrientation(LinearLayoutManager.VERTICAL);
        exercisesRecyclerview.setLayoutManager(layoutManagerhorizantalleader);

    }



    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        if (isSuccess) {

            WorkoutHistoryResults workoutHistoryResults = (WorkoutHistoryResults) result;

            ExerciseHistoryAdapter exerciseHistoryAdapter = new ExerciseHistoryAdapter(getContext(), workoutHistoryResults);
            exercisesRecyclerview.setAdapter(exerciseHistoryAdapter);

            if (workoutHistoryResults.getData().size() == 0) {
                Toast.makeText(getContext(), R.string.there_are_no_exercises_to_show, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}