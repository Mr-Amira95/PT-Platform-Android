package com.qtechnetworks.ptplatform.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.CoachSignIn.ThankCoashActivity;

public class SignUpActivity extends AppCompatActivity {

    EditText firstName, lastName, email, mobile, password, socialLink, PotentialClients;
    TextView accountType;
    Button submit_coach_button;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initial();

        submit_coach_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("coach")){
                    startActivity(new Intent(SignUpActivity.this, ThankCoashActivity.class));
                    finish();
                } else if (type.equals("trainee")){
                    Intent i =new Intent(SignUpActivity.this, OtpActivity.class);
                    i.putExtra("email", email.getText().toString());
                    startActivity(i);
                    finish();
                }
            }
        });

    }

    private void traineeDesign() {
        socialLink.setVisibility(View.GONE);
        mobile.setVisibility(View.GONE);
        PotentialClients.setVisibility(View.GONE);

        submit_coach_button.setText("Next");
        accountType.setText("Trainee");
    }

    private void initial(){
        Bundle bundle = getIntent().getExtras();
        type = bundle.getString("type");

        submit_coach_button=findViewById(R.id.submit_coach_button);
        firstName = findViewById(R.id.firstname_coach_edittext);
        lastName = findViewById(R.id.lastname_coach_edittext);
        email = findViewById(R.id.email_coach_signup_edittext);
        password = findViewById(R.id.password_signup_edittext);
        socialLink = findViewById(R.id.socialmedia_coach_signup_edittext);
        mobile = findViewById(R.id.mobilenumber_coach_signup_edittext);
        PotentialClients = findViewById(R.id.potential_coach_signup_edittext);
        accountType = findViewById(R.id.account_type);

        if (type.equals("trainee")){
            traineeDesign();
        }
    }

}