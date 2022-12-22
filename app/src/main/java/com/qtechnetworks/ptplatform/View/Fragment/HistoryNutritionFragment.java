package com.qtechnetworks.ptplatform.View.Fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.qtechnetworks.ptplatform.Controller.adapters.NutritionHistoryAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.WorkoutHistoryAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.FavoriteandWorkout.FavoriteandWorkout;
import com.qtechnetworks.ptplatform.Model.Beans.FoodHome.Foodhome;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class HistoryNutritionFragment extends Fragment implements CallBack {

    RecyclerView nutritionRecyclerviewBreakfast, nutritionRecyclerviewLunch, nutritionRecyclerviewDinner, nutritionRecyclerviewSnacks, nutritionRecyclerviewSupplements ;
    TextView selectedDate, progressTxt, breakfastTitle, launchTitle, dinnerTitle, SnacksTitle, SupplementsTitle;
    DatePickerDialog StartTime;

    ProgressBar progressBar;
    PieChart fatChart, carbChart, proteinChart;

    public HistoryNutritionFragment() {
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
        View view = inflater.inflate(R.layout.fragment_nutrition_history, container, false);

        initial(view);
        clicks();

        if (PreferencesUtils.getUserType().equalsIgnoreCase("Trainee"))
            getNutritionHistoryTrainee(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        else if (PreferencesUtils.getUserType().equalsIgnoreCase("Coach"))
            getNutritionHistoryCoach(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        // Inflate the layout for this fragment
        return view;
    }

    private void getNutritionHistoryTrainee(String date) {

        HashMap<String ,Object> params=new HashMap<>();

        params.put("date",date);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.fooduser_URL, AppConstants.food_TAG, Foodhome.class, params);

    }

    private void getNutritionHistoryCoach(String date) {

        HashMap<String ,Object> params=new HashMap<>();

        params.put("date",date);
        params.put("user_id",HistoryFragment.userID);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.fooduser_URL, AppConstants.food_TAG, Foodhome.class, params);

    }

    private void clicks() {

        selectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartTime.show();
            }
        });

    }

    private void initial(View view) {

        breakfastTitle=view.findViewById(R.id.breakfast_title);
        launchTitle=view.findViewById(R.id.lunch_title);
        dinnerTitle=view.findViewById(R.id.dinner_title);
        SnacksTitle=view.findViewById(R.id.snacks_title);
        SupplementsTitle=view.findViewById(R.id.supplement_title);

        progressBar=view.findViewById(R.id.targetprogressBar);
        carbChart = view.findViewById(R.id.carbs_chart);
        fatChart = view.findViewById(R.id.fat_chart);
        proteinChart = view.findViewById(R.id.protein_chart);

        progressTxt= view.findViewById(R.id.progress_txt);
        selectedDate= view.findViewById(R.id.selected_date);
        nutritionRecyclerviewBreakfast= view.findViewById(R.id.nutrition_history_recyclerview_breakfast);
        nutritionRecyclerviewLunch= view.findViewById(R.id.nutrition_history_recyclerview_lunch);
        nutritionRecyclerviewDinner= view.findViewById(R.id.nutrition_history_recyclerview_dinner);
        nutritionRecyclerviewSnacks= view.findViewById(R.id.nutrition_history_recyclerview_snacks);
        nutritionRecyclerviewSupplements= view.findViewById(R.id.nutrition_history_recyclerview_supplements);

        LinearLayoutManager layoutManagerhorizantalleader1 = new LinearLayoutManager(getContext());
        layoutManagerhorizantalleader1.setOrientation(LinearLayoutManager.VERTICAL);
        nutritionRecyclerviewBreakfast.setLayoutManager(layoutManagerhorizantalleader1);

        LinearLayoutManager layoutManagerhorizantalleader2 = new LinearLayoutManager(getContext());
        layoutManagerhorizantalleader2.setOrientation(LinearLayoutManager.VERTICAL);
        nutritionRecyclerviewLunch.setLayoutManager(layoutManagerhorizantalleader2);

        LinearLayoutManager layoutManagerhorizantalleader3 = new LinearLayoutManager(getContext());
        layoutManagerhorizantalleader3.setOrientation(LinearLayoutManager.VERTICAL);
        nutritionRecyclerviewDinner.setLayoutManager(layoutManagerhorizantalleader3);

        LinearLayoutManager layoutManagerhorizantalleader4 = new LinearLayoutManager(getContext());
        layoutManagerhorizantalleader4.setOrientation(LinearLayoutManager.VERTICAL);
        nutritionRecyclerviewSnacks.setLayoutManager(layoutManagerhorizantalleader4);

        LinearLayoutManager layoutManagerhorizantalleader5 = new LinearLayoutManager(getContext());
        layoutManagerhorizantalleader5.setOrientation(LinearLayoutManager.VERTICAL);
        nutritionRecyclerviewSupplements.setLayoutManager(layoutManagerhorizantalleader5);

        final Calendar newCalendar = Calendar.getInstance();
        StartTime = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                selectedDate.setText(year + "-" +(monthOfYear+1) + "-" + dayOfMonth);

                if (PreferencesUtils.getUserType().equalsIgnoreCase("Trainee"))
                    getNutritionHistoryTrainee(year + "-" +(monthOfYear+1) + "-" + dayOfMonth);
                else if (PreferencesUtils.getUserType().equalsIgnoreCase("Trainee"))
                    getNutritionHistoryCoach(year + "-" +(monthOfYear+1) + "-" + dayOfMonth);

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        if (isSuccess) {
            Foodhome foodhome = (Foodhome) result;

            if (foodhome.getData().getFoodTarget() > 0){
                progressBar.setProgress(foodhome.getData().getFoodTarget(), true);
                progressTxt.setText(foodhome.getData().getFoodTarget() + getString(R.string.calories_1));
            } else {
                progressBar.setProgress(0, true);
                progressTxt.setText("0 " + getString(R.string.calories_1));
            }

            if (foodhome.getData().getCarb() > 0) {
                carbChart.addPieSlice(new PieModel( foodhome.getData().getCarb(), Color.parseColor("#1EB1FC")));
                carbChart.addPieSlice(new PieModel( foodhome.getData().getUser().getTargetCarb()-foodhome.getData().getCarb(), Color.parseColor("#1EB1FC")));
            } else
                carbChart.addPieSlice(new PieModel( 1, Color.parseColor("#FFFFFF")));

            if (foodhome.getData().getFat() > 0) {
                fatChart.addPieSlice(new PieModel(foodhome.getData().getFat(), Color.parseColor("#FF0000")));
                fatChart.addPieSlice(new PieModel(foodhome.getData().getUser().getTargetFat() - foodhome.getData().getFat(), Color.parseColor("#FF0000")));
            } else
                fatChart.addPieSlice(new PieModel( 1, Color.parseColor("#FFFFFF")));

            if (foodhome.getData().getProtein() > 0) {
                proteinChart.addPieSlice(new PieModel(foodhome.getData().getProtein(), Color.parseColor("#8DC63F")));
                proteinChart.addPieSlice(new PieModel(foodhome.getData().getUser().getTargetProtein()-foodhome.getData().getProtein(), Color.parseColor("#8DC63F")));
            } else
                proteinChart.addPieSlice(new PieModel(1, Color.parseColor("#FFFFFF"))) ;

            carbChart.startAnimation();
            fatChart.startAnimation();
            proteinChart.startAnimation();

            carbChart.setInnerValueString(foodhome.getData().getUser().getTargetCarb().toString());
            fatChart.setInnerValueString(foodhome.getData().getUser().getTargetFat().toString());
            proteinChart.setInnerValueString(foodhome.getData().getUser().getTargetProtein().toString());

            if (foodhome.getData().getFood().getBreakfast().size() >0){
                breakfastTitle.setVisibility(View.VISIBLE);
            } else {
                breakfastTitle.setVisibility(View.GONE);
            }

            if (foodhome.getData().getFood().getLunch().size() >0){
                launchTitle.setVisibility(View.VISIBLE);
            } else {
                launchTitle.setVisibility(View.GONE);
            }

            if (foodhome.getData().getFood().getDinner().size() >0){
                dinnerTitle.setVisibility(View.VISIBLE);
            } else {
                dinnerTitle.setVisibility(View.GONE);
            }

            if (foodhome.getData().getFood().getSnack().size() >0){
                SnacksTitle.setVisibility(View.VISIBLE);
            } else {
                SnacksTitle.setVisibility(View.GONE);
            }

            if (foodhome.getData().getFood().getSupplements().size() >0){
                SupplementsTitle.setVisibility(View.VISIBLE);
            } else {
                SupplementsTitle.setVisibility(View.GONE);
            }


            NutritionHistoryAdapter nutritionHistoryAdapter1 = new NutritionHistoryAdapter(getContext(), foodhome.getData().getFood().getBreakfast(), "Breakfast");
            nutritionRecyclerviewBreakfast.setAdapter(nutritionHistoryAdapter1);
            NutritionHistoryAdapter nutritionHistoryAdapter2 = new NutritionHistoryAdapter(getContext(), foodhome.getData().getFood().getLunch(), "Lunch");
            nutritionRecyclerviewLunch.setAdapter(nutritionHistoryAdapter2);
            NutritionHistoryAdapter nutritionHistoryAdapter3 = new NutritionHistoryAdapter(getContext(), foodhome.getData().getFood().getDinner(), "Dinner");
            nutritionRecyclerviewDinner.setAdapter(nutritionHistoryAdapter3);
            NutritionHistoryAdapter nutritionHistoryAdapter4 = new NutritionHistoryAdapter(getContext(), foodhome.getData().getFood().getSnack(), "Snacks");
            nutritionRecyclerviewSnacks.setAdapter(nutritionHistoryAdapter4);
            NutritionHistoryAdapter nutritionHistoryAdapter5 = new NutritionHistoryAdapter(getContext(), foodhome.getData().getFood().getLunch(), "Supplements");
            nutritionRecyclerviewSupplements.setAdapter(nutritionHistoryAdapter5);
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}