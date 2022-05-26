package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.Target.Target;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.R;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class NutritionFragment extends Fragment implements CallBack {

    ImageView editIcon;
    LinearLayout foodLayout, supliementLayout, calculatorLayout, dietPlansLayout;
    ProgressBar progressBar;
    TextView cal_text,carbs_title,fat_title,protein_title;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nutrition, container, false);

        initials(view);

        editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(R.id.home_frame, new SetTargetCaloriesFragment());
            }
        });

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


        // Inflate the layout for this fragment
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initials(View view) {
        editIcon = view.findViewById(R.id.edit_icon);
        foodLayout = view.findViewById(R.id.food_layout);
        supliementLayout = view.findViewById(R.id.suplements_layout);
        calculatorLayout = view.findViewById(R.id.calorie_calculator_layout);
        dietPlansLayout = view.findViewById(R.id.Recipes_Diet_Plans_layout);

        cal_text=view.findViewById(R.id.cal_text);
        carbs_title=view.findViewById(R.id.carbs_title);
        fat_title=view.findViewById(R.id.fat_title);
        protein_title=view.findViewById(R.id.protein_title);

        progressBar=view.findViewById(R.id.progressBar);

        getTarget();

    }

    private void setFragment(int frameLayout, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void getTarget(){

        HashMap<String ,Object> params=new HashMap<>();

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.target_URL, AppConstants.target_TAG, Target.class, params);

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {
        Target target=(Target) result;

        progressBar.setProgress(target.getData().getTargetCalorie(),true);

        cal_text.setText(target.getData().getTargetCalorie()+"Cal");
        carbs_title.setText(target.getData().getTargetCarb().toString());
        fat_title.setText(target.getData().getTargetFat().toString());
        protein_title.setText(target.getData().getTargetProtein().toString());

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}