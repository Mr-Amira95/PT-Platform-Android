package com.company.ptplatform.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.onesignal.OneSignal;
import com.company.ptplatform.BuildConfig;
import com.company.ptplatform.Controller.networking.CallBack;
import com.company.ptplatform.Model.Beans.General;
import com.company.ptplatform.Model.Beans.RegisterAndLogin.Register;
import com.company.ptplatform.Model.basic.MyApplication;
import com.company.ptplatform.Model.utilits.AppConstants;
import com.company.ptplatform.Model.utilits.PreferencesUtils;
import com.company.ptplatform.R;

import io.reactivex.disposables.Disposable;

public class SignUpActivity extends AppCompatActivity implements CallBack{

    EditText firstName, lastName, mobile, password, confirmPassword, socialLink, PotentialClients, emailEditText, potential;
    TextView accountType;
    Button submit_coach_button;
    TextInputLayout potential_parent, social_media_parent, mobile_parent, email_parent;

    String flag, token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initial();
        clicks();
        getArguments();

    }

    private void clicks() {

        submit_coach_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (PreferencesUtils.getUserType().equalsIgnoreCase("Coach")){
                    if (!firstName.getText().toString().isEmpty() && !lastName.getText().toString().isEmpty() && !password.getText().toString().isEmpty() && !confirmPassword.getText().toString().isEmpty() && !potential.getText().toString().isEmpty() && !socialLink.getText().toString().isEmpty() && !mobile.getText().toString().isEmpty() && password.getText().toString().equals(confirmPassword.getText().toString())){
                        registerCoach();
                    } else if (!password.getText().toString().equals(confirmPassword.getText().toString())){
                        Toast.makeText(SignUpActivity.this, R.string.password_doesnt_match, Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(SignUpActivity.this, R.string.the_information_you_entered, Toast.LENGTH_SHORT).show();
                    }
                } else if (PreferencesUtils.getUserType().equalsIgnoreCase("Trainee")){
                    if (!firstName.getText().toString().isEmpty() && !lastName.getText().toString().isEmpty() && !emailEditText.getText().toString().isEmpty() && !password.getText().toString().isEmpty() && !confirmPassword.getText().toString().isEmpty() && password.getText().toString().equals(confirmPassword.getText().toString())){
                        register();
                    } else if (!password.getText().toString().equals(confirmPassword.getText().toString())){
                        Toast.makeText(SignUpActivity.this, R.string.password_doesnt_match, Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(SignUpActivity.this, R.string.the_information_you_entered, Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });

    }

    private void getArguments() {
        flag = getIntent().getStringExtra("flag");
        token = getIntent().getStringExtra("token");
    }

    private void initial() {


        potential_parent=findViewById(R.id.potential_parent);
        social_media_parent=findViewById(R.id.social_media_parent);
        mobile_parent=findViewById(R.id.mobile_parent);
        potential=findViewById(R.id.potential_coach_signup_edittext);

        submit_coach_button=findViewById(R.id.submit_coach_button);
        firstName = findViewById(R.id.firstname_coach_edittext);
        lastName = findViewById(R.id.lastname_coach_edittext);
        password = findViewById(R.id.password_signup_edittext);
        confirmPassword = findViewById(R.id.confirm_password_signup_edittext);
        socialLink = findViewById(R.id.socialmedia_coach_signup_edittext);
        mobile = findViewById(R.id.mobilenumber_coach_signup_edittext);
        PotentialClients = findViewById(R.id.potential_coach_signup_edittext);
        accountType = findViewById(R.id.account_type);
        emailEditText = findViewById(R.id.email_coach_signup_edittext);
        email_parent = findViewById(R.id.email_parent);

        if (PreferencesUtils.getUserType().equalsIgnoreCase("coach")){
            accountType.setText("Coach");
            potential_parent.setVisibility(View.VISIBLE);
            social_media_parent.setVisibility(View.VISIBLE);
            mobile_parent.setVisibility(View.VISIBLE);
            email_parent.setVisibility(View.GONE);
        } else if (PreferencesUtils.getUserType().equalsIgnoreCase("trainee")){
            accountType.setText("Trainee");
            potential_parent.setVisibility(View.GONE);
            social_media_parent.setVisibility(View.GONE);
            mobile_parent.setVisibility(View.GONE);
            email_parent.setVisibility(View.VISIBLE);
        }
    }

    private void register() {

        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("player_id", OneSignal.getDeviceState().getUserId() );//You can parameterize these values by passing them
        jsonObject.addProperty("platform", "android");
        jsonObject.addProperty("timezone", "Asian/Amman");
        jsonObject.addProperty("app_version", BuildConfig.VERSION_CODE);

        JsonObject params = new JsonObject();
        params.addProperty("email", emailEditText.getText().toString());
        params.addProperty("first_name",firstName.getText().toString());
        params.addProperty("last_name",lastName.getText().toString());
        params.addProperty("password", password.getText().toString());
        params.addProperty("password_confirmation",confirmPassword.getText().toString());
        params.add("device",jsonObject);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().postLogin(this, AppConstants.signup_URL, AppConstants.signup_TAG, Register.class, params);
    }

    private void registerCoach() {

        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("player_id", OneSignal.getDeviceState().getUserId());
        jsonObject.addProperty("platform", "android");
        jsonObject.addProperty("timezone", "Asian/Amman");
        jsonObject.addProperty("app_version", BuildConfig.VERSION_CODE);

        JsonObject params = new JsonObject();
        params.addProperty("token", token);
        params.addProperty("full_name",firstName.getText().toString());
        params.addProperty("nick_name",lastName.getText().toString());
        params.addProperty("link_social_media", socialLink.getText().toString());
        params.addProperty("potential_clients",potential.getText().toString());
        params.addProperty("phone_number",mobile.getText().toString());
        params.addProperty("password",password.getText().toString());
        params.addProperty("password_confirmation",confirmPassword.getText().toString());
        params.add("device",jsonObject);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().postLogin(this, AppConstants.signup_coach_URL, AppConstants.signup_coach_TAG, General.class, params);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        if (isSuccess) {

            switch (tag){
                case AppConstants.signup_coach_TAG:

                    General general = (General) result;
                    Toast.makeText(this, general.getData().toString(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SignUpActivity.this, ThankCoashActivity.class);
                    i.putExtra("flag", "signUp");
                    startActivity(i);
                    break;

                case AppConstants.signup_TAG:

                    Register register = (Register) result;
                    PreferencesUtils.setUser(register.getData().getUser(),SignUpActivity.this);
                    PreferencesUtils.setUserToken(register.getData().getToken());
                    PreferencesUtils.setPlayerId(OneSignal.getDeviceState().getUserId());

                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}