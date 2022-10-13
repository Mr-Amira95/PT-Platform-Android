package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.qtechnetworks.ptplatform.Controller.adapters.ChallengesAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.CoachAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.Challenge.Challenge;
import com.qtechnetworks.ptplatform.Model.Beans.calender.CalenderTime;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class ChallengesFragment extends Fragment implements CallBack {

    private RecyclerView otherRecyclerview, historyRecyclerview;
    private Integer userID;

    public ChallengesFragment(Integer userID) {
        this.userID = userID;
    }

    public ChallengesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_challenges, container, false);

        initials(view);

        if (PreferencesUtils.getUserType().equalsIgnoreCase("Trainee"))
            getChallengesUser();
        else if (PreferencesUtils.getUserType().equalsIgnoreCase("Coach"))
            getChallengesUser();

        // Inflate the layout for this fragment
        return view;
    }

    private void initials(View view) {

        otherRecyclerview = view.findViewById(R.id.other_challenges_recyclerview);
        historyRecyclerview = view.findViewById(R.id.history_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        otherRecyclerview.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        historyRecyclerview.setLayoutManager(linearLayoutManager1);

    }

    private void setFragment(int frameLayout, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void getChallengesUser(){
        HashMap<String ,Object> params=new HashMap<>();

        params.put("coach_id", PreferencesUtils.getCoach(getContext()).getId());
        params.put("skip","0");

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.CHALLENGES_URL, AppConstants.CHALLENGES_TAG, Challenge.class, params);
    }

    private void getChallengesCoach(){
        HashMap<String ,Object> params=new HashMap<>();

        params.put("coach_id", PreferencesUtils.getCoach(getContext()).getId());
        params.put("skip","0");

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.CHALLENGES_URL, AppConstants.CHALLENGES_TAG, Challenge.class, params);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {
        switch (tag){
            case AppConstants.CHALLENGES_TAG:
                if(isSuccess) {

                    Challenge challenges = (Challenge) result;

                    if (challenges.getData().size()>0) {
                        ChallengesAdapter adapter = new ChallengesAdapter(getContext(), challenges.getData());
                        otherRecyclerview.setAdapter(adapter);
                    } else {
                        Toast.makeText(getContext(), "There are no challenges to show", Toast.LENGTH_SHORT).show();
                    }
                }
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