package com.qtechnetworks.ptplatform.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Fragment.CalendarFragment;
import com.qtechnetworks.ptplatform.View.Fragment.ExercisesWorkoutFragment;
import com.qtechnetworks.ptplatform.View.Fragment.HomeFragment;
import com.qtechnetworks.ptplatform.View.Fragment.MainFragment;
import com.qtechnetworks.ptplatform.View.Fragment.NutritionFragment;
import com.qtechnetworks.ptplatform.View.Fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private TextView home, coach, profile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initials();
        clicks();

        if (PreferencesUtils.getCoach(MainActivity.this) != null){
            setFragment(new MainFragment());
        } else {
            setFragment(new HomeFragment());
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
                if (PreferencesUtils.getCoach(MainActivity.this) != null){
                    setFragment(new MainFragment());
                } else {
                    setFragment(new HomeFragment());
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
        frameLayout = findViewById(R.id.home_frame);
        home = findViewById(R.id.home);
        profile = findViewById(R.id.profile);
        coach = findViewById(R.id.coach);
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.addToBackStack("Home");
        fragmentTransaction.commit();
    }

}