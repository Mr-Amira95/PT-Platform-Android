package com.qtechnetworks.ptplatform.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

public class SignInActivity extends AppCompatActivity implements CallBack {

    TextView signup_textview;

    EditText email_login_edittext,password_login_edittext;

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
    }


    private void checkLogin() throws JSONException {

        JSONObject jsonObject=new JSONObject();

        HashMap<String, Object> params = new HashMap<>();
        params.put("email", email_login_edittext.getText().toString());
        params.put("password", password_login_edittext.getText().toString());
        params.put("device",jsonObject);


        jsonObject.accumulate("player_id", "device_player_id");//You can parameterize these values by passing them
        jsonObject.accumulate("platform", "android");
        jsonObject.accumulate("timezone", "Asin/Amman");
        jsonObject.accumulate("app_version", "1.0");

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