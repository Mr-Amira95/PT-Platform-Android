package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.qtechnetworks.ptplatform.Controller.adapters.ChalengesVideosAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.CoachAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.Challenge.Challenge;
import com.qtechnetworks.ptplatform.Model.Beans.Challenge.VideoChallenge;
import com.qtechnetworks.ptplatform.Model.Beans.General;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class ChallengesSignleFragment extends Fragment implements CallBack {

    private RecyclerView challengesRecyclerview;
    private ChalengesVideosAdapter adapter;
    Button finishBtn;
    TextView subtitle;
    private int challengeId;

    private String traineeID;
    int completed=0;

    public ChallengesSignleFragment (int challengeId, String traineeID) {
        this.challengeId = challengeId;
        this.traineeID = traineeID;
    }

    public ChallengesSignleFragment(int challengeId){
        this.challengeId = challengeId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_challenges_signle, container, false);

        initials(view);
        clicks();

        if (PreferencesUtils.getUserType().equalsIgnoreCase("trainee"))
            getChallengeVideos();
        else if (PreferencesUtils.getUserType().equalsIgnoreCase("coach"))
            getCoachChallengeVideos();

        // Inflate the layout for this fragment
        return view;
    }

    private void clicks() {

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                completeChallenges();
            }
        });

    }

    private void initials(View view) {
        finishBtn = view.findViewById(R.id.finish_btn);
        challengesRecyclerview = view.findViewById(R.id.challenges_recyclerview);
        subtitle=view.findViewById(R.id.sub_title);

        if (PreferencesUtils.getUserType().equalsIgnoreCase("coach")){
            subtitle.setVisibility(View.GONE);
            finishBtn.setVisibility(View.GONE);
        } else if (PreferencesUtils.getUserType().equalsIgnoreCase("trainee")){
            subtitle.setVisibility(View.VISIBLE);
            finishBtn.setVisibility(View.VISIBLE);
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        challengesRecyclerview.setLayoutManager(gridLayoutManager);
    }

    private void completeChallenges(){

        if(adapter.completedVideosIds.size()>0) {
            HashMap<String, Object> params = new HashMap<>();

            params.put("challenge_video_ids", adapter.completedVideosIds);

            MyApplication.getInstance().getHttpHelper().setCallback(this);
            MyApplication.getInstance().getHttpHelper().PostRaw(getContext(), AppConstants.CHALLENGES_COMPLETE_URL, AppConstants.CHALLENGES_COMPLETE_TAG, General.class, params);
        }else{
            Toast.makeText(getContext(), R.string.select_challenge_to_finish, Toast.LENGTH_SHORT).show();
        }

    }
    private void getChallengeVideos () {

        HashMap<String ,Object> params=new HashMap<>();

        params.put("challenge_id", challengeId);
        params.put("coach_id", PreferencesUtils.getCoach(getContext()).getId());
        params.put("skip",0);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.CHALLENGES_VIDEOS_URL, AppConstants.CHALLENGES_VIDEOS_TAG, VideoChallenge.class, params);
    }

    private void getCoachChallengeVideos () {

        HashMap<String ,Object> params=new HashMap<>();
        params.put("challenge_id", challengeId);
        params.put("user_id", traineeID);
        params.put("skip",0);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.CHALLENGES_VIDEOS_URL, AppConstants.CHALLENGES_VIDEOS_TAG, VideoChallenge.class, params);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }
    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {
        switch (tag){
            case AppConstants.CHALLENGES_VIDEOS_TAG:
                if(isSuccess){
                    VideoChallenge videoChallenge=(VideoChallenge)result;
                    adapter = new ChalengesVideosAdapter(getContext(),videoChallenge.getData());
                    challengesRecyclerview.setAdapter(adapter);
                    completed=0;
                    for(int i=0;i<videoChallenge.getData().size();i++){
                        if(videoChallenge.getData().get(i).getIsComplete()){
                            completed=completed+1;
                        }
                    }
                    subtitle.setText(completed+"/"+videoChallenge.getData().size()+getString(R.string.challenges_completed));
                }
                break;
            case AppConstants.CHALLENGES_COMPLETE_TAG:
                if(isSuccess){
                    Toast.makeText(getContext(), R.string.updated_successfully, Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().popBackStackImmediate();
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