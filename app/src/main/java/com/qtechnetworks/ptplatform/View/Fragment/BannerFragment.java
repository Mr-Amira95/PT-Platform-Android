package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qtechnetworks.ptplatform.Controller.adapters.SliderAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.SliderInnerAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.Banner.Banner;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.disposables.Disposable;
import me.relex.circleindicator.CircleIndicator;

public class BannerFragment extends Fragment implements CallBack {

    private ViewPager sliderViewPager;
    private CircleIndicator sliderCircleIndicator;
    private SliderInnerAdapter sliderAdapter;
    private Banner banner;
    private Timer timer;
    private int page = 0;

    String flag;

    public BannerFragment(String flag) {
        this.flag = flag;
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
        View view = inflater.inflate(R.layout.fragment_banner, container, false);

        initials(view);

        if (flag.equalsIgnoreCase("HomeTrainee"))
            getHomeTraineeBanner();
        else if (flag.equalsIgnoreCase("MainTrainee"))
            getMainTraineeBanner();
        else if (flag.equalsIgnoreCase("MainCoach"))
            getMainCoachBanner();

        // Inflate the layout for this fragment
        return view;
    }

    private void initials(View view) {
        sliderViewPager=view.findViewById(R.id.slider_viewPager);
        sliderCircleIndicator=view.findViewById(R.id.slider_indicator_unselected);
    }

    private void getHomeTraineeBanner() {

        HashMap<String ,Object> params=new HashMap<>();

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.banner_URL, AppConstants.banner_TAG, Banner.class, params);
    }

    private void getMainCoachBanner(){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("user_id", PreferencesUtils.getUser(getContext()).getId().toString());

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.banner_URL, AppConstants.banner_TAG, Banner.class, params);

    }

    private void getMainTraineeBanner(){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("user_id",PreferencesUtils.getCoach(getContext()).getId().toString());

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.banner_URL, AppConstants.banner_TAG, Banner.class, params);

    }


    public void pageSwitcher() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new BannerFragment.RemindTask(), 0, 5000); // delay
    }

    @Override
    public void onSubscribe (Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        banner = (Banner) result;

        sliderAdapter = new SliderInnerAdapter(banner.getData(), getActivity());
        sliderViewPager.setAdapter(sliderAdapter);
        sliderCircleIndicator.setViewPager(sliderViewPager);
        pageSwitcher();

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

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