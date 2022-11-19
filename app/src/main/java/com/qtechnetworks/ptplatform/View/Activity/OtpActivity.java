package com.qtechnetworks.ptplatform.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.gson.JsonObject;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.CheckCode.CheckCode;
import com.qtechnetworks.ptplatform.Model.Beans.CheckCode.ResendCodeResults;
import com.qtechnetworks.ptplatform.Model.Beans.General;
import com.qtechnetworks.ptplatform.Model.Beans.RegisterAndLogin.Register;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;

import io.reactivex.disposables.Disposable;

public class OtpActivity extends AppCompatActivity implements CallBack {

    Button next_verify_trainee_button;
    EditText otp;
    TextView resend;
    String flag, token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        initial();
        clicks();
        getArguments();

    }

    private void clicks() {

        next_verify_trainee_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (otp.getText().toString().length() > 0){
                    if (flag.equalsIgnoreCase("signUp")) {
                        checkCode(otp.getText().toString());
                    } else if (flag.equalsIgnoreCase("forgotPassword")) {
                        checkCode(otp.getText().toString());
                    }
                } else {
                    Toast.makeText(OtpActivity.this, R.string.please_check_the_verification_code, Toast.LENGTH_SHORT).show();
                }
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendCode();
            }
        });

    }

    private void getArguments() {
        flag = getIntent().getStringExtra("flag");
        token = getIntent().getStringExtra("token");

//        if (flag.equalsIgnoreCase("forgotPassword")) {
//        } else if (flag.equalsIgnoreCase("signUp")){
//            type = getIntent().getStringExtra("type");
//            email = getIntent().getStringExtra("email");
//            firstName = getIntent().getStringExtra("first_name");
//            lastName = getIntent().getStringExtra("last_name");
//            password = getIntent().getStringExtra("password");
//            confirmPassword = getIntent().getStringExtra("password_confirmation");
//        }
    }

    private void initial(){
        next_verify_trainee_button = findViewById(R.id.next_button);
        resend = findViewById(R.id.resend_textview);
        otp = findViewById(R.id.otp);
    }

    private void checkCode(String s) {

        JsonObject params = new JsonObject();
        params.addProperty("token", token);
        params.addProperty("code", s);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().postLogin(this, AppConstants.Check_Code_URL, AppConstants.Check_Code_TAG, CheckCode.class, params);
    }

    private void resendCode() {

        JsonObject params = new JsonObject();
        params.addProperty("token", token);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().postLogin(this, AppConstants.Resend_Code_URL, AppConstants.Resend_Code_TAG, ResendCodeResults.class, params);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        if(isSuccess) {

            switch (tag){

                case AppConstants.Check_Code_TAG:

                    CheckCode checkCode = (CheckCode) result;

                        if (checkCode.getSuccess()){
                            if (flag.equalsIgnoreCase("signUp")){
                                Intent i = new Intent(OtpActivity.this, SignUpActivity.class);
                                i.putExtra("token", token);
                                startActivity(i);
                            } else if (flag.equalsIgnoreCase("forgotPassword")) {
                                Intent i = new Intent(OtpActivity.this, ResetPasswordActivity.class);
                                i.putExtra("token", token);
                                startActivity(i);
                            }

                        } else if (checkCode.getErrors().getCode() != null)
                            Toast.makeText(OtpActivity.this, checkCode.getErrors().getCode(),Toast.LENGTH_SHORT).show();

                    break;

                case AppConstants.Resend_Code_TAG:

                    ResendCodeResults resendCodeResults = (ResendCodeResults) result;

                    if (resendCodeResults.getSuccess())
                        token = resendCodeResults.getData().getToken();
                    else
                        Toast.makeText(OtpActivity.this, resendCodeResults.getData().getToken(),Toast.LENGTH_SHORT).show();

                    break;
            }

        } else {
            Toast.makeText(this, R.string.please_check_inputs, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}