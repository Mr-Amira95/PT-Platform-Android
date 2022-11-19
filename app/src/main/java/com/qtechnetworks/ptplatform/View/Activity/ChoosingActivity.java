package com.qtechnetworks.ptplatform.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;

public class ChoosingActivity extends AppCompatActivity {

    Button next_button;

    ImageView imageView_coach,imageView_trainee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosing);

        initial();
        clicks();

    }

    private void clicks() {

        imageView_coach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView_coach.setImageResource(R.drawable.checked);
                imageView_trainee.setImageResource(R.drawable.notcheck);
                PreferencesUtils.setUserType("coach");
            }
        });

        imageView_trainee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView_trainee.setImageResource(R.drawable.checked);
                imageView_coach.setImageResource(R.drawable.notcheck);
                PreferencesUtils.setUserType("trainee");
            }
        });


        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (PreferencesUtils.getUserType().equalsIgnoreCase("coach")){
                    startActivity(new Intent(ChoosingActivity.this, SignInActivity.class));
                } else if (PreferencesUtils.getUserType().equalsIgnoreCase("trainee")){
                    startActivity(new Intent(ChoosingActivity.this, SignInActivity.class));
                }

            }
        });

    }

    private void initial(){
        imageView_coach=findViewById(R.id.imageView_coach);
        imageView_trainee=findViewById(R.id.imageView_trainee);
        next_button=findViewById(R.id.next_button);

        imageView_coach.setImageResource(R.drawable.checked);
        imageView_trainee.setImageResource(R.drawable.notcheck);

        PreferencesUtils.setUserType("coach");
    }

}