package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.qtechnetworks.ptplatform.R;

public class NutritionFragment extends Fragment {

    LinearLayout foodLayout, supliementLayout, calculatorLayout, dietPlansLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nutrition, container, false);

        initials(view);
        clicks();

        // Inflate the layout for this fragment
        return view;
    }

    private void clicks() {

        calculatorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(R.id.home_frame, new CaloriesCalculatorFragment());
            }
        });

        supliementLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(R.id.home_frame, new SupplementsDietPlansFragment("Supplements"));
            }
        });

        dietPlansLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(R.id.home_frame, new SupplementsDietPlansFragment("Recipes and Diet Plans"));
            }
        });

        foodLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(R.id.home_frame, new FoodFragment());
            }
        });

    }

    private void initials(View view) {
        foodLayout = view.findViewById(R.id.food_layout);
        supliementLayout = view.findViewById(R.id.suplements_layout);
        calculatorLayout = view.findViewById(R.id.calorie_calculator_layout);
        dietPlansLayout = view.findViewById(R.id.Recipes_Diet_Plans_layout);

    }

    private void setFragment(int frameLayout, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}