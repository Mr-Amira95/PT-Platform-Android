package com.qtechnetworks.ptplatform.View.Activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    String flag = " ", id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initials();
        clicks();

        me = this;

        if (flag != null) {

            if (flag.equalsIgnoreCase("news_feed") ||
                    flag.equalsIgnoreCase("admin")) {
                setFragmentwithoutBack(new HomeFragment(flag, id));
            } else if (flag.equalsIgnoreCase("expired_package") ||
                    flag.equalsIgnoreCase("calender") ||
                    flag.equalsIgnoreCase("approved_video_chat") ||
                    flag.equalsIgnoreCase("message") ||
                    flag.equalsIgnoreCase("new_coach") ||
                    flag.equalsIgnoreCase("personal_training") ||
                    flag.equalsIgnoreCase("shop")) {
                setFragmentwithoutBack(new MainFragment(flag, id));
            } else if (flag.equalsIgnoreCase("support")){
                setFragmentwithoutBack(new HomeFragment());
            } else {
                if (PreferencesUtils.getUserType().equalsIgnoreCase("coach")) {
                    setFragmentwithoutBack(new MainCoachFragment());
                } else if (PreferencesUtils.getUserType().equalsIgnoreCase("trainee")){
                    if (PreferencesUtils.getCoach(MainActivity.this) != null) {
                        setFragmentwithoutBack(new MainFragment());
                    } else {
                        setFragmentwithoutBack(new HomeFragment());
                    }
                }
            }

//            if (flag.equalsIgnoreCase("new_coach") || flag.equalsIgnoreCase("expired_package") || flag.equalsIgnoreCase("personal_trainer") || flag.equalsIgnoreCase("approved_video_chat") || flag.equalsIgnoreCase("session_reminder") || flag.equalsIgnoreCase("chat")){
//                setFragmentwithoutBack(new MainFragment(flag, id));
//            } else if (flag.equalsIgnoreCase("news_feed") || flag.equalsIgnoreCase("admin") || flag.equalsIgnoreCase("cancel")){
//                setFragmentwithoutBack(new HomeFragment(flag, id));
//            } else {
//                if (PreferencesUtils.getUserType().equalsIgnoreCase("coach")) {
//                    setFragmentwithoutBack(new MainCoachFragment());
//                } else if (PreferencesUtils.getUserType().equalsIgnoreCase("trainee")){
//                    if (PreferencesUtils.getCoach(MainActivity.this) != null) {
//                        if (getIntent().getExtras() != null && getIntent().getExtras().getString("page").equals("shop"))
//                            setFragmentwithoutBack(new MainFragment("shop"));
//                        else
//                            setFragmentwithoutBack(new MainFragment());
//                    } else {
//                        setFragmentwithoutBack(new HomeFragment());
//                    }
//                }
//            }

        } else {

            if (PreferencesUtils.getUserType().equalsIgnoreCase("coach")) {
                setFragmentwithoutBack(new MainCoachFragment());
            } else if (PreferencesUtils.getUserType().equalsIgnoreCase("trainee")){
                if (PreferencesUtils.getCoach(MainActivity.this) != null) {
//                    if (page != null)
//                        setFragmentwithoutBack(new MainFragment("shop"));
//                    else
                        setFragmentwithoutBack(new MainFragment());
                } else {
                    setFragmentwithoutBack(new HomeFragment());
                }
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

        flag = getIntent().getStringExtra("flag");
        id = getIntent().getStringExtra("id");

//        if (getIntent().getExtras().getString("page") != null)
//            page = getIntent().getExtras().getString("page");

        if (PreferencesUtils.getUserType().equalsIgnoreCase("coach")){
            bottomBar.setVisibility(View.GONE);
        } else if (PreferencesUtils.getUserType().equalsIgnoreCase("trainee")){
            bottomBar.setVisibility(View.VISIBLE);
        }

        Log.d("flag_main", flag + "/" + id);

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