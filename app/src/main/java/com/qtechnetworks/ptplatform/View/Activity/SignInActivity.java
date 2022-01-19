package com.qtechnetworks.ptplatform.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qtechnetworks.ptplatform.R;

public class SignInActivity extends AppCompatActivity {

    TextView signup_textview;

    Button googlelogin_button,facebooklogin_button,login_button;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initial();

        if (type.equalsIgnoreCase("coach")){
            facebooklogin_button.setVisibility(View.GONE);
            googlelogin_button.setVisibility(View.GONE);
        }

        signup_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignInActivity.this, SignUpActivity.class);
                i.putExtra("type", type);
                startActivity(i);

/*
                if (button.equalsIgnoreCase("true")) {

                }else {

                    startActivity(new Intent(SignInActivity.this, TraineeInfoSignupActivity.class));

                }
*/
            }
        });


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                finish();

            }
        });

    }

    private void initial(){

        Bundle bundle = getIntent().getExtras();
        type = bundle.getString("type");

        signup_textview=findViewById(R.id.signup_textview);
        googlelogin_button=findViewById(R.id.googlelogin_button);
        facebooklogin_button=findViewById(R.id.facebooklogin_button);
        login_button=findViewById(R.id.login_button);
    }

}