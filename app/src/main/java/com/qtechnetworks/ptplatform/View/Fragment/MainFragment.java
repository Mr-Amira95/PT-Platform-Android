package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.qtechnetworks.ptplatform.Controller.adapters.SliderAdapter;
import com.qtechnetworks.ptplatform.R;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class MainFragment extends Fragment {

    private ViewPager sliderViewPager;
    private CircleIndicator sliderCircleIndicator;
    private SliderAdapter sliderAdapter;

    private LinearLayout exercisesLayout, workoutsLayout, nutritionLayout, progressLayout,
    personalLayout, challengesLayout, favouriteLayout, todayWorkoutLayout, contactUsLayout,
    shopLayout, videoChatLayout, liveChatLayout;

    private boolean Subscribed = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initial(view);
        fillSliderViewPager();

        exercisesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(R.id.home_frame, new ExercisesWorkoutFragment("Exercises"));
            }
        });

        workoutsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(R.id.home_frame, new ExercisesWorkoutFragment("Workout"));
            }
        });

        nutritionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(R.id.home_frame, new NutritionFragment());
            }
        });
        
        challengesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(R.id.home_frame, new LogFragment("Log"));
            }
        });

        favouriteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(R.id.home_frame, new LogFragment("Favourite"));
            }
        });

        todayWorkoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(R.id.home_frame, new LogFragment("Today’s Workouts"));
            }
        });

        personalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Subscribed){
                    setFragment(R.id.home_frame, new PersonalTrainingSubscribedFragment());
                } else {
                    setFragment(R.id.home_frame, new PersonalTrainingUnsubscribedFragment());
                }
            }
        });

        shopLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(R.id.home_frame, new ShopFragment());
            }
        });

        challengesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(R.id.home_frame, new ChallengesFragment());
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    private void setFragment(int frameLayout, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void fillSliderViewPager() {
        List<Integer> sliderList = new ArrayList<Integer>() {{
            add(R.drawable.follwous);
            add(R.drawable.coaches);
            add(R.drawable.contactus);
        }};

        sliderAdapter = new SliderAdapter(sliderList , getActivity());
        sliderViewPager.setAdapter(sliderAdapter);
        sliderCircleIndicator.setViewPager(sliderViewPager);
    }

    private void initial(View view) {
        sliderViewPager=view.findViewById(R.id.slider_viewPager);
        sliderCircleIndicator=view.findViewById(R.id.slider_indicator_unselected);

        exercisesLayout = view.findViewById(R.id.exercise_layout);
        workoutsLayout = view.findViewById(R.id.workout_layout);
        nutritionLayout = view.findViewById(R.id.nutrition_layout);
        progressLayout = view.findViewById(R.id.progress_layout);
        personalLayout = view.findViewById(R.id.personal_layout);
        challengesLayout = view.findViewById(R.id.challenges_layout);
        favouriteLayout = view.findViewById(R.id.favourite_layout);
        todayWorkoutLayout = view.findViewById(R.id.today_workout_layout);
        contactUsLayout = view.findViewById(R.id.contact_us_layout);
        shopLayout = view.findViewById(R.id.shop_layout);
        videoChatLayout = view.findViewById(R.id.video_chat_layout);
        liveChatLayout = view.findViewById(R.id.live_chat_layout);
    }

}