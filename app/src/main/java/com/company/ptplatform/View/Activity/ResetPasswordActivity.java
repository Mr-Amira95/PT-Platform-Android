package com.company.ptplatform.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.company.ptplatform.Controller.networking.CallBack;
import com.company.ptplatform.Model.Beans.General;
import com.company.ptplatform.Model.basic.MyApplication;
import com.company.ptplatform.Model.utilits.AppConstants;
import com.company.ptplatform.R;

import io.reactivex.disposables.Disposable;

public class ResetPasswordActivity extends AppCompatActivity implements CallBack {

    EditText password, confirmPassword;
    Button save;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        initials();
        getArguments();
        clicks();
    }

    private void getArguments() {
        token = getIntent().getStringExtra("token");
    }

    private void clicks() {

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (password.getText().toString().equals(confirmPassword.getText().toString()))
                    resetPassword();
                else
                    Toast.makeText(ResetPasswordActivity.this, R.string.password_doesnt_match, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initials() {
        password = findViewById(R.id.password_edittext);
        confirmPassword = findViewById(R.id.confirm_password_edittext);
        save = findViewById(R.id.confirm_button);
    }

    private void resetPassword() {

        JsonObject params = new JsonObject();
        params.addProperty("token", token);
        params.addProperty("password", password.getText().toString());
        params.addProperty("password_confirmation", confirmPassword.getText().toString());

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().postLogin(this, AppConstants.Reset_Password_URL, AppConstants.Reset_Password_TAG, General.class, params);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        if (isSuccess){
            General general = (General) result;
            Intent i = new Intent(ResetPasswordActivity.this, SignInActivity.class);
            startActivity(i);
            finish();
        }

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}