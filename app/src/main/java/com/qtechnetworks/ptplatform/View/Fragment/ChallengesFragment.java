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
    private ChallengesAdapter adapter;
    private Button beginBtn;
    private int challengeOfTheDayId=-1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_challenges, container, false);

        initials(view);
        getChallenges();
        beginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(challengeOfTheDayId != -1) {
                    setFragment(R.id.home_frame, new ChallengesSignleFragment(challengeOfTheDayId));
                }else{
                    Toast.makeText(getContext(), "No Challenges Available", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void initials(View view) {
        beginBtn = view.findViewById(R.id.begin_btn);

        otherRecyclerview = view.findViewById(R.id.other_challenges_recyclerview);
        historyRecyclerview = view.findViewById(R.id.history_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        otherRecyclerview.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        historyRecyclerview.setLayoutManager(linearLayoutManager1);

       // historyRecyclerview.setAdapter(adapter);
    }

    private void setFragment(int frameLayout, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void getChallenges(){


        HashMap<String ,Object> params=new HashMap<>();
        params.put("coach_id", PreferencesUtils.getCoach(getActivity()).getId());
        params.put("skip",0);
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
                    try {
                        Challenge challenges = (Challenge) result;
                        if (challenges.getData().isEmpty())
                            break;
                        challengeOfTheDayId = challenges.getData().get(0).getId();
                        adapter = new ChallengesAdapter(getContext(), challenges.getData());
                        otherRecyclerview.setAdapter(adapter);
                    }catch (Exception e){
                        e.printStackTrace();
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