package com.company.ptplatform.View.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.company.ptplatform.Model.utilits.PreferencesUtils;
import com.company.ptplatform.R;
import com.company.ptplatform.View.Activity.MainActivity;
import com.company.ptplatform.View.Activity.SplashActivity;


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
                     ((MainActivity) context).finish();
                 } else if (english.isChecked() && !PreferencesUtils.getLanguage().equalsIgnoreCase("en")) {
                     PreferencesUtils.setLanguage("en");
                     Intent i = new Intent(context ,SplashActivity.class);
                     context.startActivity(i);
                     ((MainActivity) context).finish();
                 } else {
                     Toast.makeText(context, R.string.you_didnt_change_the_language, Toast.LENGTH_SHORT).show();
                 }

            }
        });
    }
}
