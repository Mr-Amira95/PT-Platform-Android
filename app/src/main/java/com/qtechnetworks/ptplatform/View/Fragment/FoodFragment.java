package com.qtechnetworks.ptplatform.View.Fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qtechnetworks.ptplatform.Controller.adapters.FoodAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.FoodDinnerAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.FoodSnackAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.NewsAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.Food.Food;
import com.qtechnetworks.ptplatform.Model.Beans.FoodHome.Foodhome;
import com.qtechnetworks.ptplatform.Model.Beans.Target.Target;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class FoodFragment extends Fragment implements CallBack {

    RecyclerView breakfastRecyclerview, lunchRecyclerview, dinnerRecyclerview, snacksRecyclerview, supplemnetsRecyclerview;
    TextView addBreakfast, addLunch, addDinner, addSnack, addSupplement, target_value,food_value,exercise_value,total_val, breakfast, lunch, dinner, snacks, supplement, selectedDate;

    DatePickerDialog StartTime;

    ImageView editIcon;
    ProgressBar progressBar;
    TextView cal_text,carbs_title,fat_title,protein_title,progressTxt;
    int targetCalories=0;

    PieChart fatChart, carbChart, proteinChart;

    Target target;
    Foodhome food;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        initials(view);
        clicks();

        // Inflate the layout for this fragment
        return view;
    }

    private void clicks() {

        selectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartTime.show();
            }
        });

        editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SetTargetCaloriesFragment());
            }
        });


        /*
        breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(R.id.home_frame, new FoodSingleFragment(breakfast.getText().toString()));
            }
        });

        lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(R.id.home_frame, new FoodSingleFragment(lunch.getText().toString()));
            }
        });

        dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(R.id.home_frame, new FoodSingleFragment(dinner.getText().toString()));
            }
        });

        snacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(R.id.home_frame, new FoodSingleFragment(snacks.getText().toString()));
            }
        });

        supplement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(R.id.home_frame, new FoodSingleFragment(supplement.getText().toString()));
            }
        });
*/

        addBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new FoodAddFragment("breakfast"));
            }
        });

        addLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new FoodAddFragment("lunch"));
            }
        });

        addDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new FoodAddFragment("dinner"));
            }
        });

        addSnack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new FoodAddFragment("snack"));
            }
        });

        addSupplement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new FoodAddFragment("supplements"));
            }
        });

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.home_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void initials(View view) {

        carbChart = view.findViewById(R.id.carbs_chart);
        fatChart = view.findViewById(R.id.fat_chart);
        proteinChart = view.findViewById(R.id.protein_chart);

        selectedDate = view.findViewById(R.id.today);
        breakfast = view.findViewById(R.id.breakfast_title);
        lunch = view.findViewById(R.id.lunch_title);
        dinner = view.findViewById(R.id.dinner_title);
        snacks = view.findViewById(R.id.snacks_title);
        supplement = view.findViewById(R.id.supplements_title);

        addBreakfast = view.findViewById(R.id.add_breakfast);
        addLunch = view.findViewById(R.id.add_lunch);
        addDinner = view.findViewById(R.id.add_dinner);
        addSnack = view.findViewById(R.id.add_snacks);
        addSupplement = view.findViewById(R.id.add_supplements);

        lunchRecyclerview = view.findViewById(R.id.lunch_recyclerview);
        breakfastRecyclerview = view.findViewById(R.id.breakfast_recyclerview);
        dinnerRecyclerview = view.findViewById(R.id.dinner_recyclerview);
        snacksRecyclerview = view.findViewById(R.id.snacks_recyclerview);
        supplemnetsRecyclerview = view.findViewById(R.id.supplements_recyclerview);

        target_value = view.findViewById(R.id.target_value);
        food_value = view.findViewById(R.id.food_value);
        exercise_value = view.findViewById(R.id.exercise_value);
        total_val = view.findViewById(R.id.total_val);

        LinearLayoutManager layoutManagerhorizantalleader = new LinearLayoutManager(getContext());
        layoutManagerhorizantalleader.setOrientation(LinearLayoutManager.VERTICAL);
        lunchRecyclerview.setLayoutManager(layoutManagerhorizantalleader);

        LinearLayoutManager layoutManagerhorizantalbreakfast = new LinearLayoutManager(getContext());
        layoutManagerhorizantalbreakfast.setOrientation(LinearLayoutManager.VERTICAL);
        breakfastRecyclerview.setLayoutManager(layoutManagerhorizantalbreakfast);

        LinearLayoutManager layoutManagerhorizantaldiner = new LinearLayoutManager(getContext());
        layoutManagerhorizantaldiner.setOrientation(LinearLayoutManager.VERTICAL);
        dinnerRecyclerview.setLayoutManager(layoutManagerhorizantaldiner);

        LinearLayoutManager layoutManagerhorizantalsnak = new LinearLayoutManager(getContext());
        layoutManagerhorizantalsnak.setOrientation(LinearLayoutManager.VERTICAL);
        snacksRecyclerview.setLayoutManager(layoutManagerhorizantalsnak);

        LinearLayoutManager layoutManagerhorizantalsupp = new LinearLayoutManager(getContext());
        layoutManagerhorizantalsupp.setOrientation(LinearLayoutManager.VERTICAL);
        supplemnetsRecyclerview.setLayoutManager(layoutManagerhorizantalsupp);

        editIcon = view.findViewById(R.id.edit_icon);

        cal_text=view.findViewById(R.id.cal_text);
        carbs_title=view.findViewById(R.id.carbs_title);
        fat_title=view.findViewById(R.id.fat_title);
        protein_title=view.findViewById(R.id.protein_title);

        progressBar=view.findViewById(R.id.targetprogressBar);
        progressTxt=view.findViewById(R.id.progress_txt);
        getTarget();

        final Calendar newCalendar = Calendar.getInstance();
        StartTime = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                selectedDate.setText(year + "-" +(monthOfYear+1) + "-" + dayOfMonth);
                getFood(year + "-" +(monthOfYear+1) + "-" + dayOfMonth);
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    private void getTarget(){

        HashMap<String ,Object> params=new HashMap<>();

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.target_URL, AppConstants.target_TAG, Target.class, params);

    }

    private void getFood(String Date){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("date",Date);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.fooduser_URL, AppConstants.fooduser_TAG, Foodhome.class, params);

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        switch (tag){

            case AppConstants.fooduser_TAG:

                food=(Foodhome) result;

                if (food.getSuccess()){

                    progressBar.setProgress((food.getData().getFoodTarget())*100/targetCalories, true);
                    progressTxt.setText((food.getData().getFoodTarget())+""+"/"+targetCalories +" Cal");

                    target_value.setText(food.getData().getUserTarget().toString());
                    food_value.setText(food.getData().getFoodTarget().toString());
                    exercise_value.setText(food.getData().getExerciseTarget().toString());

                    carbChart.addPieSlice(new PieModel( food.getData().getCarb(), Color.parseColor("#1EB1FC")));
                    fatChart.addPieSlice(new PieModel( food.getData().getFat(), Color.parseColor("#FF0000"))) ;
                    proteinChart.addPieSlice(new PieModel(food.getData().getProtein(), Color.parseColor("#8DC63F"))) ;

                    carbChart.addPieSlice(new PieModel( food.getData().getUser().getTargetCarb(), Color.parseColor("#FFFFFF")));
                    fatChart.addPieSlice(new PieModel( food.getData().getUser().getTargetFat(), Color.parseColor("#FFFFFF"))) ;
                    proteinChart.addPieSlice(new PieModel( food.getData().getUser().getTargetProtein(), Color.parseColor("#FFFFFF"))) ;

                    carbChart.startAnimation();
                    fatChart.startAnimation();
                    proteinChart.startAnimation();

                    carbs_title.setText(food.getData().getCarb().toString());
                    fat_title.setText(food.getData().getFat().toString());
                    protein_title.setText(food.getData().getProtein().toString());

                    total_val.setText(String.valueOf((food.getData().getUserTarget()-food.getData().getFoodTarget())+food.getData().getExerciseTarget()));

                    FoodSnackAdapter foodAdapter = new FoodSnackAdapter(getContext(),food.getData().getFood().getBreakfast());
                    breakfastRecyclerview.setAdapter(foodAdapter);

                    FoodSnackAdapter foodAdapterdine = new FoodSnackAdapter(getContext(),food.getData().getFood().getDinner());
                    dinnerRecyclerview.setAdapter(foodAdapterdine);

                    FoodSnackAdapter foodsnackAdapter = new FoodSnackAdapter(getContext(),food.getData().getFood().getSnack());
                    snacksRecyclerview.setAdapter(foodsnackAdapter);

                    FoodSnackAdapter foodsnackAdapter1 = new FoodSnackAdapter(getContext(),food.getData().getFood().getSnack());
                    snacksRecyclerview.setAdapter(foodsnackAdapter1);

                    FoodSnackAdapter foodsnackAdapter2 = new FoodSnackAdapter(getContext(),food.getData().getFood().getSnack());
                    snacksRecyclerview.setAdapter(foodsnackAdapter2);


                }
                break;

            case AppConstants.target_TAG:
                target = (Target) result;
                targetCalories=target.getData().getTargetCalorie();
                getFood(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

                carbChart.setInnerValueString(target.getData().getTargetCarb().toString());
                fatChart.setInnerValueString(target.getData().getTargetFat().toString());
                proteinChart.setInnerValueString(target.getData().getTargetProtein().toString());
                cal_text.setText(target.getData().getTargetCalorie() + "Cal");

                break;

        }

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

}