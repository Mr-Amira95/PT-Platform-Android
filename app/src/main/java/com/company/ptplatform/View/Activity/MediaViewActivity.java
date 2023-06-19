package com.company.ptplatform.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.company.ptplatform.R;

public class MediaViewActivity extends AppCompatActivity {
    WebView mediaWebview;

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_view);
        initals();

        url= getIntent().getExtras().getString("url");
        mediaWebview.getSettings().setLoadsImagesAutomatically(true);
        mediaWebview.getSettings().setJavaScriptEnabled(true);
        mediaWebview.getSettings().setSupportZoom(true);
        mediaWebview.getSettings().setBuiltInZoomControls(true);
        mediaWebview.getSettings().setDisplayZoomControls(false);
        mediaWebview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mediaWebview.loadUrl(url);

    }

    private void initals(){
          mediaWebview=findViewById(R.id.media_webview);
    }
}