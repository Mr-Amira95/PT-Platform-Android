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
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.Exercises.Exercise;
import com.qtechnetworks.ptplatform.Model.Beans.News.News;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.R;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class ExercisesWorkoutFragment extends Fragment implements CallBack {

    RecyclerView categoryRecyclerView, chestRecyclerview, bicepsRecyclerview, crossFitRecyclerview, homeWorkoutsRecyclerview;
    ChestAndBicepsAdapter adapter;
    TitleAdapter titleAdapter;
    TextView title, chestCrosTxt, bicepsHomeWorkoutsTxt;

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
            getWorkout(coachid);
        } else if (flag.equals("Exercises")){
            chestCrosTxt.setText("Chest");
            bicepsHomeWorkoutsTxt.setText("Biceps");
            getExercises(coachid);
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



    }

    private void getExercises(String coachid){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("coach_id",coachid);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.section_exercise_URL, AppConstants.section_exercise_TAG, Exercise.class, params);

    }


    private void getWorkout(String coachid){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("coach_id",coachid);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.section_exercise_URL, AppConstants.section_exercise_TAG, Exercise.class, params);

    }



    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        Exercise exercise=(Exercise) result;

        adapter = new ChestAndBicepsAdapter(getContext(), flag);
        chestRecyclerview.setAdapter(adapter);
        bicepsRecyclerview.setAdapter(adapter);

        titleAdapter = new TitleAdapter(getContext(), flag);
        categoryRecyclerView.setAdapter(titleAdapter);


    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}