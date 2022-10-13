package com.qtechnetworks.ptplatform.View.Activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Fragment.ChooseCoachFragment;
import com.qtechnetworks.ptplatform.View.Fragment.HomeFragment;
import com.qtechnetworks.ptplatform.View.Fragment.MainCoachFragment;
import com.qtechnetworks.ptplatform.View.Fragment.MainFragment;
import com.qtechnetworks.ptplatform.View.Fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private TextView home, coach, profile;
    private LinearLayout bottomBar;

    public static Activity me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initials();
        clicks();

        me = this;

        if (PreferencesUtils.getUserType().equalsIgnoreCase("coach")) {
            setFragmentwithoutBack(new MainCoachFragment());
        } else if (PreferencesUtils.getUserType().equalsIgnoreCase("trainee")){
            if (PreferencesUtils.getCoach(MainActivity.this) != null) {
                if (getIntent().getExtras() != null && getIntent().getExtras().getString("page").equals("shop"))
                    setFragmentwithoutBack(new MainFragment("shop"));
                else
                    setFragmentwithoutBack(new MainFragment());
            } else {
                setFragmentwithoutBack(new HomeFragment());
            }
        }

    }

    private void clicks() {

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new HomeFragment());
            }
        });

        coach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (PreferencesUtils.getCoach(MainActivity.this) != null)
                    setFragment(new MainFragment());
                else{
                    Toast.makeText(MainActivity.this, "Please choose coach", Toast.LENGTH_SHORT).show();
                    setFragment(new ChooseCoachFragment());
                }
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new ProfileFragment());
            }
        });
    }

    private void initials() {
        bottomBar = findViewById(R.id.bottom_nav_bar);

        home = findViewById(R.id.home);
        profile = findViewById(R.id.profile);
        coach = findViewById(R.id.coach);

        if (PreferencesUtils.getUserType().equalsIgnoreCase("coach")){
            bottomBar.setVisibility(View.GONE);
        } else if (PreferencesUtils.getUserType().equalsIgnoreCase("trainee")){
            bottomBar.setVisibility(View.VISIBLE);
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.home_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void setFragmentwithoutBack(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.home_frame, fragment);
        fragmentTransaction.commit();
    }

}