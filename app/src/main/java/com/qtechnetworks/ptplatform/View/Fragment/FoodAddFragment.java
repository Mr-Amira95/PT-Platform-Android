package com.qtechnetworks.ptplatform.View.Fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qtechnetworks.ptplatform.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class FoodAddFragment extends Fragment {

    PieChart pieChart;
    String flag;
    ImageView doneIcon;

    public FoodAddFragment(String flag) {
        this.flag = flag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_add, container, false);

        initials(view);

        doneIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(R.id.home_frame, new FoodFragment());
            }
        });

        setData();

        // Inflate the layout for this fragment
        return view;
    }

    private void initials(View view) {
        pieChart = view.findViewById(R.id.pie_chart);
        doneIcon = view.findViewById(R.id.done_icon);

    }

    private void setFragment(int frameLayout, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void setData() {

        // Set the percentage of language used
        // Set the data and color to the pie chart
        pieChart.addPieSlice(
                new PieModel(
                        "Carbs",
                        4,
                        R.color.blue));
        pieChart.addPieSlice(
                new PieModel(
                        "Fat",
                        16,
                        R.color.red));
        pieChart.addPieSlice(
                new PieModel(
                        "Protein",
                        80,
                        R.color.primary_color));

        //pieChart.addPieSlice(new PieModel("Carbs", 4, R.color.blue));
        //pieChart.addPieSlice(new PieModel("Fat", 16, R.color.red));
        //pieChart.addPieSlice(new PieModel("Protein", 80, R.color.primary_color));

        // To animate the pie chart
        pieChart.startAnimation();
    }

}