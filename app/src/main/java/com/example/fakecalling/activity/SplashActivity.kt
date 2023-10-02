package com.example.fakecalling.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.applovin.sdk.AppLovinSdk
import com.bumptech.glide.Glide
import com.example.fakecalling.Admanager.AppBannerAds
import com.example.fakecalling.Admanager.AppInterstitialAds
import com.example.fakecalling.Admanager.AppNativeAds
import com.example.fakecalling.R
import com.example.fakecalling.application.MyApplication
import com.example.fakecalling.databinding.ActivitySplashBinding
import com.facebook.ads.AudienceNetworkAds
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : LocalizationActivity() {
    private val binding by lazy {
       ActivitySplashBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(binding.root)

        initializeAdNetwork()
        Glide.with(this)
            .load(R.drawable.loading)
            .into(binding.loadingbar)

            Handler(Looper.getMainLooper()).postDelayed({
                if(!MyApplication.prefs!!.getBoolean("IS_OPENED", false)) {
                    startActivity(
                        Intent(
                            this@SplashActivity,
                            LanguageActivity::class.java
                        )
                    )
                    finish()
                } else {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }
            },6000)
        }
    private fun initializeAdNetwork() {
        AppLovinSdk.getInstance(this).mediationProvider = "max"
        AppLovinSdk.initializeSdk(this) {
            AppInterstitialAds.loadMaxInterAd(this@SplashActivity)
            AppInterstitialAds.loadAmInterstitial(this)
            AppNativeAds.loadMaxNativeAd(this@SplashActivity)
            AppNativeAds.loadAmNativeAd(this){}
            AppNativeAds.initializeAdMobNativeAd(this)

            AppBannerAds.adRequests
        }
    }
}