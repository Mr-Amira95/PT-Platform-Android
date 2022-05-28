package com.qtechnetworks.ptplatform.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.RegisterAndLogin.Register;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SignInActivity extends AppCompatActivity implements CallBack {

    TextView signup_textview, forgotPassword;

    EditText email_login_edittext,password_login_edittext;

    Button googlelogin_button,facebooklogin_button,login_button;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initial();
        clicks();

        if (type.equalsIgnoreCase("coach")){
            facebooklogin_button.setVisibility(View.GONE);
            googlelogin_button.setVisibility(View.GONE);
        }

    }

    private void clicks() {
        signup_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SignInActivity.this, SignUpActivity.class);
                i.putExtra("type", type);
                i.putExtra("flag", "SignUp");
                startActivity(i);


//                    Intent i = new Intent(SignInActivity.this, EmailActivity.class);
//                    i.putExtra("type", type);
//                    i.putExtra("flag", "SignUp");
//                    startActivity(i);
            }
        });


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    checkLogin();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignInActivity.this, EmailActivity.class);
                i.putExtra("type", type);
                i.putExtra("flag", "ForgotPassword");
                startActivity(i);
            }
        });

        googlelogin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignInActivity.this, "Later", Toast.LENGTH_LONG).show();
            }
        });

        facebooklogin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignInActivity.this, "Later", Toast.LENGTH_LONG).show();
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
        email_login_edittext=findViewById(R.id.email_login_edittext);
        password_login_edittext=findViewById(R.id.password_login_edittext);
        forgotPassword = findViewById(R.id.forgot_password);
    }


    private void checkLogin() throws JSONException {

        JsonObject jsonObject=new JsonObject();

        jsonObject.addProperty("player_id", "device_player_id");//You can parameterize these values by passing them
        jsonObject.addProperty("platform", "android");
        jsonObject.addProperty("timezone", "Asin/Amman");
        jsonObject.addProperty("app_version", "1.0");


        JsonObject params = new JsonObject();
        params.addProperty("email", email_login_edittext.getText().toString());
        params.addProperty("password", password_login_edittext.getText().toString());
        params.add("device",jsonObject);


        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().postLogin(this, AppConstants.login_URL, AppConstants.login_TAG, Register.class, params);

    }


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        Register register=(Register) result;

        PreferencesUtils.setUserToken(register.getData().getToken());

        PreferencesUtils.setUser(register.getData().getUser(),SignInActivity.this);


        startActivity(new Intent(SignInActivity.this,MainActivity.class));
        finish();

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}