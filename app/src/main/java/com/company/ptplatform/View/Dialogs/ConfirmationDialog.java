package com.company.ptplatform.View.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.company.ptplatform.Controller.networking.CallBack;
import com.company.ptplatform.Model.Beans.General;
import com.company.ptplatform.Model.basic.MyApplication;
import com.company.ptplatform.Model.utilits.AppConstants;
import com.company.ptplatform.R;
import com.company.ptplatform.View.Activity.MainActivity;
import com.company.ptplatform.View.Fragment.VideoChatFragment;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;


public class ConfirmationDialog extends Dialog implements CallBack {

    String id;
    Context context;
    Button yesBtn, noBtn;

    public ConfirmationDialog(@NonNull Context context, String id) {
        super(context);
        this.id = id;
        this.context = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirmation);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        initials();

    }

    private void initials() {

        yesBtn = findViewById(R.id.yes_btn);
        noBtn = findViewById(R.id.no_btn);

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 cancelSession();
            }
        });
    }

    private void cancelSession(){
        HashMap<String ,Object> params=new HashMap<>();
        params.put("id",id);
        MyApplication.getInstance().getHttpHelper().setCallback(this);
        MyApplication.getInstance().getHttpHelper().Post(context, AppConstants.CANCEL_RESERVATION_URL, AppConstants.CANCEL_RESERVATION_TAG, General.class, params);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {

        if(isSuccess){
            Toast.makeText(context, R.string.reservation_failed, Toast.LENGTH_SHORT).show();
            dismiss();
            setFragment(new VideoChatFragment());
        }

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction= ((MainActivity) context).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.home_frame, fragment).commit();
    }

}
