package com.qtechnetworks.ptplatform.View.Activity.CoachSignIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.ChoosingActivity;
import com.qtechnetworks.ptplatform.View.Activity.SplashActivity;
import com.qtechnetworks.ptplatform.View.Activity.TraineeSignIn.SignInTraineeActivity;

public class ThankCoashActivity extends AppCompatActivity {

    public static int SPLASH_TIME_OUT=4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_coash);

        initial();


    }

    public void initial(){


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(ThankCoashActivity.this, SignInTraineeActivity.class).putExtra("button","true"));
                finish();

            }
        },SPLASH_TIME_OUT);

    }


}