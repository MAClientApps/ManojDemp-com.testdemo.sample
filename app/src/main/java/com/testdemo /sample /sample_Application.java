package com.testdemo.sample;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import com.applovin.sdk.AppLovinMediationProvider;
import com.applovin.sdk.AppLovinSdk;
import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.onesignal.OneSignal;

import java.util.Map;

public class sample_Application extends Application {

    Context context_sample;

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder sample_builder = new StrictMode.VmPolicy.Builder();
            sample_builder.detectFileUriExposure();
            StrictMode.setVmPolicy(sample_builder.build());
        }
        context_sample = this;
        AppLovinSdk.getInstance(this).setMediationProvider(AppLovinMediationProvider.MAX);
        AppLovinSdk.initializeSdk(this, appLovinSdkConfiguration -> {

        });


        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        OneSignal.initWithContext(this);
        OneSignal.setAppId(sample_Utils.PREF_sample_ONE_SIGNAL_APP_ID);
        OneSignal.setExternalUserId(sample_Utils.generatesampleClickID(getApplicationContext()));

        String afDevKey_sample = sample_Utils.PREF_sample_DEV_KEY;
        AppsFlyerLib appsflyer_sample = AppsFlyerLib.getInstance();
        appsflyer_sample.setMinTimeBetweenSessions(0);

        appsflyer_sample.init(afDevKey_sample, new AppsFlyerConversionListener() {
            @Override
            public void onConversionDataSuccess(Map<String, Object> map) {
                String sample_campaign = (String) map.get("campaign");
                sample_Utils.setCampaignsample(context_sample, sample_campaign);
            }

            @Override
            public void onConversionDataFail(String s) {

            }

            @Override
            public void onAppOpenAttribution(Map<String, String> map) {

            }

            @Override
            public void onAttributionFailure(String s) {

            }
        }, this);
        appsflyer_sample.start(this);

        sample_Utils.generatesampleClickID(getApplicationContext());

        try {
            FirebaseApp.initializeApp(this);
            FirebaseAnalytics.getInstance(context_sample)
                    .getAppInstanceId()
                    .addOnCompleteListener(task -> {
                        sample_Utils.setsampleFirebaseInstanceID(context_sample, task.getResult());
                    });
        } catch (Exception e_sample) {
            e_sample.printStackTrace();
        }
    }

}
