package com.testdemo.sample;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;


public class sample_Activity extends AppCompatActivity {

    private WebView webtestdemo;
    LinearLayout layout_testdemo;
    Button buttontestdemo_retry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        initView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void initView() {
        webtestdemo = findViewById(R.id.view_sample_web);
        layout_testdemo = findViewById(R.id.layoutconnetion_testdemo);

        CookieManager.getInstance().setAcceptCookie(true);
        webtestdemo.getSettings().setJavaScriptEnabled(true);
        webtestdemo.getSettings().setUseWideViewPort(true);
        webtestdemo.getSettings().setLoadWithOverviewMode(true);
        webtestdemo.getSettings().setDomStorageEnabled(true);
        webtestdemo.getSettings().setPluginState(WebSettings.PluginState.ON);
        webtestdemo.setWebChromeClient(new WebChromeClient());
        webtestdemo.setVisibility(View.VISIBLE);

        webtestdemo.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request,
                                        WebResourceError error) {
                super.onReceivedError(view, request, error);
                String testdemo_url = request.getUrl().toString();
                if (!testdemo_url.startsWith("http")) {
                    startActivity(new Intent(sample_Activity.this, WebsampleActivity.class));
                    try {
                        Intent testdemointent = new Intent(Intent.ACTION_VIEW);
                        testdemointent.setData(Uri.parse(testdemo_url));
                        startActivity(testdemointent);
                        finish();
                        return;
                    } catch (Exception e) {
                        finish();
                        return;
                    }
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });

        showtestdemoView();
    }

    public void validatingtestdemoInternetConnection() {
        layout_testdemo.setVisibility(View.VISIBLE);
        buttontestdemo_retry = findViewById(R.id.testdemo_btn);
        buttontestdemo_retry.setOnClickListener(view -> {
            layout_testdemo.setVisibility(View.GONE);
            showtestdemoView();
        });
    }

    protected void showtestdemoView() {
        if (sample_Utils.isNetworkConnected(this)) {
            webtestdemo.loadUrl(sample_Utils.generatesamplePremiumLink(sample_Activity.this));
        } else {
            validatingtestdemoInternetConnection();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        webtestdemo.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        webtestdemo.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        webtestdemo.loadUrl("about:blank");
    }

    @Override
    public void onBackPressed() {
    }

}
