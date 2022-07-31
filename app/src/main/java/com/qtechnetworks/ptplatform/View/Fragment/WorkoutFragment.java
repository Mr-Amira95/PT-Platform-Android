package com.qtechnetworks.ptplatform.View.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.qtechnetworks.ptplatform.Controller.adapters.TitleAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.WorkoutAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.WorkoutAndExsircisesAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.WorkoutTitleAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.workout.Category;
import com.qtechnetworks.ptplatform.Model.Beans.workout.CategoryResults;
import com.qtechnetworks.ptplatform.Model.Beans.workout.workoutResults;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.EndlessRecyclerViewScrollListener;
import com.qtechnetworks.ptplatform.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class WorkoutFragment extends Fragment implements CallBack {

    RecyclerView categoryRecyclerView;
    public static RecyclerView home_exir_work_recyclerview;

    WorkoutTitleAdapter workoutTitleAdapter;
    TextView title;

    String coachid;

    public static int groupID = -1;

    public static workoutResults workoutResults;
    public static WorkoutAdapter workoutAdapter;

    public WorkoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            coachid=getArguments().getString("coachid");
            if (getArguments().getString("groupID") != null)
                groupID = Integer.parseInt(getArguments().getString("groupID")) ;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout, container, false);

        initial(view);
        getWorkout(coachid);

        // Inflate the layout for this fragment
        return view;
    }

    private void initial(View view) {
        home_exir_work_recyclerview= view.findViewById(R.id.home_exir_work_recyclerview);
        categoryRecyclerView= view.findViewById(R.id.category_recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        home_exir_work_recyclerview.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(linearLayoutManager3);


//        home_exir_work_recyclerview.addOnScrollListener(new EndlessRecyclerViewScrollListener( linearLayoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//
//                getCategoryWORK(String.valueOf(groupID),totalItemsCount);
//
//            }
//        });


    }

    private void getWorkout(String coachid){

        HashMap<String ,Object> params = new HashMap<>();
        params.put("coach_id",coachid);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.section_workout_URL, AppConstants.section_workout_TAG, workoutResults.class, params);

    }

    public void setGroupName(){
        workoutTitleAdapter = new WorkoutTitleAdapter (getContext(), workoutResults.getData());
        categoryRecyclerView.setAdapter(workoutTitleAdapter);
    }

    public static void setItems(Context context, List<Category> category){
        workoutAdapter = new WorkoutAdapter(context,category);
        home_exir_work_recyclerview.setAdapter(workoutAdapter);
    }


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        switch (tag) {
            case AppConstants.section_workout_TAG:
                workoutResults = (workoutResults) result;
                if (workoutResults.getData().size() > 0) {
                    groupID = workoutResults.getData().get(0).getId();
                    setItems(getContext(), workoutResults.getData().get(0).getCategory());
                    setGroupName();
                } else {
                    Toast.makeText(getContext(), "No Workouts", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}