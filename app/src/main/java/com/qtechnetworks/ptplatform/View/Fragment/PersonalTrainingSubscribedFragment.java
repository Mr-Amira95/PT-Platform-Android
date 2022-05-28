package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qtechnetworks.ptplatform.Controller.adapters.CoachAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.PersonalMealsHomeAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.PersonalWorkoutHomeAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.MealsPersonal.MealsPersonal;
import com.qtechnetworks.ptplatform.Model.Beans.WorkoutPersonal.WorkoutPersonal;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class PersonalTrainingSubscribedFragment extends Fragment implements CallBack {

    private ConstraintLayout noContentLayout;
    private LinearLayout contentLayout, homeCategoryLayout, PersonalizedCategoryLayout;
    private TextView homeCategoryTxt, personalizedCategoryTxt;
    boolean haveContent = true;

    private RecyclerView assignedWorkoutsRecyclerview, assignedMealsRecyclerview, videosRecyclerview, imagesRecyclerview, notesRecyclerview;
    private CoachAdapter adapter;

    int tag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_training_subscribed, container, false);

        initials(view);

        if (haveContent){
            noContentLayout.setVisibility(View.INVISIBLE);
            contentLayout.setVisibility(View.VISIBLE);
        } else {
            noContentLayout.setVisibility(View.VISIBLE);
            contentLayout.setVisibility(View.INVISIBLE);
        }

        personalizedCategoryTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeCategoryLayout.setVisibility(View.GONE);
                PersonalizedCategoryLayout.setVisibility(View.VISIBLE);
                personalizedCategoryTxt.setBackgroundResource(R.drawable.background_radius_20_title);
                homeCategoryTxt.setBackgroundResource(0);
            }
        });

        homeCategoryTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeCategoryLayout.setVisibility(View.VISIBLE);
                PersonalizedCategoryLayout.setVisibility(View.GONE);
                homeCategoryTxt.setBackgroundResource(R.drawable.background_radius_20_title);
                personalizedCategoryTxt.setBackgroundResource(0);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void initials(View view) {
        contentLayout = view.findViewById(R.id.content_layout);
        noContentLayout = view.findViewById(R.id.no_content_layout);

        homeCategoryLayout = view.findViewById(R.id.home_layout);
        PersonalizedCategoryLayout = view.findViewById(R.id.personalized_layout);

        homeCategoryTxt = view.findViewById(R.id.home_category);
        personalizedCategoryTxt = view.findViewById(R.id.personalised_category);

        assignedWorkoutsRecyclerview = view.findViewById(R.id.assigned_workouts_recyclerview);
        assignedMealsRecyclerview = view.findViewById(R.id.assigned_meals_recyclerview);
        videosRecyclerview = view.findViewById(R.id.video_recyclerView);
        imagesRecyclerview = view.findViewById(R.id.image_recyclerView);
        notesRecyclerview = view.findViewById(R.id.notes_recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        assignedWorkoutsRecyclerview.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        assignedMealsRecyclerview.setLayoutManager(linearLayoutManager1);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        videosRecyclerview.setLayoutManager(linearLayoutManager2);

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        imagesRecyclerview.setLayoutManager(linearLayoutManager3);

        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(getContext());
        linearLayoutManager4.setOrientation(LinearLayoutManager.HORIZONTAL);
        notesRecyclerview.setLayoutManager(linearLayoutManager4);

        adapter = new CoachAdapter(getContext());

        videosRecyclerview.setAdapter(adapter);
        imagesRecyclerview.setAdapter(adapter);
        notesRecyclerview.setAdapter(adapter);

        getworkout();

    }

    private void getworkout(){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("coach_id", PreferencesUtils.getCoach(getContext()).getId());
        params.put("skip","0");

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.personalWorkout_URL, AppConstants.personalWorkout_TAG, WorkoutPersonal.class, params);

    }


    private void getMeals(){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("coach_id", PreferencesUtils.getCoach(getContext()).getId());
        params.put("skip","0");

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.personalMeals_URL, AppConstants.personalMeals_TAG, MealsPersonal.class, params);

    }


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {
        this.tag=tag;

        switch (tag){

            case AppConstants.personalWorkout_TAG:

                WorkoutPersonal workoutPersonal =(WorkoutPersonal) result;

                PersonalWorkoutHomeAdapter personalHomeAdapter=new PersonalWorkoutHomeAdapter(getContext(),workoutPersonal.getData());

                assignedWorkoutsRecyclerview.setAdapter(personalHomeAdapter);


                break;

            case AppConstants.personalMeals_TAG:

                MealsPersonal mealsPersonal=(MealsPersonal) result;

                PersonalMealsHomeAdapter personalMealsHomeAdapter=new PersonalMealsHomeAdapter(getContext(),mealsPersonal.getData());

                assignedMealsRecyclerview.setAdapter(personalMealsHomeAdapter);

                break;



        }

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

        if (tag==AppConstants.personalWorkout_TAG){
            getMeals();
        }

    }
}