package com.qtechnetworks.ptplatform.View.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qtechnetworks.ptplatform.Controller.adapters.CoachAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.CoachUserAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.CoachesAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.Coach.Coach;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.R;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;


public class CoachesDialog extends Dialog implements CallBack {

    Context mContext;
    RecyclerView coachesRecyclerview;

    public CoachesDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_coaches);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        initials();
        getCoach();

    }

    private void initials() {
        coachesRecyclerview = findViewById(R.id.coaches_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        coachesRecyclerview.setLayoutManager(linearLayoutManager);
    }

    private void getCoach(){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("skip","0");

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.users_coaches_URL, AppConstants.users_coaches_TAG, Coach.class, params);

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        Coach coach=(Coach) result;

        CoachesAdapter coachAdapter = new CoachesAdapter(mContext,coach.getData(),CoachesDialog.this);
        coachesRecyclerview.setAdapter(coachAdapter);

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
