package com.qtechnetworks.ptplatform.View.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.utilits.UtilisMethods;
import com.qtechnetworks.ptplatform.R;

import io.reactivex.disposables.Disposable;


public class AddLogDialog extends Dialog implements CallBack {

    Spinner setNumber, weightUnit;
    EditText repeat, weight, note;
    Button save_btn;

    Context mContext;

    public AddLogDialog(@NonNull Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_log);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        initials();
        clicks();

    }

    private void clicks() {

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLog();
            }
        });


    }

    private void saveLog() {

    }

    private void initials() {
        setNumber = findViewById(R.id.set_number);
        weightUnit = findViewById(R.id.weight_unit);
        weight = findViewById(R.id.weight);
        repeat = findViewById(R.id.repeat);
        note = findViewById(R.id.note);
        save_btn = findViewById(R.id.save_btn);


    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {


    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
