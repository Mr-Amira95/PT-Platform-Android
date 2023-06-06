package com.qtechnetworks.ptplatform.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.CheckEmail.CheckEmailResults;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.R;

import io.reactivex.disposables.Disposable;

public class EmailActivity extends AppCompatActivity implements CallBack {

    Button verifyBtn;
    EditText email;

    String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        initials();
        clicks();
    }

    private void clicks() {

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!email.getText().toString().isEmpty())
                    if (flag.equalsIgnoreCase("forgotPassword"))
                        checkEmail(email.getText().toString());
                    else
                        checkCoachEmail(email.getText().toString());
                else
                    Toast.makeText(EmailActivity.this, getString(R.string.fill_the_email_field), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void initials() {

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            flag = extras.getString("flag");
        }

        verifyBtn = findViewById(R.id.verify_button);
        email = findViewById(R.id.email_edittext);
    }

    private void checkEmail(String email) {

        JsonObject params = new JsonObject();
        params.addProperty("email", email);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().postLogin(this, AppConstants.Check_Email_URL, AppConstants.Check_Email_TAG, CheckEmailResults.class, params);
    }

    private void checkCoachEmail(String email) {
        JsonObject params = new JsonObject();
        params.addProperty("email", email);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().postLogin(this, AppConstants.Check_Email_Coach_URL, AppConstants.Check_Email_Coach_TAG, CheckEmailResults.class, params);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        if (isSuccess){
            CheckEmailResults checkEmailResults = (CheckEmailResults) result;

            Intent i = new Intent(EmailActivity.this, OtpActivity.class);
            i.putExtra("token",checkEmailResults.getData().getToken());
            i.putExtra("flag", flag);
            startActivity(i);
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}