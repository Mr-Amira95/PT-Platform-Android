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
import com.qtechnetworks.ptplatform.Controller.adapters.WorkoutAndExsircisesAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.Exercises.Exercise;
import com.qtechnetworks.ptplatform.Model.Beans.Exercises.Exercises;
import com.qtechnetworks.ptplatform.Model.Beans.News.News;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.R;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class ExercisesWorkoutFragment extends Fragment implements CallBack {

    RecyclerView categoryRecyclerView,home_exir_work_recyclerview;

    WorkoutAndExsircisesAdapter workoutAndExsircisesAdapter;

    TitleAdapter titleAdapter;
    TextView title;

    String flag = "";

    String coachid;

    public ExercisesWorkoutFragment(String flag) {
        this.flag = flag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            coachid=getArguments().getString("coachid");
        }
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
        home_exir_work_recyclerview= view.findViewById(R.id.home_exir_work_recyclerview);

        categoryRecyclerView= view.findViewById(R.id.category_recyclerView);

        title= view.findViewById(R.id.title);
        title.setText(flag);

        if (flag.equals("Workout")){

            getWorkout(coachid);

        } else if (flag.equals("Exercises")){

            getExercises(coachid);

        }


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        home_exir_work_recyclerview.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(linearLayoutManager3);



    }

    private void getExercises(String coachid){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("coach_id",coachid);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.section_exercise_URL, AppConstants.section_exercise_TAG, Exercises.class, params);

    }


    private void getWorkout(String coachid){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("coach_id",coachid);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.section_workout_URL, AppConstants.section_workout_TAG, Exercises.class, params);

    }



    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        Exercises exercise=(Exercises) result;

        WorkoutAndExsircisesAdapter workoutAndExsircisesAdapter=new WorkoutAndExsircisesAdapter(getContext(),flag,exercise.getData().get(0).getCategory());
        home_exir_work_recyclerview.setAdapter(workoutAndExsircisesAdapter);

        titleAdapter = new TitleAdapter(getContext(), flag,exercise.getData(),home_exir_work_recyclerview);
        categoryRecyclerView.setAdapter(titleAdapter);


    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}