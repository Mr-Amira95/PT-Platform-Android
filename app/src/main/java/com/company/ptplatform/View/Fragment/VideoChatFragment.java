package com.company.ptplatform.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.company.ptplatform.Controller.adapters.VideoChatAdapter;
import com.company.ptplatform.Controller.networking.CallBack;
import com.company.ptplatform.Model.Beans.VideoChat.VideoChat;
import com.company.ptplatform.Model.basic.MyApplication;
import com.company.ptplatform.Model.utilits.AppConstants;
import com.company.ptplatform.Model.utilits.PreferencesUtils;
import com.company.ptplatform.R;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class VideoChatFragment extends Fragment implements CallBack {

    public static RecyclerView videoChatRv;
    public static VideoChatAdapter videoChatAdapter;
    public static VideoChat sessions;
    TextView emptyTxt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_chat, container, false);

        initials(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void initials(View view) {
        videoChatRv = view.findViewById(R.id.video_chat_rv);
        LinearLayoutManager layoutManagerhorizantalleader = new LinearLayoutManager(getContext());
        layoutManagerhorizantalleader.setOrientation(LinearLayoutManager.VERTICAL);
        videoChatRv.setLayoutManager(layoutManagerhorizantalleader);
        emptyTxt=view.findViewById(R.id.emptytxt);

    }
    public  void getVideoChatSessions(String coachid){

        HashMap<String ,Object> params=new HashMap<>();

        //  params.put("skip",skip);
        params.put("coach_id",coachid);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.GET_RESERVATIONS_VIDEO_CHAT_URL, AppConstants.GET_RESERVATIONS_VIDEO_CHAT_TAG, VideoChat.class, params);

    }


    @Override
    public void onResume() {
        super.onResume();
        getVideoChatSessions(PreferencesUtils.getCoach(getContext()).getId().toString());
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        switch (tag){
            case AppConstants.GET_RESERVATIONS_VIDEO_CHAT_TAG:
                if(isSuccess) {
                     sessions = (VideoChat) result;
                    videoChatAdapter = new VideoChatAdapter(getContext(),sessions.getData());
                    videoChatRv.setAdapter(videoChatAdapter);
                    if(sessions.getData().isEmpty()){
                        emptyTxt.setVisibility(View.VISIBLE);
                    } else {
                        emptyTxt.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(getContext(), R.string.no_reservation, Toast.LENGTH_SHORT).show();
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