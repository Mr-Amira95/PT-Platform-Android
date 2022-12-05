package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qtechnetworks.ptplatform.Controller.adapters.CoachAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.PersonalMealsHomeAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.PersonalWorkoutHomeAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.PersonalizedItemAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.MealsPersonal.MealsPersonal;
import com.qtechnetworks.ptplatform.Model.Beans.Personalized.Personalized;
import com.qtechnetworks.ptplatform.Model.Beans.WorkoutPersonal.WorkoutPersonal;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class PersonalTrainingTraineeFragment extends Fragment implements CallBack {

    private ConstraintLayout noContentLayout;
    private LinearLayout contentLayout, homeCategoryLayout, PersonalizedCategoryLayout;
    private TextView homeCategoryTxt, personalizedCategoryTxt, assignedWorkouts, assignedMeals, videoTitle, imagesTitle, notesTitle;

    private RecyclerView assignedWorkoutsRecyclerview, assignedMealsRecyclerview, videosRecyclerview, imagesRecyclerview, notesRecyclerview;
    private PersonalizedItemAdapter videosAdapter,imagesAdapter,notesAdapter;

    int tag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_training_subscribed, container, false);

        initials(view);

        noContentLayout.setVisibility(View.INVISIBLE);
        contentLayout.setVisibility(View.VISIBLE);

        personalizedCategoryTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPersonalized();
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
        videoTitle = view.findViewById(R.id.video_title);
        imagesTitle = view.findViewById(R.id.image_title);
        notesTitle = view.findViewById(R.id.notes_title);

        contentLayout = view.findViewById(R.id.content_layout);
        noContentLayout = view.findViewById(R.id.no_content_layout);

        assignedWorkouts = view.findViewById(R.id.assigned_workouts_title);
        assignedMeals = view.findViewById(R.id.assigned_meals_title);

        homeCategoryLayout = view.findViewById(R.id.home_layout);
        PersonalizedCategoryLayout = view.findViewById(R.id.personalized_layout);

        homeCategoryTxt = view.findViewById(R.id.home_category);
        personalizedCategoryTxt = view.findViewById(R.id.personalised_category);

        assignedWorkoutsRecyclerview = view.findViewById(R.id.assigned_workouts_recyclerview);
        assignedMealsRecyclerview = view.findViewById(R.id.assigned_meals_recyclerview);
        videosRecyclerview = view.findViewById(R.id.video_recyclerView);
        imagesRecyclerview = view.findViewById(R.id.image_recyclerView);
        notesRecyclerview = view.findViewById(R.id.notes_recyclerView);

        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 3);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        assignedWorkoutsRecyclerview.setLayoutManager(linearLayoutManager);

        GridLayoutManager linearLayoutManager1 = new GridLayoutManager(getContext(), 3);
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        assignedMealsRecyclerview.setLayoutManager(linearLayoutManager1);

        GridLayoutManager linearLayoutManager2 = new GridLayoutManager(getContext(), 3);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        videosRecyclerview.setLayoutManager(linearLayoutManager2);

        GridLayoutManager linearLayoutManager3 = new GridLayoutManager(getContext(), 3);
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        imagesRecyclerview.setLayoutManager(linearLayoutManager3);

        GridLayoutManager linearLayoutManager4 = new GridLayoutManager(getContext(), 3);
        linearLayoutManager4.setOrientation(LinearLayoutManager.VERTICAL);
        notesRecyclerview.setLayoutManager(linearLayoutManager4);

        getworkout();
    }

    private void getPersonalized() {

        HashMap<String ,Object> params=new HashMap<>();

        params.put("coach_id", PreferencesUtils.getCoach(getContext()).getId());
        params.put("skip","0");

        MyApplication.getInstance().getBackgroundHttpHelper().setCallback(this);
        MyApplication.getInstance().getBackgroundHttpHelper().get(getContext(), AppConstants.GET_PERSONALIZED_URL, AppConstants.GET_PERSONALIZED_TAG, Personalized.class, params);
    }

    private void getworkout() {
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

                if (workoutPersonal.getData().size() > 0){
                    PersonalWorkoutHomeAdapter personalHomeAdapter=new PersonalWorkoutHomeAdapter(getContext(),workoutPersonal.getData());
                    assignedWorkoutsRecyclerview.setAdapter(personalHomeAdapter);
                } else {
                    assignedWorkouts.setVisibility(View.GONE);
                    assignedWorkoutsRecyclerview.setVisibility(View.GONE);
                }

                break;

            case AppConstants.personalMeals_TAG:
                MealsPersonal mealsPersonal=(MealsPersonal) result;

                if (mealsPersonal.getData().size() > 0){
                    PersonalMealsHomeAdapter personalMealsHomeAdapter=new PersonalMealsHomeAdapter(getContext(),mealsPersonal.getData());
                    assignedMealsRecyclerview.setAdapter(personalMealsHomeAdapter);
                } else {
                    assignedMeals.setVisibility(View.GONE);
                    assignedMealsRecyclerview.setVisibility(View.GONE);
                }

                break;

            case AppConstants.GET_PERSONALIZED_TAG:

                Personalized personalized=(Personalized) result;

                if (personalized.getData().getVideo().size() > 0) {
                    videosAdapter=new PersonalizedItemAdapter(getContext(),personalized.getData(),PersonalizedItemAdapter.VIDEO_PERSONAL_TAG);
                    videosRecyclerview.setAdapter(videosAdapter);
                } else {
                    videoTitle.setVisibility(View.GONE);
                    videosRecyclerview.setVisibility(View.GONE);
                }

                if (personalized.getData().getImage().size() >0) {
                    imagesAdapter=new PersonalizedItemAdapter(getContext(),personalized.getData(),PersonalizedItemAdapter.IMAGE_PERSONAL_TAG);
                    imagesRecyclerview.setAdapter(imagesAdapter);
                } else {
                    imagesTitle.setVisibility(View.GONE);
                    imagesRecyclerview.setVisibility(View.GONE);
                }

                if (personalized.getData().getPdf().size() > 0) {
                    notesAdapter=new PersonalizedItemAdapter(getContext(),personalized.getData(),PersonalizedItemAdapter.NOTE_PERSONAL_TAG);
                    notesRecyclerview.setAdapter(notesAdapter);
                } else {
                    notesTitle.setVisibility(View.GONE);
                    notesRecyclerview.setVisibility(View.GONE);
                }

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