package com.qtechnetworks.ptplatform.View.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import com.qtechnetworks.ptplatform.Controller.adapters.TitleAdapter;
import com.qtechnetworks.ptplatform.R;

public class CalendarFragment extends Fragment {

    RecyclerView timesRecyclerview;
    CalendarView calendarView;
    TitleAdapter timesAdapter;
    Button confirmBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        initials(view);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(R.id.home_frame, new RequestSuccessFragment());
            }
        });

        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void initials(View view) {

        confirmBtn = view.findViewById(R.id.confirm_btn);
        timesRecyclerview= view.findViewById(R.id.available_time_recyclerview);
        calendarView = view.findViewById(R.id.calendar);

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        timesRecyclerview.setLayoutManager(linearLayoutManager3);

        timesAdapter = new TitleAdapter(getContext(), "Calendar");
        timesRecyclerview.setAdapter(timesAdapter);
    }

    private void setFragment(int frameLayout, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}