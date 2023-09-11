package com.testdemo.sample;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class WebsampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_web_sample);
        WebView testdemo_web = findViewById(R.id.web_sample_view);
        testdemo_web.setWebViewClient(new WebViewClient());
        testdemo_web.getSettings().setJavaScriptEnabled(true);
        testdemo_web.getSettings().setUseWideViewPort(true);
        testdemo_web.getSettings().setLoadWithOverviewMode(true);
        testdemo_web.getSettings().setDomStorageEnabled(true);
        testdemo_web.getSettings().setPluginState(WebSettings.PluginState.ON);
        testdemo_web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        testdemo_web.loadUrl("https://www.gogole.com");
    }
}