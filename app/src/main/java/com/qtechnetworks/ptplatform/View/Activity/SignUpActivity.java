package com.qtechnetworks.ptplatform.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

public class SignUpActivity extends AppCompatActivity implements CallBack {

    EditText firstName, lastName, mobile, password, socialLink, PotentialClients;
    TextView accountType;
    Button submit_coach_button;
    String type, email, flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initial();
        getArguments();

        submit_coach_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    register();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                /*if (type.equals("coach")){
                    startActivity(new Intent(SignUpActivity.this, ThankCoashActivity.class));
                    finish();
                } else if (type.equals("trainee")){
                    Intent i =new Intent(SignUpActivity.this, OtpActivity.class);
                    i.putExtra("email", email.getText().toString());
                    startActivity(i);
                    finish();
                }*/
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
        email = bundle.getString("email");

        submit_coach_button=findViewById(R.id.submit_coach_button);
        firstName = findViewById(R.id.firstname_coach_edittext);
        lastName = findViewById(R.id.lastname_coach_edittext);
        password = findViewById(R.id.password_signup_edittext);
        socialLink = findViewById(R.id.socialmedia_coach_signup_edittext);
        mobile = findViewById(R.id.mobilenumber_coach_signup_edittext);
        PotentialClients = findViewById(R.id.potential_coach_signup_edittext);
        accountType = findViewById(R.id.account_type);

        if (type.equals("trainee")){
            traineeDesign();
        }
    }

    private void getArguments() {
        flag = getIntent().getStringExtra("flag");
        type = getIntent().getStringExtra("type");
    }

    private void register() throws JSONException {


        JsonObject jsonObject=new JsonObject();

        jsonObject.addProperty("player_id", "device_player_id");//You can parameterize these values by passing them
        jsonObject.addProperty("platform", "android");
        jsonObject.addProperty("timezone", "Asin/Amman");
        jsonObject.addProperty("app_version", "1.0");


        JsonObject params = new JsonObject();
        params.addProperty("email", email);
        params.addProperty("first_name",firstName.getText().toString());
        params.addProperty("last_name",lastName.getText().toString());
        params.addProperty("password", password.getText().toString());
        params.addProperty("password_confirmation",password.getText().toString());
        params.add("device",jsonObject);


        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().postLogin(this, AppConstants.signup_URL, AppConstants.signup_TAG, Register.class, params);

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        Register register=(Register) result;

        PreferencesUtils.setUserToken(register.getData().getToken());

        PreferencesUtils.setUser(register.getData().getUser(),SignUpActivity.this);


        startActivity(new Intent(SignUpActivity.this,MainActivity.class));
        finish();


    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}