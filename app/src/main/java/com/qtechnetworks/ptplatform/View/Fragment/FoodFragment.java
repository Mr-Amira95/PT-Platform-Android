package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class FoodFragment extends Fragment implements CallBack {

    RecyclerView breakfastRecyclerview, lunchRecyclerview, dinnerRecyclerview, snacksRecyclerview, supplemnetsRecyclerview;
    TextView addBreakfast, addLunch, addDinner, addSnack, addSupplement;
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
                setFragment(R.id.home_frame, new FoodAddFragment("Breakfast"));
            }
        });

        addLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(R.id.home_frame, new FoodAddFragment("Lunch"));
            }
        });

        addDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(R.id.home_frame, new FoodAddFragment("Dinner"));
            }
        });

        addSnack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(R.id.home_frame, new FoodAddFragment("Snack"));
            }
        });

        addSupplement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(R.id.home_frame, new FoodAddFragment("Supplement"));
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

        lunchRecyclerview= view.findViewById(R.id.lunch_recyclerview);
        breakfastRecyclerview=view.findViewById(R.id.breakfast_recyclerview);
        dinnerRecyclerview=view.findViewById(R.id.dinner_recyclerview);
        snacksRecyclerview=view.findViewById(R.id.snacks_recyclerview);

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

        getFood("2022-5-25");


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

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        Foodhome food=(Foodhome) result;

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