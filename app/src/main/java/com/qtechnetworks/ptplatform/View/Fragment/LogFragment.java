package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.qtechnetworks.ptplatform.Controller.adapters.LogAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.NewsAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.FavoriteandWorkout.FavoriteandWorkout;
import com.qtechnetworks.ptplatform.Model.Beans.videoExercises.VideoExercises;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class LogFragment extends Fragment implements CallBack {

    RecyclerView logRecyclerview;
    LogAdapter logAdapter;
    String flag;
    TextView title;

    String coachid;

    public LogFragment(String flag) {
        this.flag = flag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            coachid=getArguments().getString("coachid");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log, container, false);

        initial(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void initial(View view) {
        logRecyclerview= view.findViewById(R.id.log_recyclerview);
        title = view.findViewById(R.id.title);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        logRecyclerview.setLayoutManager(gridLayoutManager);

        if (flag.equalsIgnoreCase("Favourite")){
            getFavorite(coachid);
            title.setText(R.string.favorites);
        } else if (flag.equalsIgnoreCase("Log")){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = sdf.format(new Date());
            Log.d("current Date",currentDate);
            getLog(coachid,currentDate);
            title.setText(R.string.log);
        }else if (flag.equalsIgnoreCase("Today’s Workouts")){
            getWorkout(coachid);
            title.setText(R.string.today_s_workout);
        }


    }

    private void getWorkout(String Coachid){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("coach_id",Coachid);
        params.put("skip","0");

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.Add_Workout_URL, AppConstants.Add_Workout_TAG, FavoriteandWorkout.class, params);

    }

    private void getFavorite(String Coachid){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("coach_id",Coachid);
        params.put("skip","0");

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.Add_Favorite_URL, AppConstants.Add_Favorite_TAG, FavoriteandWorkout.class, params);

    }


    private void getLog(String Coachid,String date){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("coach_id",Coachid);
        params.put("date",date);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.Add_Log_URL, AppConstants.Add_Log_TAG, FavoriteandWorkout.class, params);

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        switch(tag){

            case AppConstants.Add_Favorite_TAG:
            case AppConstants.Add_Workout_TAG:
            case AppConstants.Add_Log_TAG:

                FavoriteandWorkout favoriteandWorkout=(FavoriteandWorkout) result;

                logAdapter = new LogAdapter(getContext(), favoriteandWorkout.getData());
                logRecyclerview.setAdapter(logAdapter);

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