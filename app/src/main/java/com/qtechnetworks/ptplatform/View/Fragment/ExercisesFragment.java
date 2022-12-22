package com.qtechnetworks.ptplatform.View.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qtechnetworks.ptplatform.Controller.adapters.TitleAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.ExercisesAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.Exercises.Datum;
import com.qtechnetworks.ptplatform.Model.Beans.Exercises.GroupResults;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class ExercisesFragment extends Fragment implements CallBack {

    RecyclerView categoryRecyclerView;
    public static RecyclerView home_exir_work_recyclerview;

    TitleAdapter titleAdapter;
    TextView title;
    Button searchBtn;
    EditText searchBar;

    List<Datum> exdata;

    String coachid;
    public static int groupID;

    public static GroupResults exercise;
    public static ExercisesAdapter exercisesAdapter;

    public static int counter = 0;

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
        clicks();

        // Inflate the layout for this fragment
        return view;
    }

    private void clicks() {

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!searchBar.getText().toString().isEmpty()) {
                    setFragment(new SearchFragment("Exercises", searchBar.getText().toString()));
                } else {
                    Toast.makeText(getContext(), R.string.please_write_what_you_are_looking_for, Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    private void initial(View view) {

        searchBar = view.findViewById(R.id.search_bar);
        searchBtn = view.findViewById(R.id.search_button);

        home_exir_work_recyclerview = view.findViewById(R.id.home_exir_work_recyclerview);

        categoryRecyclerView = view.findViewById(R.id.category_recyclerView);

        exdata=new ArrayList<>();

        title= view.findViewById(R.id.title);

        getExercises(coachid);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        home_exir_work_recyclerview.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(linearLayoutManager3);

    }

    private void getExercises(String coachid){

        HashMap<String ,Object> params = new HashMap<>();

        params.put("coach_id", coachid);
        params.put("skip", counter);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.section_exercise_URL, AppConstants.section_exercise_TAG, GroupResults.class, params);

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    public static void setItems(String groupID, Context context){

        for (int i=0; i<exercise.getData().size(); i++){
            if (groupID.equalsIgnoreCase(exercise.getData().get(i).getId().toString())){
                exercisesAdapter = new ExercisesAdapter(context,exercise.getData().get(i).getCategory());
                home_exir_work_recyclerview.setAdapter(exercisesAdapter);
            }
        }

    }

    public void setGroupName(){
        titleAdapter = new TitleAdapter(getContext(), exercise.getData());
        categoryRecyclerView.setAdapter(titleAdapter);
    }

    private void setFragment(Fragment fragment) {

        ((MainActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").addToBackStack(null).commit();

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        if(isSuccess) {

            exercise = (GroupResults) result;
            counter += exercise.getData().size();

            if(exercise.getData().size()>0) {
                groupID = exercise.getData().get(0).getId();
                setGroupName();
                setItems(String.valueOf(groupID), getContext());
            } else {
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