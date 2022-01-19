package com.qtechnetworks.ptplatform.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.qtechnetworks.ptplatform.R;

public class ContactUsActivity extends AppCompatActivity {

    Button feedback_button,technicalsupport_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        initial();

    }

    private void initial(){


        feedback_button=findViewById(R.id.feedback_button);
        technicalsupport_button=findViewById(R.id.technicalsupport_button);


        feedback_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ContactUsActivity.this,FeedAndTechnicalActivity.class).putExtra("title","Feedback"));
                finish();

            }
        });


        technicalsupport_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ContactUsActivity.this,FeedAndTechnicalActivity.class).putExtra("title","Technical Support"));
                finish();

            }
        });


    }

}