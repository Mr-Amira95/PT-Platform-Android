package com.qtechnetworks.ptplatform.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonObject;
import com.onesignal.OneSignal;
import com.qtechnetworks.ptplatform.BuildConfig;
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

    TextView signup_textview, forgotPassword, orTxt;
    EditText email_login_edittext,password_login_edittext;
    Button googlelogin_button,facebooklogin_button,login_button;

    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

//        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
//
//        // OneSignal Initialization
//        OneSignal.initWithContext(this);
//        OneSignal.setAppId(ONESIGNAL_APP_ID);

        initial();
        clicks();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().requestIdToken("778836019800-ae9t16kd468u9niin041l9j64a3cdqmo.apps.googleusercontent.com").build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


    }

    private void clicks() {

        signup_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (PreferencesUtils.getUserType().equalsIgnoreCase("coach")){
                    startActivity(new Intent(SignInActivity.this, EmailActivity.class).putExtra("flag", "signUp"));
                } else if (PreferencesUtils.getUserType().equalsIgnoreCase("trainee")){
                    startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                }
            }
        });


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (email_login_edittext.getText().toString().isEmpty() && password_login_edittext.getText().toString().isEmpty())
                    Toast.makeText(SignInActivity.this, R.string.please_fill_all_fields, Toast.LENGTH_SHORT).show();
                else if (email_login_edittext.getText().toString().isEmpty())
                    Toast.makeText(SignInActivity.this, R.string.fill_the_email_field, Toast.LENGTH_SHORT).show();
                else if (password_login_edittext.getText().toString().isEmpty())
                    Toast.makeText(SignInActivity.this, R.string.please_fill_password_field, Toast.LENGTH_SHORT).show();
                else
                    checkLogin();

            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this,EmailActivity.class).putExtra("flag", "forgotPassword"));
            }
        });

        googlelogin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(SignInActivity.this);
                if (account != null){
                    socialLogin("google", account.getId(), account.getIdToken(), account.getDisplayName(), account.getEmail());
                } else {
                    googleSignIn();
                }
            }
        });

        facebooklogin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //socialLogin("facebook");
            }
        });

    }

    private void socialLogin(String provider, String providerId, String providerToken, String name, String email) {

        JsonObject jsonObject=new JsonObject();

        jsonObject.addProperty("player_id",OneSignal.getDeviceState().getUserId()); //You can parameterize these values by passing them
        jsonObject.addProperty("platform", "android");
        jsonObject.addProperty("timezone", "Asian/Amman");
        jsonObject.addProperty("app_version", BuildConfig.VERSION_CODE);

        JsonObject params = new JsonObject();
        params.addProperty("email", email);
        params.addProperty("name", name);
        params.addProperty("provider", provider);
        params.addProperty("provider_id", providerId);
        params.addProperty("provider_token", providerToken);
        params.add("device",jsonObject);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().postLogin(this, AppConstants.SOCIAL_LOGIN_URL, AppConstants.SOCIAL_LOGIN_TAG, Register.class, params);

    }

    private void initial() {

        orTxt=findViewById(R.id.or_txt);
        signup_textview=findViewById(R.id.signup_textview);
        googlelogin_button=findViewById(R.id.googlelogin_button);
        facebooklogin_button=findViewById(R.id.facebooklogin_button);
        login_button=findViewById(R.id.login_button);
        email_login_edittext=findViewById(R.id.email_login_edittext);
        password_login_edittext=findViewById(R.id.password_login_edittext);
        forgotPassword = findViewById(R.id.forgot_password);

        if (PreferencesUtils.getUserType().equalsIgnoreCase("coach")) {
            orTxt.setVisibility(View.GONE);
            googlelogin_button.setVisibility(View.GONE);
            facebooklogin_button.setVisibility(View.GONE);
        } else if (PreferencesUtils.getUserType().equalsIgnoreCase("trainee")) {
            orTxt.setVisibility(View.VISIBLE);
            googlelogin_button.setVisibility(View.VISIBLE);
            facebooklogin_button.setVisibility(View.GONE);
        }
    }


    private void checkLogin() {

        JsonObject jsonObject=new JsonObject();

        jsonObject.addProperty("player_id",OneSignal.getDeviceState().getUserId()); //You can parameterize these values by passing them
        jsonObject.addProperty("platform", "android");
        jsonObject.addProperty("timezone", "Asian/Amman");
        jsonObject.addProperty("app_version", BuildConfig.VERSION_CODE);

        JsonObject params = new JsonObject();
        params.addProperty("email", email_login_edittext.getText().toString());
        params.addProperty("password", password_login_edittext.getText().toString());
        params.add("device",jsonObject);

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().postLogin(this, AppConstants.login_URL, AppConstants.login_TAG, Register.class, params);

    }

    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 100);
    }


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        Register register = (Register) result;

        if (register.getData().getUser().getStatus().equalsIgnoreCase("accept") || register.getData().getUser().getStatus().equalsIgnoreCase("waite")){

            PreferencesUtils.setUserToken(register.getData().getToken());
            PreferencesUtils.setUser(register.getData().getUser(),SignInActivity.this);
            PreferencesUtils.setPlayerId(OneSignal.getDeviceState().getUserId());

            if (register.getData().getUser().getRole().equalsIgnoreCase("user"))
                PreferencesUtils.setUserType("trainee");
            else
                PreferencesUtils.setUserType("coach");

            startActivity(new Intent(SignInActivity.this, MainActivity.class));
            finish();

        } else if (register.getData().getUser().getStatus().equalsIgnoreCase("reject")){
            Toast.makeText(this, "your account is rejected, contact support", Toast.LENGTH_SHORT).show();
        } else if  (register.getData().getUser().getStatus().equalsIgnoreCase("requested")){
            Toast.makeText(this, "Please wait until admin approval", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 100) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {

        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            String providerId = account.getId();
            String providerToken = account.getIdToken();
            String name = account.getDisplayName();
            String email = account.getEmail();

            socialLogin("google", providerId, providerToken, name, email);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("google login", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(SignInActivity.this, R.string.sign_in_failed, Toast.LENGTH_SHORT).show();
        }
    }

}