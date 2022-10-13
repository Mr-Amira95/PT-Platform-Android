package com.qtechnetworks.ptplatform.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import com.qtechnetworks.ptplatform.R;

public class ThankCoashActivity extends AppCompatActivity {

    public static int SPLASH_TIME_OUT=4000;
    LinearLayout thankYouLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_coash);

        initial();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(ThankCoashActivity.this, SignInActivity.class);
                i.putExtra("type","coach");
                startActivity(i);
                finish();
            }
        },SPLASH_TIME_OUT);

        thankYouLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ThankCoashActivity.this, SignInActivity.class);
                i.putExtra("type","coach");
                startActivity(i);
                finish();
            }
        });
    }
    public void initial(){
        thankYouLayout = findViewById(R.id.thank_you_layout);
    }
}