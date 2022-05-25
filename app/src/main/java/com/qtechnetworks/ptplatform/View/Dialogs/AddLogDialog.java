package com.qtechnetworks.ptplatform.View.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.qtechnetworks.ptplatform.R;


public class AddLogDialog extends Dialog {

    Context mContext;

    public AddLogDialog(@NonNull Context context) {
        super(context);
        this.mContext = mContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_languages);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        initials();
        clicks();

    }

    private void clicks() {
        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(mContext, MainActivity.class);
                mContext.startActivity(i);
            }
        });

        arabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, MainActivity.class);
                mContext.startActivity(i);
            }
        });

    }

    private void initials() {
        english = findViewById(R.id.english);
        arabic = findViewById(R.id.arabic);
        closeIcon = findViewById(R.id.close_icon);
    }

}
