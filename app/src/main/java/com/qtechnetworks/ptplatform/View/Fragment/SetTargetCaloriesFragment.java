package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.Target.Target;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class SetTargetCaloriesFragment extends Fragment implements CallBack {

    Button save;
    EditText valueCal_text, carbsValue, fatValue, proteinValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_target_calories, container, false);

        initials(view);
        getTargets();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!valueCal_text.getText().toString().isEmpty() && !fatValue.getText().toString().isEmpty() && !carbsValue.getText().toString().isEmpty() && !proteinValue.getText().toString().isEmpty()){
                    setTarget();
                }else {
                    Toast.makeText(getContext(), R.string.please_fill_all_input_values,Toast.LENGTH_LONG).show();
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void getTargets() {
        HashMap<String ,Object> params=new HashMap<>();

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.target_URL, 1, Target.class, params);
    }

    private void initials(View view) {
        save = view.findViewById(R.id.save_btn);
        carbsValue=view.findViewById(R.id.carb);
        fatValue=view.findViewById(R.id.fat);
        proteinValue=view.findViewById(R.id.protein);
        valueCal_text=view.findViewById(R.id.valueCal_text);
    }

    private void setTarget(){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("target_calorie", valueCal_text.getText().toString());
        params.put("target_carb", carbsValue.getText().toString());
        params.put("target_fat", fatValue.getText().toString());
        params.put("target_protein", proteinValue.getText().toString());

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().Post(getContext(), AppConstants.target_URL, AppConstants.target_TAG, Target.class, params);
    }

    private void setFragment (Fragment fragment) {

        Bundle args = new Bundle();
        fragment.setArguments(args);

        ((MainActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").commit();
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {
        Target target=(Target) result;

        switch (tag){
            case AppConstants.target_TAG:
                setFragment(new FoodFragment());
                break;

            case 1:
                if (target.getData().getTargetCalorie() != null)
                    valueCal_text.setText(String.valueOf(target.getData().getTargetCalorie()));

                if (target.getData().getTargetCarb() != null)
                    carbsValue.setText(String.valueOf(target.getData().getTargetCarb()));

                if (target.getData().getTargetFat() != null)
                    fatValue.setText(String.valueOf(target.getData().getTargetFat()));

                if (target.getData().getTargetProtein() != null)
                    proteinValue.setText(String.valueOf(target.getData().getTargetProtein()));

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