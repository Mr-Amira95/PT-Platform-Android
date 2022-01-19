package com.qtechnetworks.ptplatform.View.Activity.TraineeSignIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;

public class EmailVerifyTraineeActivity extends AppCompatActivity {

    Button next_verify_trainee_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verify_trainee);

        initial();

    }

    private void initial(){

        next_verify_trainee_button=findViewById(R.id.next_verify_trainee_button);

        next_verify_trainee_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(EmailVerifyTraineeActivity.this, MainActivity.class));

            }
        });

    }


}