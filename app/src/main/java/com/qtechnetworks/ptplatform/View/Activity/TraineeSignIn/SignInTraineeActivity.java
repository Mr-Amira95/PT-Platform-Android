package com.qtechnetworks.ptplatform.View.Activity.TraineeSignIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.CoachSignIn.SignUpCoashActivity;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;

public class SignInTraineeActivity extends AppCompatActivity {

    TextView signup_textview;

    Button googlelogin_button,facebooklogin_button,login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initial();

    }

    private void initial(){

        Bundle bundle = getIntent().getExtras();

        signup_textview=findViewById(R.id.signup_textview);
        googlelogin_button=findViewById(R.id.googlelogin_button);
        facebooklogin_button=findViewById(R.id.facebooklogin_button);
        login_button=findViewById(R.id.login_button);

        String button=bundle.getString("button","1");


        if (button.equalsIgnoreCase("true")){

            facebooklogin_button.setVisibility(View.GONE);
            googlelogin_button.setVisibility(View.GONE);

        }

        signup_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bundle.getString("button","1").equalsIgnoreCase("true")) {

                    startActivity(new Intent(SignInTraineeActivity.this, SignUpCoashActivity.class));

                }else {

                    startActivity(new Intent(SignInTraineeActivity.this, TraineeInfoSignupActivity.class));

                }

            }
        });


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SignInTraineeActivity.this, MainActivity.class));
                finish();

            }
        });


    }

}