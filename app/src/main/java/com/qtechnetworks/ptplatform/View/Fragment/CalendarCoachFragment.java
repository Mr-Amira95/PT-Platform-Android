package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.qtechnetworks.ptplatform.Controller.adapters.CalendarAdapter;
import com.qtechnetworks.ptplatform.Controller.adapters.PersonalCoachAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.CoachCalendarResults.CoachCalendarResults;
import com.qtechnetworks.ptplatform.Model.Beans.PersonalCoach.PersonalCoach;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.R;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class CalendarCoachFragment extends Fragment implements CallBack {

    RecyclerView calendarRecyclerview;

    public CalendarCoachFragment() {
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
        View view = inflater.inflate(R.layout.fragment_calendar_coach, container, false);

        initials(view);
        getCalendar();

        // Inflate the layout for this fragment
        return view;
    }

    private void initials(View view) {
        calendarRecyclerview = view.findViewById(R.id.calender_recyclerview);

        LinearLayoutManager layoutManagerhorizantalleader = new LinearLayoutManager(getContext());
        layoutManagerhorizantalleader.setOrientation(LinearLayoutManager.VERTICAL);
        calendarRecyclerview.setLayoutManager(layoutManagerhorizantalleader);
    }

    private void getCalendar() {

        HashMap<String ,Object> params=new HashMap<>();

        MyApplication.getInstance().getBackgroundHttpHelper().setCallback(this);
        MyApplication.getInstance().getBackgroundHttpHelper().get(getContext(), AppConstants.Coach_Calendar_URL, AppConstants.Coach_Calendar_TAG, CoachCalendarResults.class, params);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        if (isSuccess) {
            CoachCalendarResults coachCalendarResults =(CoachCalendarResults) result;
            if (coachCalendarResults.getData().size() > 0) {
                CalendarAdapter calendarAdapter = new CalendarAdapter(getContext(), coachCalendarResults.getData());
                calendarRecyclerview.setAdapter(calendarAdapter);
            } else {
                calendarRecyclerview.setVisibility(View.GONE);
                Toast.makeText(getContext(), "There are no results to show", Toast.LENGTH_SHORT).show();
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