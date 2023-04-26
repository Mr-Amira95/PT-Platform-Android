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
import com.qtechnetworks.ptplatform.Model.Beans.Challenge.ChallengeData;
import com.qtechnetworks.ptplatform.Model.Beans.CoachCalendarResults.Datum;
import com.qtechnetworks.ptplatform.Model.Beans.calender.CalenderTime;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class ChallengesFragment extends Fragment implements CallBack {

    private RecyclerView otherRecyclerview, historyRecyclerview;
    public static Integer userID;

    int skip = 0;
    ArrayList<ChallengeData> data = new ArrayList<>();

    public ChallengesFragment(Integer userID) {
        ChallengesFragment.userID = userID;
    }

    public ChallengesFragment() {
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_challenges, container, false);

        initials(view);

        if (PreferencesUtils.getUserType().equalsIgnoreCase("Trainee"))
            getChallengesUser(PreferencesUtils.getCoach(getContext()).getId());
        else if (PreferencesUtils.getUserType().equalsIgnoreCase("Coach"))
            getChallengesUser(PreferencesUtils.getUser(getContext()).getId());

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

//        historyRecyclerview.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
//
//                LinearLayoutManager layoutManager = (LinearLayoutManager) historyRecyclerview.getLayoutManager();
//                if (layoutManager.findLastVisibleItemPosition() == data.size() - 1) {
//                    skip += data.size();
//
//                    if (PreferencesUtils.getUserType().equalsIgnoreCase("Trainee"))
//                        getChallengesUserBackground(PreferencesUtils.getCoach(getContext()).getId());
//                    else if (PreferencesUtils.getUserType().equalsIgnoreCase("Coach"))
//                        getChallengesUserBackground(PreferencesUtils.getUser(getContext()).getId());
//
//                }
//            }
//        });


    }

    private void setFragment(int frameLayout, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void getChallengesUser(int id) {
        HashMap<String ,Object> params=new HashMap<>();

        params.put("coach_id", id);
        params.put("skip",skip);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.CHALLENGES_URL, AppConstants.CHALLENGES_TAG, Challenge.class, params);
    }

    private void getChallengesUserBackground(int id) {
        HashMap<String ,Object> params=new HashMap<>();

        params.put("coach_id", id);
        params.put("skip",skip);

        MyApplication.getInstance().getBackgroundHttpHelper().setCallback(this);
        MyApplication.getInstance().getBackgroundHttpHelper().get(getContext(), AppConstants.CHALLENGES_URL, AppConstants.CHALLENGES_TAG, Challenge.class, params);
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
                    if (challenges.getData().size() > 0) {
                        data.addAll(challenges.getData());

                        ChallengesAdapter adapter = new ChallengesAdapter(getContext(), challenges.getData());
                        otherRecyclerview.setAdapter(adapter);
                    }

                    if (data.size() == 0)
                        Toast.makeText(getContext(), R.string.there_are_no_challenges_to_show, Toast.LENGTH_SHORT).show();


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