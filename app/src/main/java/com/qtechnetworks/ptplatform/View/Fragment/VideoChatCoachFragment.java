package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.qtechnetworks.ptplatform.Controller.adapters.VideoChatAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.VideoChatCoachAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.VideoChat.VideoChat;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.R;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class VideoChatCoachFragment extends Fragment implements CallBack {

    RecyclerView videoChatRv;

    public VideoChatCoachFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_chat_coach, container, false);

        initials(view);
        getVideoChatSessions();

        // Inflate the layout for this fragment
        return view;
    }

    private void initials(View view) {

        videoChatRv = view.findViewById(R.id.video_chat_rv);

        LinearLayoutManager layoutManagerhorizantalleader = new LinearLayoutManager(getContext());
        layoutManagerhorizantalleader.setOrientation(LinearLayoutManager.VERTICAL);
        videoChatRv.setLayoutManager(layoutManagerhorizantalleader);

    }

    public  void getVideoChatSessions(){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("skip", "0");

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.Coach_Video_Chat_URL, AppConstants.Coach_Video_Chat_TAG, VideoChat.class, params);

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        if(isSuccess) {

            VideoChat sessions = (VideoChat) result;
            VideoChatCoachAdapter videoChatAdapter = new VideoChatCoachAdapter(getContext(),sessions.getData());
            videoChatRv.setAdapter(videoChatAdapter);

            if(sessions.getData().size()>0){
                videoChatRv.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(getContext(), R.string.no_reservation, Toast.LENGTH_SHORT).show();
                videoChatRv.setVisibility(View.GONE);
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