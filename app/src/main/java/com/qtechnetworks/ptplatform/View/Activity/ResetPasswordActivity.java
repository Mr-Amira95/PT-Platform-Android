package com.qtechnetworks.ptplatform.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.qtechnetworks.ptplatform.R;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText password, confirmPassword;
    Button save;
    String type, email, flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        initials();
        getArguments();
        clicks();
    }

    private void getArguments() {
        flag = getIntent().getStringExtra("flag");
        type = getIntent().getStringExtra("type");
        email = getIntent().getStringExtra("email");
    }

    private void clicks() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ResetPasswordActivity.this, SignInActivity.class);
                i.putExtra("type", type);
                startActivity(i);
            }
        });
    }

    private void initials() {
        password = findViewById(R.id.password_edittext);
        confirmPassword = findViewById(R.id.confirm_password_edittext);
        save = findViewById(R.id.confirm_button);
    }
}