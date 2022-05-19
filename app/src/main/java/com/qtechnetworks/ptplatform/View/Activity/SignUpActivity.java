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

public class SignUpActivity extends AppCompatActivity implements CallBack {

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

    private void register() throws JSONException {

        JSONObject jsonObject=new JSONObject();

        HashMap<String, Object> params = new HashMap<>();
        params.put("email", email.getText().toString());
        params.put("first_name","");
        params.put("last_name","");
        params.put("password", password.getText().toString());
        params.put("password_confirmation",password.getText().toString());
        params.put("device",jsonObject);


        jsonObject.accumulate("player_id", "device_player_id");//You can parameterize these values by passing them
        jsonObject.accumulate("platform", "android");
        jsonObject.accumulate("timezone", "Asin/Amman");
        jsonObject.accumulate("app_version", "1.0");

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