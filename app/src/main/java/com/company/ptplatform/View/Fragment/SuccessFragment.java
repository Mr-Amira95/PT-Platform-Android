package com.company.ptplatform.View.Fragment;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.company.ptplatform.Model.utilits.PreferencesUtils;
import com.company.ptplatform.R;
import com.company.ptplatform.View.Activity.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class SuccessFragment extends Fragment {

    TextView title, msg;
    String flag;

    Button doneBtn, addToCalenderBtn;

    public SuccessFragment (String flag) {
        this.flag = flag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_success, container, false);

        initials(view);
        clicks();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                setFragment(R.id.home_frame, new MainFragment());
            }
        },3000);


        // Inflate the layout for this fragment
        return view;
    }

    private void clicks() {

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), MainActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        addToCalenderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEvent("Appointment with Coach "+ PreferencesUtils.getCoach(getContext()).getLastName(),"on Zoom",CalendarFragment.selectedDate+" "+CalendarFragment.SELECTED_TIME_STRING,"Zoom URL");
            }
        });

    }

    private void initials(View view) {
        title = view.findViewById(R.id.title);
        msg = view.findViewById(R.id.message);

        doneBtn = view.findViewById(R.id.done_btn);
        addToCalenderBtn = view.findViewById(R.id.add_to_calender_btn);

        if (flag.equals("Calendar")){
            title.setText(getString(R.string.request_sent));
            msg.setText(getString(R.string.your_request_is_awaiting_your_pt_s_approval_we_ll_notify_you_soon));
            addToCalenderBtn.setVisibility(View.VISIBLE);
        } else if (flag.equals("Checkout")) {
            title.setText(R.string.purchase_successfull);
            msg.setText(R.string.enjoy_the_benefits);
            addToCalenderBtn.setVisibility(View.GONE);
        }
    }

    public void addEvent(String title, String location, String begin, String description) {
        Log.d("AddEvent",title + location + begin+description);
        ContentResolver cr = getActivity().getContentResolver();
        ContentValues values = new ContentValues();
        String dateString = begin;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        try {
            Date date = sdf.parse(dateString);
            long  startDate = date.getTime();
            long endDate= getEndDatetime(date);
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
            values.put(CalendarContract.Events.DURATION, "+P60M");

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

    public long  getEndDatetime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, +30);

        Date currentDatePlusOne = c.getTime();

        return currentDatePlusOne.getTime();
    }


}