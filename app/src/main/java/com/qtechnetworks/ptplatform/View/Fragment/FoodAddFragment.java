package com.qtechnetworks.ptplatform.View.Fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qtechnetworks.ptplatform.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class FoodAddFragment extends Fragment {

    PieChart pieChart;
    String flag;

    public FoodAddFragment(String flag) {
        this.flag = flag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_add, container, false);

        initials(view);
        setData();

        // Inflate the layout for this fragment
        return view;
    }

    private void initials(View view) {
        pieChart = view.findViewById(R.id.pie_chart);
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