package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

import com.qtechnetworks.ptplatform.Controller.adapters.ChestAndBicepsAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.Exercises.Exercise;
import com.qtechnetworks.ptplatform.Model.Beans.Exercises.GroupResults;
import com.qtechnetworks.ptplatform.Model.Beans.Exercises.SearchResults;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;

import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class SearchFragment extends Fragment implements CallBack {

    private Button searchBtn;
    private EditText searchEdittext;
    private RecyclerView searchRecyclerview;

    private String flag, word;

    public SearchFragment(String flag, String word) {
        this.flag = flag;
        this.word = word;
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        initials(view);
        clicks();

        // Inflate the layout for this fragment
        return view;
    }

    private void clicks() {

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PreferencesUtils.getUserType().equalsIgnoreCase("coach")){
                    if (!searchEdittext.getText().toString().isEmpty()) {
                        if (flag.equalsIgnoreCase("Exercises"))
                            searchExerciseCoach(searchEdittext.getText().toString());
                        else if (flag.equalsIgnoreCase("workouts"))
                            searchExerciseCoach(searchEdittext.getText().toString());
                    }
                } else {
                    if (!searchEdittext.getText().toString().isEmpty()) {
                        if (flag.equalsIgnoreCase("Exercises"))
                            searchExercise(searchEdittext.getText().toString());
                        else if (flag.equalsIgnoreCase("workouts"))
                            searchWorkout(searchEdittext.getText().toString());
                    }
                }


            }
        });
    }

    private void initials(View view) {
        searchBtn = view.findViewById(R.id.search_button);
        searchEdittext = view.findViewById(R.id.search_bar);
        searchRecyclerview = view.findViewById(R.id.search_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        searchRecyclerview.setLayoutManager(linearLayoutManager);

        searchEdittext.setText(word);

        if (PreferencesUtils.getUserType().equalsIgnoreCase("coach")) {
            if (flag.equalsIgnoreCase("Exercises"))
                searchExerciseCoach(word);
            else if (flag.equalsIgnoreCase("workouts"))
                searchWorkoutCoach(word);
        } else {
            if (flag.equalsIgnoreCase("Exercises"))
                searchExercise(word);
            else if (flag.equalsIgnoreCase("workouts"))
                searchWorkout(word);
        }
    }

    private void displaySearch(List<Exercise> data) {
        ChestAndBicepsAdapter adapter = new ChestAndBicepsAdapter(getContext(), data);
        searchRecyclerview.setAdapter(adapter);
    }

    private void searchExercise(String word) {

        HashMap<String ,Object> params = new HashMap<>();
        params.put("search", word);
        params.put("coach_id", PreferencesUtils.getCoach(getContext()).getId());

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.workout_URL, AppConstants.workout_TAG, SearchResults.class, params);

    }

    private void searchExerciseCoach(String word) {

        HashMap<String ,Object> params = new HashMap<>();
        params.put("skip","0");
        params.put("search", word);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.workout_URL, AppConstants.workout_TAG, SearchResults.class, params);

    }

    private void searchWorkout(String word) {

        HashMap<String ,Object> params = new HashMap<>();
        params.put("skip","0");
        params.put("search", word);
        params.put("coach_id", PreferencesUtils.getCoach(getContext()).getId());

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.exercise_URL, AppConstants.workout_TAG, SearchResults.class, params);

    }

    private void searchWorkoutCoach(String word) {

        HashMap<String ,Object> params = new HashMap<>();
        params.put("skip","0");
        params.put("search", word);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.exercise_URL, AppConstants.workout_TAG, SearchResults.class, params);

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        if (isSuccess){
            SearchResults searchResults = (SearchResults) result;

            if(searchResults.getData().size()>0) {
                displaySearch(searchResults.getData());
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