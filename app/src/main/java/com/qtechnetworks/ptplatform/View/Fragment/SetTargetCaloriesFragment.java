package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;
import com.qtechnetworks.ptplatform.View.Fragment.NutritionFragment;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class SetTargetCaloriesFragment extends Fragment implements CallBack {

    Button save;

    TextView valueCal_text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_target_calories, container, false);

        initials(view);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!valueCal_text.getText().toString().isEmpty()){

                    setTarget(valueCal_text.getText().toString());

                }else {
                    Toast.makeText(getContext(),"Please input value",Toast.LENGTH_LONG).show();
                }



            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void initials(View view) {
        save = view.findViewById(R.id.save_btn);
        valueCal_text=view.findViewById(R.id.valueCal_text);
    }

    private void setTarget(String value){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("key","target_calorie");
        params.put("value",value);

        //MyApplication.getInstance().getHttpHelper().setCallback(this);
        //MyApplication.getInstance().getHttpHelper().Post(getContext(), AppConstants.target_URL, AppConstants.target_TAG, Target.class, params);

    }

    private void setFragment(Fragment fragment) {

        Bundle args = new Bundle();
        fragment.setArguments(args);

        ((MainActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").addToBackStack(null).commit();

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {
/*
        Target target=(Target) result;
        setFragment(new NutritionFragment());
*/
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}