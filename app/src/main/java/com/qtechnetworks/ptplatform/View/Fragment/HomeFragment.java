package com.qtechnetworks.ptplatform.View.Fragment;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.JsonObject;
import com.qtechnetworks.ptplatform.Controller.adapters.CategoryAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.SliderAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.Banner.Banner;
import com.qtechnetworks.ptplatform.Model.Beans.Banner.Datum;
import com.qtechnetworks.ptplatform.Model.Beans.News.News;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.disposables.Disposable;
import me.relex.circleindicator.CircleIndicator;
import ss.com.bannerslider.Slider;

public class HomeFragment extends Fragment implements CallBack {

    private ViewPager sliderViewPager;
    private Banner banner;
    private CircleIndicator sliderCircleIndicator;
    private SliderAdapter sliderAdapter;
    private Timer timer;
    private int page = 0;

    LinearLayout followUs, contactUs, news, coaches, nutritions, progress;

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

        sliderViewPager=view.findViewById(R.id.slider_viewPager);
        sliderCircleIndicator=view.findViewById(R.id.slider_indicator_unselected);

        followUs = view.findViewById(R.id.category_one_layout);
        coaches = view.findViewById(R.id.category_two_layout);
        news = view.findViewById(R.id.category_three_layout);
        contactUs = view.findViewById(R.id.category_four_layout);
        nutritions=view.findViewById(R.id.category_five_layout);
        progress=view.findViewById(R.id.category_six_layout);

    }

    private void getbanner(){

        HashMap<String ,Object> params=new HashMap<>();

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.banner_URL, AppConstants.banner_TAG, Banner.class, params);

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

    private void displaySlider(List<Datum> data) {
        sliderAdapter = new SliderAdapter(data , getActivity(), "HomeTrainee");
        sliderViewPager.setAdapter(sliderAdapter);
        sliderCircleIndicator.setViewPager(sliderViewPager);
        pageSwitcher();
    }


    public void pageSwitcher() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new RemindTask(), 0, 5000); // delay
    }


    class RemindTask extends TimerTask {

        @Override
        public void run() {

            if (getActivity() != null) {

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