package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qtechnetworks.ptplatform.Controller.adapters.CoachAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.CoachUserAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.Coach.Coach;
import com.qtechnetworks.ptplatform.Model.Beans.News.News;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class ChooseCoachFragment extends Fragment implements CallBack {

    RecyclerView coachRecyclerview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_coach, container, false);

        initials(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void initials(View view) {
        coachRecyclerview = view.findViewById(R.id.coach_recyclerview);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        coachRecyclerview.setLayoutManager(gridLayoutManager);

        getCoach();

    }

    private void getCoach(){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("skip","0");

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.users_coaches_URL, AppConstants.users_coaches_TAG, Coach.class, params);

    }

    private void setFragmentWithoutBack(Fragment fragment ) {

        ((MainActivity) requireContext()).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").commit();

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        try {
            Coach coach=(Coach) result;

            CoachUserAdapter coachAdapter = new CoachUserAdapter(getContext(),coach.getData());
            coachRecyclerview.setAdapter(coachAdapter);
        } catch (Exception e){
            setFragmentWithoutBack(new ChooseCoachFragment());
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}