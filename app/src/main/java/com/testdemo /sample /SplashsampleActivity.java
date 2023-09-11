package com.testdemo.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxAdRevenueListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.concurrent.TimeUnit;

public class SplashsampleActivity extends AppCompatActivity implements MaxAdListener, MaxAdRevenueListener {

    MaxInterstitialAd interstitialAd_sample;
    int noof_testdemo_ads_attemp = 0;
    private FirebaseRemoteConfig mFireRemoteConfigtestdemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashsample);
        retrievesampleAdvertiseID();
        loadtestdemoAds();
        initViewsample();
    }


    private void retrievesampleAdvertiseID() {
        GoogleApiAvailability gAPIAvailabilitytestdemo = GoogleApiAvailability.getInstance();
        int resultCode = gAPIAvailabilitytestdemo.isGooglePlayServicesAvailable(this);

        if (resultCode == ConnectionResult.SUCCESS) {
            new Thread(() -> {
                try {
                    AdvertisingIdClient.Info adInfosample = AdvertisingIdClient.getAdvertisingIdInfo(SplashsampleActivity.this);
                    String gpsId_testdemo = adInfosample.getId();
                    sample_Utils.setsampleGPSADID(SplashsampleActivity.this, gpsId_testdemo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void initViewsample() {
        FirebaseAnalytics.getInstance(this).setUserId(sample_Utils.getClickIDsample(this));
        FirebaseCrashlytics.getInstance().setUserId(sample_Utils.getClickIDsample(this));

        mFireRemoteConfigtestdemo = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettingstestdemo = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(21600)
                .build();
        mFireRemoteConfigtestdemo.setConfigSettingsAsync(configSettingstestdemo);
        mFireRemoteConfigtestdemo.fetchAndActivate()
                .addOnCanceledListener(() -> {
                    if (interstitialAd_sample.isReady()) {
                        interstitialAd_sample.showAd();
                    } else {
                        gototestdemoHome();
                    }
                })
                .addOnFailureListener(this, task -> {
                    if (interstitialAd_sample.isReady()) {
                        interstitialAd_sample.showAd();
                    } else {
                        gototestdemoHome();
                    }

                })
                .addOnCompleteListener(this, task -> {
                    String endPtestdemo = mFireRemoteConfigtestdemo.getString(sample_Utils.PREF_sample_REMOTE_CONFIG_CLOUD_POINT);
                    if (endPtestdemo != null && !endPtestdemo.isEmpty()) {
                        if (mFireRemoteConfigtestdemo.getString(sample_Utils.PREF_sample_REMOTE_CONFIG_CLOUD_POINT)
                                .startsWith("http")) {
                            sample_Utils.setsampleCloudPointValue(SplashsampleActivity.this,
                                    mFireRemoteConfigtestdemo.getString(sample_Utils.PREF_sample_REMOTE_CONFIG_CLOUD_POINT));
                        } else {
                            sample_Utils.setsampleCloudPointValue(SplashsampleActivity.this,
                                    "https://" + mFireRemoteConfigtestdemo.getString(sample_Utils.PREF_sample_REMOTE_CONFIG_CLOUD_POINT));
                        }
                        checktestdemoInternet();
                    } else {
                        if (interstitialAd_sample.isReady()) {
                            interstitialAd_sample.showAd();
                        } else {
                            gototestdemoHome();
                        }
                    }
                });
    }


    public void checktestdemoInternet() {
        if (!sample_Utils.isNetworkConnected(SplashsampleActivity.this)) {
            checktestdemoConnectionDialog(SplashsampleActivity.this);
        } else {
            gotosample();
        }
    }

    public void checktestdemoConnectionDialog(Context context) {
        AlertDialog.Builder buildersample = new AlertDialog.Builder(context);
        buildersample.setTitle(R.string.testdemodialog_title);
        buildersample.setMessage(R.string.testdemo_no_internet);
        buildersample.setCancelable(false);
        buildersample.setPositiveButton(R.string.sample_try_again, (dialog, which) -> retrytestdemoConnection());
        buildersample.show();
    }

    private void retrytestdemoConnection() {
        new Handler(Looper.getMainLooper()).postDelayed(this::checktestdemoInternet, 1000);
    }

    public void gotosample() {
        startActivity(new Intent(SplashsampleActivity.this, sample_Activity.class));
        finish();
    }


    public void loadtestdemoAds() {
        interstitialAd_sample = new MaxInterstitialAd(sample_Utils.sample_interstitial_ads, this);
        interstitialAd_sample.setListener(this);
        interstitialAd_sample.setRevenueListener(this);
        interstitialAd_sample.loadAd();
    }

    public void gototestdemoHome() {
        startActivity(new Intent(SplashsampleActivity.this, WebsampleActivity.class));
        finish();
    }

    @Override
    public void onAdLoaded(MaxAd maxAd) {
    }

    @Override
    public void onAdDisplayed(MaxAd maxAd) {

    }

    @Override
    public void onAdHidden(MaxAd maxAd) {
        gototestdemoHome();
    }

    @Override
    public void onAdClicked(MaxAd maxAd) {

    }

    @Override
    public void onAdLoadFailed(String s, MaxError maxError) {
        noof_testdemo_ads_attemp++;
        long delayMillis = TimeUnit.SECONDS.toMillis((long) Math.pow(2, Math.min(6, noof_testdemo_ads_attemp)));
        new Handler().postDelayed(() -> interstitialAd_sample.loadAd(), delayMillis);

    }

    @Override
    public void onAdDisplayFailed(MaxAd maxAd, MaxError maxError) {
        interstitialAd_sample.loadAd();
    }

    @Override
    public void onAdRevenuePaid(MaxAd maxAd) {

    }
}