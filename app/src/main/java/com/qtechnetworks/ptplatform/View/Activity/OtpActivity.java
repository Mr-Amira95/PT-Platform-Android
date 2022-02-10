package com.qtechnetworks.ptplatform.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.qtechnetworks.ptplatform.R;

public class OtpActivity extends AppCompatActivity {

    Button next_verify_trainee_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        initial();

        next_verify_trainee_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OtpActivity.this, MainActivity.class);
                i.putExtra("type", "trainee");
                startActivity(i);

            }
        });

    }

    private void initial(){

        next_verify_trainee_button=findViewById(R.id.next_button);

    }


}