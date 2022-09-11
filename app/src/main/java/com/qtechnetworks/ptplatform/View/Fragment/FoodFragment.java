package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qtechnetworks.ptplatform.Controller.adapters.FoodAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.FoodDinnerAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.FoodSnackAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.NewsAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.Food.Food;
import com.qtechnetworks.ptplatform.Model.Beans.FoodHome.Foodhome;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class FoodFragment extends Fragment implements CallBack {

    RecyclerView breakfastRecyclerview, lunchRecyclerview, dinnerRecyclerview, snacksRecyclerview, supplemnetsRecyclerview;
    TextView addBreakfast, addLunch, addDinner, addSnack, addSupplement;
    TextView target_value,food_value,exercise_value,total_val;
    FoodAdapter foodAdapter;
    TextView breakfast, lunch, dinner, snacks, supplement;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        initials(view);

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

        addBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(R.id.home_frame, new FoodAddFragment("breakfast"));
            }
        });

        addLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(R.id.home_frame, new FoodAddFragment("lunch"));
            }
        });

        addDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(R.id.home_frame, new FoodAddFragment("dinner"));
            }
        });

        addSnack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(R.id.home_frame, new FoodAddFragment("snack"));
            }
        });

        addSupplement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(R.id.home_frame, new FoodAddFragment("supplement"));
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void setFragment(int frameLayout, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void initials(View view) {

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




    }

    private void getFood(String Date){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("date",Date);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.fooduser_URL, AppConstants.fooduser_TAG, Foodhome.class, params);

    }

    @Override
    public void onResume() {
        super.onResume();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());
        Log.d("current Date",currentDate);
        getFood(currentDate);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        Foodhome food=(Foodhome) result;

        target_value.setText(food.getData().getFoodTarget().toString());
        food_value.setText(food.getData().getUserTarget().toString());
        exercise_value.setText(food.getData().getExerciseTarget().toString());

        total_val.setText(String.valueOf((food.getData().getFoodTarget()-food.getData().getUserTarget())+food.getData().getExerciseTarget()));

        foodAdapter = new FoodAdapter(getContext(),food.getData().getFood().getBreakfast());
        breakfastRecyclerview.setAdapter(foodAdapter);

        FoodDinnerAdapter foodAdapterdine = new FoodDinnerAdapter(getContext(),food.getData().getFood().getDinner());
        dinnerRecyclerview.setAdapter(foodAdapterdine);

        FoodSnackAdapter foodsnackAdapter = new FoodSnackAdapter(getContext(),food.getData().getFood().getSnack());
        snacksRecyclerview.setAdapter(foodsnackAdapter);


    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}