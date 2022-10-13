package com.qtechnetworks.ptplatform.View.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.General;
import com.qtechnetworks.ptplatform.Model.Beans.RegisterAndLogin.UpdateUserResults;
import com.qtechnetworks.ptplatform.Model.Beans.RegisterAndLogin.User;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;
import com.qtechnetworks.ptplatform.View.Fragment.ProfileFragment;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class EditProfileDialog  extends Dialog implements CallBack {


    Context mContext;
    EditText firstNameEt,lastNameEt,emailEt;
    Button updateBtn;

    public EditProfileDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit_profile);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        initials();
        clicks();

    }

    private void clicks() {

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validation(firstNameEt) && validation(lastNameEt) && validation(emailEt)) {
                    updateName();
                } else {
                    Toast.makeText(mContext, "Please Check info", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void updateEmail(){

        HashMap<String ,Object> params=new HashMap<>();

        params.put("email", emailEt.getText().toString());

        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().Post(getContext(), AppConstants.UPDATE_EMAIL_URL, AppConstants.UPDATE_EMAIL_TAG, UpdateUserResults.class, params);
    }


    private void updateName(){
        HashMap<String ,Object> params=new HashMap<>();

        params.put("first_name", firstNameEt.getText().toString());
        params.put("last_name", lastNameEt.getText().toString());

        MyApplication.getInstance().getBackgroundHttpHelper().setCallback(this);
        MyApplication.getInstance().getBackgroundHttpHelper().Post(getContext(), AppConstants.UPDATE_NAME_URL, AppConstants.UPDATE_NAME_TAG, UpdateUserResults.class, params);
    }

    private boolean validation(EditText et){
        if( TextUtils.isEmpty(et.getText())){
            et.setError( "this field is required!" );
            return false;
        }
        return true;
    }
    private void initials() {

        emailEt=findViewById(R.id.email_et);
        firstNameEt=findViewById(R.id.first_name_et);
        lastNameEt=findViewById(R.id.last_name_et);
        updateBtn=findViewById(R.id.update_btn);

        emailEt.setText(PreferencesUtils.getUser(getContext()).getEmail());
        firstNameEt.setText(PreferencesUtils.getUser(getContext()).getFirstName());
        lastNameEt.setText(PreferencesUtils.getUser(getContext()).getLastName());
    }

    private void setFragment(Fragment fragment ) {

        Bundle args = new Bundle();
        fragment.setArguments(args);
        ((MainActivity) mContext).getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, fragment, "OptionsFragment").commit();

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        if (isSuccess){
            UpdateUserResults updateUserResults = (UpdateUserResults) result;

            switch (tag){

                case AppConstants.UPDATE_NAME_TAG:
                    updateEmail();
                    break;

                case AppConstants.UPDATE_EMAIL_TAG:
                    Toast.makeText(mContext, "Updated", Toast.LENGTH_SHORT).show();
                    PreferencesUtils.setUser(updateUserResults.getData(),getContext());
                    setFragment(new ProfileFragment());
                    dismiss();
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
