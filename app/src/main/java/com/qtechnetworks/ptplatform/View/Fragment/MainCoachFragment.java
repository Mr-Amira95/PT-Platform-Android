package com.qtechnetworks.ptplatform.View.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.qtechnetworks.ptplatform.Controller.adapters.SliderAdapterExample;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.Banner.Banner;
import com.qtechnetworks.ptplatform.Model.Beans.General;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;
import com.qtechnetworks.ptplatform.View.Activity.SplashActivity;
import com.qtechnetworks.ptplatform.View.Dialogs.LanguagesDialog;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class MainCoachFragment extends Fragment implements CallBack {

//    private ViewPager sliderViewPager;
//    private CircleIndicator sliderCircleIndicator;
//    private SliderAdapter sliderAdapter;
    private Banner banner;
    SliderView sliderView;

//    private Timer timer;
//    private int page = 0;

    private LinearLayout
            exercisesLayout, workoutsLayout, challengesLayout,
            shopLayout, personalLayout, calenderLayout,
            videoChatLayout, liveChatLayout, kycLayout,
            historyLayout, logoutLayout, progressLayout,
            languagesLayout;

    public MainCoachFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_main_coach, container, false);

        initial(view);
        clicks();

        // Inflate the layout for this fragment
        return view;
    }

    private void clicks() {

        exercisesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new ExercisesFragment(), PreferencesUtils.getUser(getContext()).getId().toString());
            }
        });

        workoutsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new WorkoutFragment(),PreferencesUtils.getUser(getContext()).getId().toString());
            }
        });

        calenderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment( new CalendarCoachFragment(), PreferencesUtils.getUser(getContext()).getId().toString());
            }
        });

        personalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment( new PersonalTrainingCoachFragment());
            }
        });

        shopLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new ShopFragment());
            }
        });

        videoChatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setFragment( new VideoChatCoachFragment());
            }
        });

        challengesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new ChooseCoachFragment("challenges"));
//                setFragment( new ChallengesFragment());
            }
        });

        historyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new ChooseCoachFragment("history"));
            }
        });

        kycLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new ChooseCoachFragment("kyc"));

//                setFragment(new AddTraineeDetailsFragment());
            }
        });

        liveChatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new ChooseCoachFragment("chat"));

//                setFragment(new AddTraineeDetailsFragment());
            }
        });

        progressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new ChooseCoachFragment("progress"));

//                setFragment( new ProgressFragment());
            }
        });

        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                logout();
            }
        });

        languagesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LanguagesDialog languagesDialog = new LanguagesDialog(getContext());
                languagesDialog.show();
            }
        });
    }

    private void logout() {
        HashMap<String ,Object> params=new HashMap<>();

        params.put("device_player_id", PreferencesUtils.getPlayerId());

        MyApplication.getInstance().getBackgroundHttpHelper().setCallback(this);
        MyApplication.getInstance().getBackgroundHttpHelper().Post(getContext(), AppConstants.LOGOUT_URL, AppConstants.LOGOUT_TAG, General.class, params);

    }

    private void initial(View view) {

//        sliderViewPager=view.findViewById(R.id.slider_viewPager);
//        sliderCircleIndicator=view.findViewById(R.id.slider_indicator_unselected);

        sliderView = view.findViewById(R.id.imageSlider);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.startAutoCycle();

        languagesLayout = view.findViewById(R.id.languages_layout);
        progressLayout = view.findViewById(R.id.progress_layout);
        exercisesLayout = view.findViewById(R.id.exercise_layout);
        workoutsLayout = view.findViewById(R.id.workout_layout);
        personalLayout = view.findViewById(R.id.personal_layout);
        challengesLayout = view.findViewById(R.id.challenges_layout);
        calenderLayout = view.findViewById(R.id.calendar_layout);
        shopLayout = view.findViewById(R.id.shop_layout);
        videoChatLayout = view.findViewById(R.id.video_chat_layout);
        liveChatLayout = view.findViewById(R.id.live_chat_layout);
        historyLayout = view.findViewById(R.id.history_layout);
        kycLayout = view.findViewById(R.id.kyc_layout);
        logoutLayout = view.findViewById(R.id.logout_layout);

//        getBanner();

    }

    private void setFragment(Fragment fragment ) {

        ((MainActivity) requireContext()).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").addToBackStack(null).commit();

    }

    private void setFragment(Fragment fragment,String coachid) {

        Bundle args = new Bundle();
        args.putString("coachid", coachid);
        fragment.setArguments(args);

        ((MainActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").addToBackStack(null).commit();

    }

    private void getBanner() {

        HashMap<String ,Object> params=new HashMap<>();

        params.put("user_id", PreferencesUtils.getUser(getContext()).getId().toString());

        MyApplication.getInstance().getBackgroundHttpHelper().setCallback(this);
        MyApplication.getInstance().getBackgroundHttpHelper().get(getContext(), AppConstants.banner_URL, AppConstants.banner_TAG, Banner.class, params);

    }

//    public void pageSwitcher() {
//        timer = new Timer();
//        timer.scheduleAtFixedRate(new MainCoachFragment.RemindTask(), 0, 5000); // delay
//    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        if (isSuccess){
            switch (tag){
                case AppConstants.banner_TAG:

                    banner = (Banner) result;
                    sliderView.setSliderAdapter(new SliderAdapterExample(getContext(), banner.getData(), "MainCoach"));

//                    sliderAdapter = new SliderAdapter(banner.getData(), getActivity(), "MainCoach");
//                    sliderViewPager.setAdapter(sliderAdapter);
//                    sliderCircleIndicator.setViewPager(sliderViewPager);
//                    pageSwitcher();

                    break;

                case AppConstants.LOGOUT_TAG:

                    General general = (General) result;

                    Toast.makeText(getContext(), general.getData().toString(), Toast.LENGTH_SHORT).show();
                    PreferencesUtils.clearDefaults(getContext());
                    startActivity(new Intent(getContext(), SplashActivity.class));
                    getActivity().finish();

                    break;
            }
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }


    @Override
    public void onStart() {
        getBanner();
        super.onStart();
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//    }

//    class RemindTask extends TimerTask {
//
//        @Override
//        public void run() {
//
//            if (getActivity() != null){
//
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        if (page > banner.getData().size()-1) {
//                            page = 0;
//                            sliderViewPager.setCurrentItem(page);
//                        } else {
//                            sliderViewPager.setCurrentItem(page++);
//                        }
//
//                    }
//                });
//
//            }
//
//        }
//    }

}