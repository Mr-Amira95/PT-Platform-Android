package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qtechnetworks.ptplatform.Controller.adapters.NewsAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.VideoItemAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.News.News;
import com.qtechnetworks.ptplatform.Model.Beans.videoExercises.VideoExercises;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.R;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class ExercisesSingleFragment extends Fragment implements CallBack {

    RecyclerView videoRecyclerview;
    VideoItemAdapter videoAdapter;
    public ImageView video_view;

    public TextView desc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_exercises_single, container, false);

        initial(view);

        //Inflate the layout for this fragment
        return view;
    }

    private void initial(View view) {
        videoRecyclerview= view.findViewById(R.id.video_recyclerView);

        video_view=view.findViewById(R.id.video_view);
        desc=view.findViewById(R.id.desc);

        LinearLayoutManager layoutManagerhorizantalleader = new LinearLayoutManager(getContext());
        layoutManagerhorizantalleader.setOrientation(LinearLayoutManager.VERTICAL);
        videoRecyclerview.setLayoutManager(layoutManagerhorizantalleader);

        getExcerisis("2695");

    }


    private void getExcerisis(String Exid){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("exercise_id",Exid);
        params.put("skip","0");

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.exercise_videos_URL, AppConstants.exercise_videos_TAG, VideoExercises.class, params);

    }


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        VideoExercises videoExercises=(VideoExercises) result;


        try{

            Glide.with(getContext()).load(videoExercises.getData().get(0).getImage()).placeholder(R.drawable.logo).into(video_view);

        }catch (Exception e){
            e.printStackTrace();
        }

        desc.setText(videoExercises.getData().get(0).getDescription());


        videoAdapter = new VideoItemAdapter(getContext(),videoExercises.getData(),ExercisesSingleFragment.this);
        videoRecyclerview.setAdapter(videoAdapter);

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}