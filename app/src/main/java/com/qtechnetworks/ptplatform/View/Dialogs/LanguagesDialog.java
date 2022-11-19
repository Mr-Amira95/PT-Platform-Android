package com.qtechnetworks.ptplatform.View.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.General;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;
import com.qtechnetworks.ptplatform.View.Activity.SplashActivity;
import com.qtechnetworks.ptplatform.View.Fragment.VideoChatFragment;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;


public class LanguagesDialog extends Dialog {

    Context context;
    Button yesBtn, noBtn;
    RadioButton arabic, english;

    public LanguagesDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_languages);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        initials();

    }

    private void initials() {

        yesBtn = findViewById(R.id.yes_btn);
        noBtn = findViewById(R.id.no_btn);
        arabic = findViewById(R.id.arabic_language);
        english = findViewById(R.id.english_language);

        if (PreferencesUtils.getLanguage().equalsIgnoreCase("ar")){
            arabic.setChecked(true);
        } else if (PreferencesUtils.getLanguage().equalsIgnoreCase("en")){
            english.setChecked(true);
        }

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if (arabic.isChecked() && !PreferencesUtils.getLanguage().equalsIgnoreCase("ar")){
                     PreferencesUtils.setLanguage("ar");
                     Intent i = new Intent(context ,SplashActivity.class);
                     context.startActivity(i);
                 } else if (english.isChecked() && !PreferencesUtils.getLanguage().equalsIgnoreCase("en")) {
                     PreferencesUtils.setLanguage("en");
                     Intent i = new Intent(context ,SplashActivity.class);
                     context.startActivity(i);
                 } else {
                     Toast.makeText(context, R.string.you_didnt_change_the_language, Toast.LENGTH_SHORT).show();
                 }

            }
        });
    }
}
