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

public class CalendarFragment extends Fragment implements CallBack {

    RecyclerView timesRecyclerview;
    CalendarView calendarView;
    CoachTimesAdapter timesAdapter;
    public static Integer SELECTED_TIME;
    Button confirmBtn;
    String coachId;
    TextView coachNameTv;
    String coachName="";
     String selectedDate="";
    public static String SELECTED_TIME_STRING="";
    CalenderTime times;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        askForPermission(Manifest.permission.WRITE_CALENDAR,1);

        initials(view);
        if (getArguments() != null) {
            coachId=getArguments().getString("coachid");
            coachName= PreferencesUtils.getCoach(getContext()).getFirstName();
            coachNameTv.setText(coachName+" availability:");
        }
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
                    // You can use the API that requires the permission.
                    requestReservation(SELECTED_TIME+"");
                } else {
                    // You can directly ask for the permission.
                    askForPermission(Manifest.permission.WRITE_CALENDAR,1);

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


        // Inflate the layout for this fragment
        return view;
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

        Log.d("AddEvent","out");
        HashMap<String ,Object> params=new HashMap<>();
        params.put("time_id",time_id);
      //  addEvent("Appointment with caoch "+PreferencesUtils.getCoach(getContext()).getFirstName(),"on Zoom",selectedDate+" "+SELECTED_TIME_STRING,"Zoom URL");

          MyApplication.getInstance().getHttpHelper().setCallback(this);
       MyApplication.getInstance().getHttpHelper().Post(getContext(), AppConstants.COACH_CALENDAR_RESERVATION_URL, AppConstants.COACH_CALENDAR_RESERVATION_TAG , CoachTime.class, params);

    }
    private void getAvailableTimes(String date){


            HashMap<String ,Object> params=new HashMap<>();
            params.put("coach_id",coachId);
            params.put("date",date);
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
                         times = (CalenderTime) result;
                        timesAdapter = new CoachTimesAdapter(getContext(), times.getData());
                        timesRecyclerview.setAdapter(timesAdapter);
                    }
                        break;
                case AppConstants.COACH_CALENDAR_RESERVATION_TAG:
                    if(isSuccess) {
                        addEvent("Appointment with caoch "+PreferencesUtils.getCoach(getContext()).getFirstName(),"on Zoom",selectedDate+" "+SELECTED_TIME_STRING,"Zoom URL");

                        setFragment(R.id.home_frame, new SuccessFragment("Calendar"));


                    }else{
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    }
    public long  getEndDatetime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        // Perform addition/subtraction
        //  c.add(Calendar.YEAR, 2);
        //   c.add(Calendar.MONTH, 1);
        //   c.add(Calendar.DATE, -10);
        //   c.add(Calendar.HOUR, -4);
        c.add(Calendar.MINUTE, +30);
        //   c.add(Calendar.SECOND, 50);

        // Convert calendar back to Date
        Date currentDatePlusOne = c.getTime();
        //System.out.println("Current time => "+c.getTime());
      //  SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
       // String formattedDate = df.format(currentDatePlusOne.getTime());
        return currentDatePlusOne.getTime();
    }
    public void addEvent(String title, String location, String begin, String  description) {
        Log.d("AddEvent",title+location+begin+description);
        ContentResolver cr = getActivity().getContentResolver();
        ContentValues values = new ContentValues();
            String dateString = begin;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            try {
                Date  date = sdf.parse(dateString);
                long  startDate = date.getTime();
               long endDate=getEndDatetime(date);
                values.put(CalendarContract.Events.DTSTART, startDate);
                values.put(CalendarContract.Events.TITLE, title);
                values.put(CalendarContract.Events.DESCRIPTION, description);
                values.put(CalendarContract.Events.EVENT_LOCATION, location);

                TimeZone timeZone = TimeZone.getDefault();
                values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());

// Default calendar
                values.put(CalendarContract.Events.CALENDAR_ID, 1);

//        values.put(CalendarContract.Events.RRULE, "FREQ=DAILY;UNTIL="
//                + dtUntill);
// Set Period for 1 Hour
                values.put(CalendarContract.Events.DURATION, "+P30M");

                values.put(CalendarContract.Events.HAS_ALARM, 1);

// Insert event to calendar
                Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.Events.TITLE, title)
                        .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
                        .putExtra(CalendarContract.Events.DURATION, "+P30M")
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startDate)
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endDate)
                        .putExtra(CalendarContract.EXTRA_CUSTOM_APP_URI, endDate);
             //   startActivity(intent);
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }



    }


    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission with request code 1 granted
                    Toast.makeText(getContext(), "Permission Granted" , Toast.LENGTH_LONG).show();
                } else {
                    //permission with request code 1 was not granted
                    Toast.makeText(getContext(), "Permission was not Granted" , Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}