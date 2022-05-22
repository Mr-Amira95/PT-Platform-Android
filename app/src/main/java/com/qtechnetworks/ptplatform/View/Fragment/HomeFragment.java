package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.qtechnetworks.ptplatform.Controller.adapters.CategoryAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.SliderAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.Banner.Banner;
import com.qtechnetworks.ptplatform.Model.Beans.News.News;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.Disposable;
import me.relex.circleindicator.CircleIndicator;
import ss.com.bannerslider.Slider;

public class HomeFragment extends Fragment implements CallBack {

    private ViewPager sliderViewPager;
    private RecyclerView categoryRecyclerView;
    private CircleIndicator sliderCircleIndicator;
    private CategoryAdapter categoryAdapter;
    private SliderAdapter sliderAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initial(view);
        fillCategoriesViewPager();
        fillSliderViewPager();

        // Inflate the layout for this fragment
        return view;
    }

    private void fillCategoriesViewPager() {
        List<String> list = new ArrayList<String>() {{
            add("Follow Us");
            add("Coaches");
            add("Contact Us");
            add("News");
        }};

        List<Integer> listpic = new ArrayList<Integer>() {{
            add(R.drawable.follwous);
            add(R.drawable.coaches);
            add(R.drawable.contactus);
            add(R.drawable.follwous);
        }};

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(linearLayoutManager);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //categoryRecyclerView.setLayoutManager(gridLayoutManager);

        categoryAdapter = new CategoryAdapter(list,listpic , getActivity());
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    private void fillSliderViewPager() {
        List<Integer> sliderList = new ArrayList<Integer>() {{
            add(R.drawable.follwous);
            add(R.drawable.coaches);
            add(R.drawable.contactus);
        }};

        getbanner();

    }

    private void initial(View view) {
        categoryRecyclerView=view.findViewById(R.id.category_recyclerView);
        sliderViewPager=view.findViewById(R.id.slider_viewPager);
        sliderCircleIndicator=view.findViewById(R.id.slider_indicator_unselected);
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

        Banner banner=(Banner) result;

        sliderAdapter = new SliderAdapter(banner.getData() , getActivity());
        sliderViewPager.setAdapter(sliderAdapter);
        sliderCircleIndicator.setViewPager(sliderViewPager);

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}