package com.example.fakecalling.application

import android.app.Application
import android.content.Context
import com.akexorcist.localizationactivity.ui.LocalizationApplication
import com.example.fakecalling.Admanager.AppNativeAds
import com.example.fakecalling.Admanager.AppOpenManager
import com.example.fakecalling.utils.Prefs
import com.facebook.ads.AudienceNetworkAds
import java.util.Locale

class MyApplication:LocalizationApplication() {
     private lateinit var appOpenManager: AppOpenManager
    companion object {
        var prefs: Prefs? = null
    }
    override fun getDefaultLanguage(context: Context): Locale = Locale.ENGLISH
    override fun onCreate() {
        super.onCreate()
        prefs= Prefs(this)
        AudienceNetworkAds.initialize(this)
        this.appOpenManager =AppOpenManager(this)
           AppNativeAds.initializeAdMobNativeAd(this)

    }
}