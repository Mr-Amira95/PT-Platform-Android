package com.qtechnetworks.ptplatform.Model.basic;



import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class BasicActivity extends AppCompatActivity {
    public BasicActivity activity;
    public ProgressDialog dialog;
    /*private FirebaseAnalytics mFirebaseAnalytics;*/

    public String firBaseID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = this;
        dialog = ProgressDialog.show(activity, "",
                "Loading. Please wait...", true);
        dialog.setCancelable(true);
        dialog.dismiss();
  /*      mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

//        firBaseID = mFirebaseAnalytics.getFirebaseInstanceId();
        firBaseID = getUserToken();*/
//        Fabric.with(this, new Crashlytics());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
