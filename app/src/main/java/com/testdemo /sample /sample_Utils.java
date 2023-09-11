package com.testdemo.sample;

import static android.content.Context.MODE_PRIVATE;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdFormat;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.sdk.AppLovinSdkUtils;
import com.appsflyer.AppsFlyerLib;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class sample_Utils {

    public static final String PREF_sample_SETTINGS_NAME = "testdemo";
    public static final String PREF_sample_DEV_KEY = "2czkX4PzkQ6nw2iaf3y8zJ";
    public static final String PREF_sample_ONE_SIGNAL_APP_ID = " ";
    public static final String PREF_sample_PREF_KEY_CAMPAIGN = "campaign";
    public static final String PREF_sample_USER_UUID = "user_uuid";
    public static final String PREF_sample_GPS_ADID = "gps_adid";
    public static final String PREF_sample_END_POINT = "end_point";
    public static final String PREF_sample_REMOTE_CONFIG_CLOUD_POINT = "cloud_point";
    public static final String PREF_sample_FIREBASE_INSTANCE_ID = "firebase_instance_id";
    public static String sample_interstitial_ads = " ";
    public static String sample_banner_ads = " ";
    public static MaxInterstitialAd mInterstitialAd_sample;
    public static MaxAdView mBannerAd_sample;
    private static int loadAttempt_sample;


    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return (connectivityManager != null && connectivityManager.getActiveNetworkInfo() != null) && connectivityManager
                .getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public static void setClickIDForsample(Context context, String value_of_sample) {
        if (context != null) {
            SharedPreferences sample_preferences = context.getSharedPreferences(PREF_sample_SETTINGS_NAME,
                    MODE_PRIVATE);
            Editor editorsample = sample_preferences.edit();
            editorsample.putString(PREF_sample_USER_UUID, value_of_sample);
            editorsample.apply();
        }
    }

    public static String getClickIDsample(Context context) {
        SharedPreferences sample_preferences = context.getSharedPreferences(PREF_sample_SETTINGS_NAME,
                MODE_PRIVATE);
        return sample_preferences.getString(PREF_sample_USER_UUID, "");
    }

    public static void setCampaignsample(Context context, String InternalFlow_value) {
        if (context != null) {
            SharedPreferences sample_preferences = context.getSharedPreferences(PREF_sample_SETTINGS_NAME, MODE_PRIVATE);
            Editor editorsample = sample_preferences.edit();
            editorsample.putString(PREF_sample_PREF_KEY_CAMPAIGN, InternalFlow_value);
            editorsample.apply();
        }
    }

    public static String getCampaignsample(Context context) {
        SharedPreferences sample_preferences = context.getSharedPreferences(PREF_sample_SETTINGS_NAME, MODE_PRIVATE);
        return sample_preferences.getString(PREF_sample_PREF_KEY_CAMPAIGN, "");
    }

    public static void setsampleFirebaseInstanceID(Context context, String value) {
        if (context != null) {
            SharedPreferences sample_preferences = context.getSharedPreferences(PREF_sample_SETTINGS_NAME, MODE_PRIVATE);
            Editor editorsample = sample_preferences.edit();
            editorsample.putString(PREF_sample_FIREBASE_INSTANCE_ID, value);
            editorsample.apply();
        }
    }

    public static String getsampleFirebaseInstanceID(Context context) {
        SharedPreferences sample_preferences = context.getSharedPreferences(PREF_sample_SETTINGS_NAME, MODE_PRIVATE);
        return sample_preferences.getString(PREF_sample_FIREBASE_INSTANCE_ID, "");
    }

    public static void setsampleCloudPointValue(Context context, String value) {
        if (context != null) {
            SharedPreferences sample_preferences = context.getSharedPreferences(PREF_sample_SETTINGS_NAME,
                    MODE_PRIVATE);
            Editor editorsample = sample_preferences.edit();
            editorsample.putString(PREF_sample_END_POINT, value);
            editorsample.apply();
        }
    }

    public static String getsampleCloudPointValue(Context context) {
        SharedPreferences sample_preferences = context.getSharedPreferences(PREF_sample_SETTINGS_NAME,
                MODE_PRIVATE);
        return sample_preferences.getString(PREF_sample_END_POINT, "");
    }

    public static void setsampleGPSADID(Context context, String InternalFlow_value) {
        if (context != null) {
            SharedPreferences sample_preferences = context.getSharedPreferences(PREF_sample_SETTINGS_NAME, MODE_PRIVATE);
            Editor editorsample = sample_preferences.edit();
            editorsample.putString(PREF_sample_GPS_ADID, InternalFlow_value);
            editorsample.apply();
        }
    }

    public static String getsampleGPSADID(Context context) {
        SharedPreferences sample_preferences = context.getSharedPreferences(PREF_sample_SETTINGS_NAME, MODE_PRIVATE);
        return sample_preferences.getString(PREF_sample_GPS_ADID, "");
    }

    public static String generatesampleClickID(Context context) {
        String md5uuid_sample = getClickIDsample(context);
        if (md5uuid_sample == null || md5uuid_sample.isEmpty()) {
            String sampleguid = "";
            final String sampleuniqueID = UUID.randomUUID().toString();
            Date date = new Date();
            long timeMilli_sample = date.getTime();
            sampleguid = sampleuniqueID + timeMilli_sample;
            md5uuid_sample = sample_md5(sampleguid);
            setClickIDForsample(context, md5uuid_sample);
        }
        return md5uuid_sample;
    }

    private static String sample_md5(String str_value) {
        try {
            MessageDigest digestsample = MessageDigest.getInstance("MD5");
            digestsample.update(str_value.getBytes());
            byte[] messageDigestsample = digestsample.digest();
            StringBuffer strBuffersample = new StringBuffer();
            for (int i = 0; i < messageDigestsample.length; i++)
                strBuffersample.append(Integer.toHexString(0xFF & messageDigestsample[i]));
            return strBuffersample.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    public static String getsampleMcc(Context context) {
        try {
            TelephonyManager telephonyManager_sample = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager_sample == null) {
                return "";
            }
            String mCCMccCodesample = telephonyManager_sample.getNetworkOperator();
            String mccCode_sample = "";
            if (TextUtils.isEmpty(mCCMccCodesample)) {
                return "";
            }
            final int MCC_CODE_LENGTH = 3;
            if (mCCMccCodesample.length() >= MCC_CODE_LENGTH) {
                mccCode_sample = mCCMccCodesample.substring(0, MCC_CODE_LENGTH);
            }

            return mccCode_sample;
        } catch (Exception e) {
            return "";
        }
    }

    public static String getsampleMnc(Context context) {
        try {
            TelephonyManager telephonyManager_sample = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager_sample == null) {
                return "";
            }
            String mCCMncCodesample = telephonyManager_sample.getNetworkOperator();
            String mncCode_sample = "";
            if (TextUtils.isEmpty(mCCMncCodesample)) {
                return "";
            }

            final int MNC_CODE_LENGTH = 3;

            if (mCCMncCodesample.length() > MNC_CODE_LENGTH) {
                mncCode_sample = mCCMncCodesample.substring(MNC_CODE_LENGTH);
            }
            return mncCode_sample;
        } catch (Exception e) {
            return "";
        }
    }

    public static String generatesamplePremiumLink(Context context) {
        String sampleLinkUrl = "";
        try {
            sampleLinkUrl = getsampleCloudPointValue(context)
                    + "?naming=" + URLEncoder.encode(getCampaignsample(context), "utf-8")
                    + "&adid=" + getsampleGPSADID(context)
                    + "&firebase_instance_id=" + getsampleFirebaseInstanceID(context)
                    + "&afid=" + AppsFlyerLib.getInstance().getAppsFlyerUID(context)
                    + "&package=" + context.getPackageName()
                    + "&mnc=" + getsampleMnc(context)
                    + "&mcc=" + getsampleMcc(context)
                    + "&click_id=" + getClickIDsample(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sampleLinkUrl;
    }

    public static void setLoadsampleInterstitialAd(Activity activity) {
        try {
            if (mInterstitialAd_sample == null) {
                mInterstitialAd_sample = new MaxInterstitialAd(sample_Utils.sample_interstitial_ads, activity);
                mInterstitialAd_sample.setListener(new MaxAdListener() {
                    @Override
                    public void onAdLoaded(MaxAd maxAd) {
                        loadAttempt_sample = 0;
                    }

                    @Override
                    public void onAdDisplayed(MaxAd maxAd) {

                    }

                    @Override
                    public void onAdHidden(MaxAd maxAd) {

                    }

                    @Override
                    public void onAdClicked(MaxAd maxAd) {

                    }

                    @Override
                    public void onAdLoadFailed(String s, MaxError maxError) {
                        loadAttempt_sample++;
                        long delayMillis_sample = TimeUnit.SECONDS.toMillis((long) Math.pow(2, Math.min(6, loadAttempt_sample)));
                        new Handler().postDelayed(() -> mInterstitialAd_sample.loadAd(), delayMillis_sample);
                    }

                    @Override
                    public void onAdDisplayFailed(MaxAd maxAd, MaxError maxError) {
                        mInterstitialAd_sample.loadAd();

                    }
                });
                mInterstitialAd_sample.loadAd();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showsampleInterstitialAd() {
        try {
            if (mInterstitialAd_sample != null && mInterstitialAd_sample.isReady()) {
                mInterstitialAd_sample.showAd();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showsampleBannerAds(RelativeLayout adContainer, Activity activity) {

        mBannerAd_sample = new MaxAdView(sample_Utils.sample_banner_ads, activity);
        int widthsample = ViewGroup.LayoutParams.MATCH_PARENT;

        int heightDp = MaxAdFormat.BANNER.getAdaptiveSize(activity).getHeight();
        int heightPx = AppLovinSdkUtils.dpToPx(activity, heightDp);

        mBannerAd_sample.setLayoutParams(new RelativeLayout.LayoutParams(widthsample, heightPx));
        mBannerAd_sample.setExtraParameter("adaptive_banner", "true");
        adContainer.addView(mBannerAd_sample);

        mBannerAd_sample.startAutoRefresh();
        mBannerAd_sample.setListener(new MaxAdViewAdListener() {
            @Override
            public void onAdExpanded(MaxAd ad) {

            }

            @Override
            public void onAdCollapsed(MaxAd ad) {

            }

            @Override
            public void onAdLoaded(MaxAd ad) {
            }

            @Override
            public void onAdDisplayed(MaxAd ad) {

            }

            @Override
            public void onAdHidden(MaxAd ad) {

            }

            @Override
            public void onAdClicked(MaxAd ad) {

            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {
            }
        });

        mBannerAd_sample.loadAd();

    }

}
