package com.company.ptplatform.View.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.company.ptplatform.Controller.adapters.CoachesAdapter;
import com.company.ptplatform.Controller.networking.CallBack;
import com.company.ptplatform.Model.Beans.Coach.Coach;
import com.company.ptplatform.Model.basic.MyApplication;
import com.company.ptplatform.Model.utilits.AppConstants;
import com.company.ptplatform.R;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;


public class CoachesDialog extends Dialog implements CallBack {

    Context mContext;
    RecyclerView coachesRecyclerview;
    TextView title;

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
        title = findViewById(R.id.title);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        coachesRecyclerview.setLayoutManager(linearLayoutManager);
    }

    private void getCoach(){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("skip","0");
        params.put("filter","assigned_coaches");

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.users_coaches_URL, AppConstants.users_coaches_TAG, Coach.class, params);

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        Coach coach = (Coach) result;
        CoachesAdapter coachAdapter = new CoachesAdapter(mContext, coach.getData(), CoachesDialog.this);
        coachesRecyclerview.setAdapter(coachAdapter);

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
