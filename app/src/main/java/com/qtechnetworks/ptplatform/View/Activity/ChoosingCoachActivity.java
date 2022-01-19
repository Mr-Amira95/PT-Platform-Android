package com.qtechnetworks.ptplatform.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.qtechnetworks.ptplatform.Controller.adapters.CoachAdapter;
import com.qtechnetworks.ptplatform.R;

public class ChoosingCoachActivity extends AppCompatActivity {

    RecyclerView coach_recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosing_coach);

        initial();

    }

    private void initial(){

        coach_recyclerview=findViewById(R.id.coach_recyclerview);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        coach_recyclerview.setLayoutManager(gridLayoutManager);


        CoachAdapter coachAdapter = new CoachAdapter(getApplicationContext());
        coach_recyclerview.setAdapter(coachAdapter);
        coachAdapter.notifyDataSetChanged();


    }

}