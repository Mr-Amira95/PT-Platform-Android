package com.company.ptplatform.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.company.ptplatform.R;

public class ThankCoashActivity extends AppCompatActivity {

    public static int SPLASH_TIME_OUT=4000;

    LinearLayout thankYouLayout;
    TextView title, desc;

    String flag = getIntent().getStringExtra("flag");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_coash);

        initial();

        flag = getIntent().getStringExtra("flag");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (flag.equalsIgnoreCase("signUp")){
                    Intent i = new Intent(ThankCoashActivity.this, SignInActivity.class);
                    i.putExtra("type","coach");
                    startActivity(i);
                    finish();
                }
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
        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);

        if (flag.equalsIgnoreCase("reject")){
            title.setText(getString(R.string.rejected));
            title.setText(getString(R.string.rejected_message));
        } else if (flag.equalsIgnoreCase("requested")){
            title.setText(getString(R.string.requesed));
        }
    }
}