package com.qtechnetworks.ptplatform.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.qtechnetworks.ptplatform.R;

public class OtpActivity extends AppCompatActivity {

    Button next_verify_trainee_button;
    String flag, type, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        initial();
        getArguments();

        next_verify_trainee_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag.equalsIgnoreCase("SignUp")) {
                    Intent i = new Intent(OtpActivity.this, SignUpActivity.class);
                    i.putExtra("type", type);
                    i.putExtra("flag", flag);
                    i.putExtra("email", email);
                    startActivity(i);
                } else if (flag.equalsIgnoreCase("ForgotPassword")) {
                    Intent i = new Intent(OtpActivity.this, ResetPasswordActivity.class);
                    i.putExtra("type", type);
                    i.putExtra("flag", flag);
                    i.putExtra("email", email);
                    startActivity(i);
                }

            }
        });

}
    private void getArguments() {
        flag = getIntent().getStringExtra("flag");
        type = getIntent().getStringExtra("type");
        email = getIntent().getStringExtra("email");
    }

    private void initial(){

        next_verify_trainee_button=findViewById(R.id.next_button);

    }

}