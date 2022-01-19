package com.qtechnetworks.ptplatform.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.TraineeSignIn.SignInTraineeActivity;

public class ChoosingActivity extends AppCompatActivity {

    Button next_button;

    ImageView imageView_coach,imageView_trainee;

    Boolean trainee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosing);

        initial();

    }

    private void initial(){

        imageView_coach=findViewById(R.id.imageView_coach);
        imageView_trainee=findViewById(R.id.imageView_trainee);
        next_button=findViewById(R.id.next_button);

        imageView_coach.setImageResource(R.drawable.notcheck);
        imageView_trainee.setImageResource(R.drawable.notcheck);


        imageView_coach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                imageView_coach.setImageResource(R.drawable.checked);
                imageView_trainee.setImageResource(R.drawable.notcheck);
                trainee=false;


            }
        });

        imageView_trainee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageView_trainee.setImageResource(R.drawable.checked);
                imageView_coach.setImageResource(R.drawable.notcheck);
                trainee=true;

            }
        });


        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!trainee){

                    startActivity(new Intent(ChoosingActivity.this, SignInTraineeActivity.class).putExtra("button","true"));

                }else if (trainee){

                    startActivity(new Intent(ChoosingActivity.this, SignInTraineeActivity.class).putExtra("button","1"));

                }else {

                    Toast.makeText(getApplicationContext(),"Please Choose ",Toast.LENGTH_LONG).show();

                }

            }
        });


    }

}