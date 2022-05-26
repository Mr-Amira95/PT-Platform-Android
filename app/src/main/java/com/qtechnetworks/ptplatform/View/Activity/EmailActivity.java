package com.qtechnetworks.ptplatform.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.qtechnetworks.ptplatform.R;

public class EmailActivity extends AppCompatActivity {

    Button verifyBtn;
    EditText email;

    String flag, type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        getArguments();

        initials();
        clicks();
    }

    private void getArguments() {
        flag = getIntent().getStringExtra("flag");
        type = getIntent().getStringExtra("type");
    }

    private void clicks() {

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EmailActivity.this, OtpActivity.class);
                i.putExtra("flag", flag);
                i.putExtra("type", type);
                i.putExtra("email", email.getText().toString());
                startActivity(i);
            }
        });

    }

    private void initials() {
        verifyBtn = findViewById(R.id.verify_button);
        email = findViewById(R.id.email_edittext);
    }
}