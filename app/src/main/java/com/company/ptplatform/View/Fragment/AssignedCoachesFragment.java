package com.company.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.company.ptplatform.Controller.adapters.CoachAdapter;
import com.company.ptplatform.Controller.networking.CallBack;
import com.company.ptplatform.Model.Beans.Coach.Coach;
import com.company.ptplatform.Model.basic.MyApplication;
import com.company.ptplatform.Model.utilits.AppConstants;
import com.company.ptplatform.R;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class AssignedCoachesFragment extends Fragment implements CallBack {

    private RecyclerView coachRecyclerview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assigned_coaches, container, false);

        initials(view);
        getAssignedCoach();

        // Inflate the layout for this fragment
        return view;
    }

    private void getAssignedCoach(){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("skip","0");
        params.put("filter","assigned_coaches");

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.users_coaches_URL, AppConstants.users_coaches_TAG, Coach.class, params);

    }

    private void initials(View view) {
        coachRecyclerview = view.findViewById(R.id.assigendcoaches_recyclerview);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        coachRecyclerview.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {
        Coach coach=(Coach) result;

        if (coach.getData().size()>0) {
            CoachAdapter adapter = new CoachAdapter(getContext(), coach);
            coachRecyclerview.setAdapter(adapter);
        } else {
            Toast.makeText(getContext(), R.string.no_assigned_coaches_to_show, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}