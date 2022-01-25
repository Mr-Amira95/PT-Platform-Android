package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qtechnetworks.ptplatform.Controller.adapters.SliderAdapter;
import com.qtechnetworks.ptplatform.R;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class MainFragment extends Fragment {

    private ViewPager sliderViewPager;
    private CircleIndicator sliderCircleIndicator;
    private SliderAdapter sliderAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initial(view);
        fillSliderViewPager();

        // Inflate the layout for this fragment
        return view;
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
    }

}