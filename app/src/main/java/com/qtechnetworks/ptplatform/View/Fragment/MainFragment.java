package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
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

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.disposables.Disposable;
import me.relex.circleindicator.CircleIndicator;

public class MainFragment extends Fragment implements CallBack {

    private ViewPager sliderViewPager;
    private CircleIndicator sliderCircleIndicator;
    private SliderAdapter sliderAdapter;
    private Banner banner;
    private Timer timer;
    private int page = 0;

    private LinearLayout
            exercisesLayout, workoutsLayout, nutritionLayout, progressLayout,
            personalLayout, challengesLayout, favouriteLayout, calenderLayout, todayWorkoutLayout,
            shopLayout, videoChatLayout, liveChatLayout, historyLayout, kycLayout;

    CoachesDialog coachesDialog;

    TextView nametext;
    String flag = "";
    RoundedImageView cate1_image;

    public MainFragment(String flag) {
        this.flag = flag;
    }

    public MainFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ( getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initial(view);
        clicks();

        // Inflate the layout for this fragment
        return view;
    }

    private void clicks() {

        exercisesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new ExercisesFragment(), PreferencesUtils.getCoach(getContext()).getId().toString());
            }
        });

        workoutsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new WorkoutFragment(),PreferencesUtils.getCoach(getContext()).getId().toString());
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
                setFragment( new CalendarFragment(),PreferencesUtils.getCoach(getContext()).getId().toString());
            }
        });

        personalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment( new PersonalTrainingTraineeFragment());
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

        historyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new HistoryFragment());
            }
        });

        kycLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment( new AddTraineeDetailsFragment());
            }
        });

        todayWorkoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment( new LogFragment("Today’s Workouts"),PreferencesUtils.getCoach(getContext()).getId().toString());
            }
        });

        videoChatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setFragment( new VideoChatFragment());
            }
        });

    }

    private void setFragment(Fragment fragment ) {

        ((MainActivity) requireContext()).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").addToBackStack(null).commit();

    }

    private void setFragmentWithoutBack(Fragment fragment ) {

        ((MainActivity) requireContext()).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").commit();

    }

    private void setFragment(Fragment fragment,String coachid) {

        Bundle args = new Bundle();
        args.putString("coachid",coachid);
        fragment.setArguments(args);

        ((MainActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").addToBackStack(null).commit();

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
        shopLayout = view.findViewById(R.id.shop_layout);
        videoChatLayout = view.findViewById(R.id.video_chat_layout);
        liveChatLayout = view.findViewById(R.id.live_chat_layout);
        todayWorkoutLayout = view.findViewById(R.id.today_layout);
        historyLayout = view.findViewById(R.id.history_layout);
        kycLayout = view.findViewById(R.id.kyc_layout);

        cate1_image=view.findViewById(R.id.cate1_image);

        coachesDialog = new CoachesDialog(getContext());

        nametext= view.findViewById(R.id.name);

        nametext.setText(PreferencesUtils.getCoach(getContext()).getLastName());

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


        try {
            banner=(Banner) result;

            sliderAdapter = new SliderAdapter(banner.getData(), getActivity(), "MainTrainee");
            sliderViewPager.setAdapter(sliderAdapter);
            sliderCircleIndicator.setViewPager(sliderViewPager);
            pageSwitcher();


        }catch (Exception e){
            setFragmentWithoutBack(new MainFragment());
            e.printStackTrace();
        }
        if(flag.equals("shop")){
            setFragment(new ShopFragment());
            flag="";

        }

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    public void pageSwitcher() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new RemindTask(), 0, 5000); // delay
    }


    class RemindTask extends TimerTask {

        @Override
        public void run() {

            if (getActivity() != null){

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (page > banner.getData().size()-1) {
                            page = 0;
                            sliderViewPager.setCurrentItem(page);
                        } else {
                            sliderViewPager.setCurrentItem(page++);
                        }

                    }
                });

            }

        }
    }

}