package com.company.ptplatform.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.company.ptplatform.Controller.adapters.CalendarAdapter;
import com.company.ptplatform.Controller.networking.CallBack;
import com.company.ptplatform.Model.Beans.Banner.CoachCalendarResults.CoachCalendarResults;
import com.company.ptplatform.Model.Beans.Banner.CoachCalendarResults.Datum;
import com.company.ptplatform.Model.basic.MyApplication;
import com.company.ptplatform.Model.utilits.AppConstants;
import com.company.ptplatform.R;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class CalendarCoachFragment extends Fragment implements CallBack {

    RecyclerView calendarRecyclerview;
    int skip = 0;
    ArrayList<Datum> data = new ArrayList<>();

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

//        calendarRecyclerview.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
//
//                LinearLayoutManager layoutManager = (LinearLayoutManager) calendarRecyclerview.getLayoutManager();
//                if (layoutManager.findLastVisibleItemPosition() == data.size() - 1) {
//                    skip += data.size();
//                    getCalendarBackground();
//                }
//            }
//        });

    }

    private void getCalendar() {

        HashMap<String ,Object> params=new HashMap<>();

        params.put("skip", skip);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.Coach_Calendar_URL, AppConstants.Coach_Calendar_TAG, CoachCalendarResults.class, params);
    }

    private void getCalendarBackground() {

        HashMap<String ,Object> params=new HashMap<>();

        params.put("skip", skip);

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
            data.addAll(coachCalendarResults.getData());
            if (coachCalendarResults.getData().size() > 0) {
                CalendarAdapter calendarAdapter = new CalendarAdapter(getContext(), data);
                calendarRecyclerview.setAdapter(calendarAdapter);
            }

            if (data.size() == 0) {
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