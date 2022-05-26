package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.qtechnetworks.ptplatform.Controller.adapters.SliderAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.Banner.Banner;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;
import com.qtechnetworks.ptplatform.View.Dialogs.CoachesDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.Disposable;
import me.relex.circleindicator.CircleIndicator;

public class MainFragment extends Fragment implements CallBack {

    private ViewPager sliderViewPager;
    private CircleIndicator sliderCircleIndicator;
    private SliderAdapter sliderAdapter;

    private LinearLayout exercisesLayout, workoutsLayout, nutritionLayout, progressLayout,
    personalLayout, challengesLayout, favouriteLayout, calenderLayout, todayWorkout, contactUsLayout,
    shopLayout, videoChatLayout, liveChatLayout, historyLayout, kycLayout;

    CoachesDialog coachesDialog;

    private boolean Subscribed = true;

    TextView nametext;

    RoundedImageView cate1_image;

    String name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            name = getArguments().getString("name");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initial(view);
        clicks();
        fillSliderViewPager();

        // Inflate the layout for this fragment
        return view;
    }

    private void clicks() {

        exercisesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new ExercisesWorkoutFragment("Exercises"), PreferencesUtils.getCoach(getContext()).getId().toString());
            }
        });

        workoutsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new ExercisesWorkoutFragment("Workout"),PreferencesUtils.getCoach(getContext()).getId().toString());
            }
        });

        nutritionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment( new NutritionFragment());
            }
        });

        challengesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment( new LogFragment("Log"),PreferencesUtils.getCoach(getContext()).getId().toString());
            }
        });

        favouriteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment( new LogFragment("Favourite"),PreferencesUtils.getCoach(getContext()).getId().toString());
            }
        });

        calenderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment( new LogFragment("Today’s Workouts"),PreferencesUtils.getCoach(getContext()).getId().toString());
            }
        });

        personalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Subscribed){
                    setFragment( new PersonalTrainingSubscribedFragment());
                } else {
                    setFragment( new PersonalTrainingUnsubscribedFragment());
                }
            }
        });

        progressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment( new ProgressFragment());
            }
        });

        shopLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new ShopFragment());
            }
        });

        challengesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment( new ChallengesFragment());
            }
        });

        nametext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coachesDialog.setCancelable(true);
                coachesDialog.show();
            }
        });
    }

    private void setFragment(Fragment fragment ) {

        ((MainActivity) requireContext()).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").addToBackStack(null).commit();

    }

    private void setFragment(Fragment fragment,String coachid) {

        Bundle args = new Bundle();
        args.putString("coachid",coachid);
        fragment.setArguments(args);

        ((MainActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").addToBackStack(null).commit();

    }

    private void fillSliderViewPager() {
        List<Integer> sliderList = new ArrayList<Integer>() {{
            add(R.drawable.follwous);
            add(R.drawable.coaches);
            add(R.drawable.contactus);
        }};

        /*sliderAdapter = new SliderAdapter(sliderList , getActivity());
        sliderViewPager.setAdapter(sliderAdapter);
        sliderCircleIndicator.setViewPager(sliderViewPager);*/
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
        calenderLayout = view.findViewById(R.id.calendar_layout);
        contactUsLayout = view.findViewById(R.id.contact_us_layout);
        shopLayout = view.findViewById(R.id.shop_layout);
        videoChatLayout = view.findViewById(R.id.video_chat_layout);
        liveChatLayout = view.findViewById(R.id.live_chat_layout);
        todayWorkout = view.findViewById(R.id.today_layout);
        historyLayout = view.findViewById(R.id.history_layout);
        kycLayout = view.findViewById(R.id.kyc_layout);

        cate1_image=view.findViewById(R.id.cate1_image);

        coachesDialog = new CoachesDialog(getContext());

        nametext= view.findViewById(R.id.name);

        nametext.setText(PreferencesUtils.getCoach(getContext()).getFirstName() + " " + PreferencesUtils.getCoach(getContext()).getLastName());

        try{
            Glide.with(getContext()).load(PreferencesUtils.getCoach(getContext()).getAvatar()).placeholder(R.drawable.logo).into(cate1_image);
        }catch (Exception e){
            e.printStackTrace();
        }

        getbanner();

    }

    private void getbanner(){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("user_id",PreferencesUtils.getCoach(getContext()).getId().toString());

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.banner_URL, AppConstants.banner_TAG, Banner.class, params);

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        Banner banner=(Banner) result;

        try {
            sliderAdapter = new SliderAdapter(banner.getData(), getActivity());
            sliderViewPager.setAdapter(sliderAdapter);
            sliderCircleIndicator.setViewPager(sliderViewPager);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}