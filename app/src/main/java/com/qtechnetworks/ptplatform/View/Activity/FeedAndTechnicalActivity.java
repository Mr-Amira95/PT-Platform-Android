package com.qtechnetworks.ptplatform.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.qtechnetworks.ptplatform.R;

public class FeedAndTechnicalActivity extends AppCompatActivity {

    TextView title_textview;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_and_technical);

        bundle = getIntent().getExtras();


        initial();

    }

    private void initial(){

        title_textview=findViewById(R.id.title_textview);

        title_textview.setText(bundle.getString("title"));


    }

}