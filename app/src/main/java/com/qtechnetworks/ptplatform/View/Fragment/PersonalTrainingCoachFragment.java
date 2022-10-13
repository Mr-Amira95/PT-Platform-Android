package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.qtechnetworks.ptplatform.Controller.adapters.PackageAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.PersonalCoachAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.PersonalWorkoutHomeAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.PersonalCoach.PersonalCoach;
import com.qtechnetworks.ptplatform.Model.Beans.Personalized.Personalized;
import com.qtechnetworks.ptplatform.Model.Beans.WorkoutPersonal.WorkoutPersonal;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class PersonalTrainingCoachFragment extends Fragment implements CallBack {

    RecyclerView packageRecyclerview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_personal_training_unsubscribed, container, false);

        initials(view);
        getPersonalized();

        // Inflate the layout for this fragment
        return view;
    }

    private void initials(View view) {
        packageRecyclerview = view.findViewById(R.id.packages_recyclerview);

        LinearLayoutManager layoutManagerhorizantalleader = new LinearLayoutManager(getContext());
        layoutManagerhorizantalleader.setOrientation(LinearLayoutManager.VERTICAL);
        packageRecyclerview.setLayoutManager(layoutManagerhorizantalleader);
    }

    private void getPersonalized() {

        HashMap<String ,Object> params=new HashMap<>();

        MyApplication.getInstance().getBackgroundHttpHelper().setCallback(this);
        MyApplication.getInstance().getBackgroundHttpHelper().get(getContext(), AppConstants.Personal_Training_Coach_URL, AppConstants.Personal_Training_Coach_TAG, PersonalCoach.class, params);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        if (isSuccess){
            PersonalCoach personalCoach =(PersonalCoach) result;
            if (personalCoach.getData().size() > 0){
                PersonalCoachAdapter personalCoachAdapter = new PersonalCoachAdapter(getContext(), personalCoach.getData());
                packageRecyclerview.setAdapter(personalCoachAdapter);
            } else {
                packageRecyclerview.setVisibility(View.GONE);
                Toast.makeText(getContext(), "There are no results to show", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}