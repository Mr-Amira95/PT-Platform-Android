package com.qtechnetworks.ptplatform.View.Fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.qtechnetworks.ptplatform.Controller.adapters.CoachTimesAdapter;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.CoachTime.CoachTime;
import com.qtechnetworks.ptplatform.Model.Beans.Progress.Progress;
import com.qtechnetworks.ptplatform.Model.Beans.calender.CalenderTime;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import io.reactivex.disposables.Disposable;
import pub.devrel.easypermissions.EasyPermissions;

public class CalendarFragment extends Fragment implements CallBack {

    RecyclerView timesRecyclerview;
    CalendarView calendarView;
    public static Integer SELECTED_TIME;
    Button confirmBtn;
    public static String selectedDate="";
    TextView coachNameTv;
    public static String SELECTED_TIME_STRING = "";
    boolean thereTimes= false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        EasyPermissions.requestPermissions(getActivity(), "Please accept permission", 233, Manifest.permission.WRITE_CALENDAR);

        initials(view);
        clicks();

        // Inflate the layout for this fragment
        return view;
    }

    private void clicks() {

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EasyPermissions.requestPermissions(getActivity(), "Please accept permission", 233, Manifest.permission.WRITE_CALENDAR);
                 if (SELECTED_TIME != null && thereTimes){
                        requestReservation(String.valueOf(SELECTED_TIME));
                } else if (!thereTimes){
                     Toast.makeText(getContext(), "There are no available appointments on " + selectedDate, Toast.LENGTH_SHORT).show();
                 } else {
                     Toast.makeText(getContext(), "Please select time", Toast.LENGTH_SHORT).show();
                 }
            }
        });

        calendarView.setMinDate(Calendar.getInstance().getTimeInMillis());
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                selectedDate=year+"-"+(month+1)+"-"+dayOfMonth;
                getAvailableTimes(selectedDate);
            }
        });

    }

    private void askForPermission(String permission, int requestCode) {

        if (ContextCompat.checkSelfPermission(getContext(), permission)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
                Toast.makeText(getContext(), "Please grant the requested permission to get your task done!", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
            }
        }
    }

    private void requestReservation(String time_id){

        HashMap<String ,Object> params=new HashMap<>();
        params.put("time_id",time_id);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().Post(getContext(), AppConstants.COACH_CALENDAR_RESERVATION_URL, AppConstants.COACH_CALENDAR_RESERVATION_TAG , CoachTime.class, params);

    }
    private void getAvailableTimes(String date){

            HashMap<String ,Object> params=new HashMap<>();
            params.put("coach_id", PreferencesUtils.getCoach(getContext()).getId());
            params.put("date", date);
            MyApplication.getInstance().getHttpHelper().setCallback(this);
            MyApplication.getInstance().getHttpHelper().get(getContext(), AppConstants.COACH_CALENDAR_URL, AppConstants.COACH_CALENDAR_TAG, CalenderTime.class, params);

    }

    private void initials(View view) {

        confirmBtn = view.findViewById(R.id.confirm_btn);
        timesRecyclerview= view.findViewById(R.id.available_time_recyclerview);
        calendarView = view.findViewById(R.id.calendar);

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        timesRecyclerview.setLayoutManager(linearLayoutManager3);
        coachNameTv=view.findViewById(R.id.coach_name_tv);

        coachNameTv.setText(PreferencesUtils.getCoach(getContext()).getFirstName() + " availability:");
        selectedDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        getAvailableTimes(selectedDate);
    }

    private void setFragment(int frameLayout, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

            switch (tag) {
                case AppConstants.COACH_CALENDAR_TAG:

                    if(isSuccess) {
                        CalenderTime times = (CalenderTime) result;

                        if (times.getData().size() > 0){
                            thereTimes = true;
                            CoachTimesAdapter timesAdapter = new CoachTimesAdapter(getContext(), times.getData());
                            timesRecyclerview.setAdapter(timesAdapter);
                            CalendarFragment.SELECTED_TIME = null;
                            CalendarFragment.SELECTED_TIME_STRING=  "";
                        } else {
                            thereTimes = false;
                            CoachTimesAdapter timesAdapter = new CoachTimesAdapter(getContext(), times.getData());
                            timesRecyclerview.setAdapter(timesAdapter);
                            Toast.makeText(getContext(), "There are no available appointments on " + selectedDate, Toast.LENGTH_SHORT).show();
                            CalendarFragment.SELECTED_TIME = null;
                            CalendarFragment.SELECTED_TIME_STRING=  "";
                        }
                    }
                        break;

                case AppConstants.COACH_CALENDAR_RESERVATION_TAG:
                    if(isSuccess) {
                        setFragment(R.id.home_frame, new SuccessFragment("Calendar"));
                    }else{
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                    break;

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