package com.qtechnetworks.ptplatform.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Fragment.ShopFragment;

public class NotAuthorizedActivity extends AppCompatActivity {
    Button subscribeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_authorized);
        initial();
        clicks();

    }
    private void clicks() {
        subscribeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotAuthorizedActivity.this,MainActivity.class).putExtra("page","shop"));
                finish();
            }
        });
    }

    private void initial() {

        subscribeBtn = findViewById(R.id.subscribe_btn);




    }
}