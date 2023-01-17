package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.qtechnetworks.ptplatform.Controller.adapters.SliderAdapterExample;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.Banner.Banner;
import com.qtechnetworks.ptplatform.Model.Beans.Banner.Datum;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class HomeFragment extends Fragment implements CallBack {

    SliderView sliderView;

//    private ViewPager sliderViewPager;
    private Banner banner;
//    private CircleIndicator sliderCircleIndicator;
//    private SliderAdapter sliderAdapter;
//    private Timer timer;
//    private int page = 0;

    String flag =" ", id;

    LinearLayout followUs, contactUs, news, coaches, nutritions, progress;

    public HomeFragment(String flag, String id) {
        this.flag = flag;
        this.id = id;
    }

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initial(view);
        clicks();
        getbanner();

        // Inflate the layout for this fragment
        return view;
    }

    private void clicks() {

        followUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new FollowUsFragment());
            }
        });

        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new ContactFragment());
            }
        });

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new NewsFragment());
            }
        });

        coaches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new ChooseCoachFragment());
            }
        });
        progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment( new ProgressFragment());
            }
        });
        nutritions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment( new NutritionFragment());
            }
        });
    }

    private void setFragment(Fragment fragment) {

        Bundle args = new Bundle();
        fragment.setArguments(args);

        ((MainActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").addToBackStack(null).commit();

    }

    private void setFragmentWithoutBack(Fragment fragment) {

        Bundle args = new Bundle();
        fragment.setArguments(args);

        ((MainActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").commit();

    }

    private void initial(View view) {

//        sliderViewPager=view.findViewById(R.id.slider_viewPager);
//        sliderCircleIndicator=view.findViewById(R.id.slider_indicator_unselected);

        sliderView = view.findViewById(R.id.imageSlider);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.startAutoCycle();

        followUs = view.findViewById(R.id.category_one_layout);
        coaches = view.findViewById(R.id.category_two_layout);
        news = view.findViewById(R.id.category_three_layout);
        contactUs = view.findViewById(R.id.category_four_layout);
        nutritions=view.findViewById(R.id.category_five_layout);
        progress=view.findViewById(R.id.category_six_layout);

        if (flag != null){
            if (flag.equalsIgnoreCase("news_feed")){
                setFragment(new NewsSingleFragment(id));
            }
        }

    }

    private void getbanner(){

        HashMap<String ,Object> params=new HashMap<>();

        MyApplication.getInstance().getBackgroundHttpHelper().setCallback(this);
        MyApplication.getInstance().getBackgroundHttpHelper().get(getContext(), AppConstants.banner_URL, AppConstants.banner_TAG, Banner.class, params);

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        try {
            banner= (Banner) result;
            displaySlider(banner.getData());
        } catch (Exception e) {
            setFragmentWithoutBack(new HomeFragment());
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    private void displaySlider(ArrayList<Datum> data) {
        sliderView.setSliderAdapter(new SliderAdapterExample(getContext(), data, "HomeTrainee"));

//        sliderAdapter = new SliderAdapter(data , getActivity(), "HomeTrainee");
//        sliderViewPager.setAdapter(sliderAdapter);
//        sliderCircleIndicator.setViewPager(sliderViewPager);
//        pageSwitcher();
    }


//    public void pageSwitcher() {
//        timer = new Timer();
//        timer.scheduleAtFixedRate(new RemindTask(), 0, 5000); // delay
//    }
//
//
//    class RemindTask extends TimerTask {
//
//        @Override
//        public void run() {
//
//            if (getActivity() != null) {
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
//        }
//    }

}