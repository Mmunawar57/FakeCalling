package com.example.fakecalling.Admanager

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.fakecalling.R
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxError
import com.applovin.mediation.nativeAds.MaxNativeAdListener
import com.applovin.mediation.nativeAds.MaxNativeAdLoader
import com.applovin.mediation.nativeAds.MaxNativeAdView
import com.applovin.mediation.nativeAds.MaxNativeAdViewBinder
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppNativeAds {

    companion object {
        var maxNativeAd: MaxAd? = null
        var maxNativeAdLoader: MaxNativeAdLoader? = null
        var amNativeAd: NativeAd? = null
        val testid = "ca-app-pub-3940256099942544/2247696210"
        val appLovingNative = "e40c76bea1413446"
        val applovinWrongId = "e40c76bea1413449"

        var nativeRequesCounter = 0

        fun initializeAdMobNativeAd(context: Context) {
            MobileAds.initialize(context) {}
        }

        //splashAds
        private fun loadAmNativeAdSplash(context: Context, frameLayout: FrameLayout) {
            nativeRequesCounter++

            val adID = context.getString(R.string.AM_NATIVE_AD_ID)
            val builder =
                AdLoader.Builder(context, adID)
                    .forNativeAd { nativeAd ->
                        Log.d("AM_NATIVE", "loadAmNativeAdSplash:$amNativeAd ")
                        inflateAdmobNativeAdBig(context, frameLayout, nativeAd)
                    }.withAdListener(object : AdListener() {
                        override fun onAdClosed() {
                            super.onAdClosed()
                        }

                        override fun onAdFailedToLoad(p0: LoadAdError) {
                            super.onAdFailedToLoad(p0)
                            loadMaxNativeAdSplash(context, frameLayout)
                            Log.e("AM_NATIVE_Splash", "onAdError:failed-->${p0.code} ")
                        }

                        override fun onAdImpression() {
                            super.onAdImpression()
                        }

                        override fun onAdLoaded() {
                            super.onAdLoaded()
                            Log.e("AM_NATIVE", "onAdLoaded:native_ad_loaded ")
                        }
                    }).build()

            builder.loadAd(AdRequest.Builder().build())
        }

        fun loadMaxNativeAdSplash(context: Context, parent: FrameLayout) {

            val maxNativeAdLoader = MaxNativeAdLoader(
                appLovingNative,
                context as Activity
            )
            maxNativeAdLoader.setNativeAdListener(object : MaxNativeAdListener() {
                override fun onNativeAdLoaded(p0: MaxNativeAdView?, p1: MaxAd?) {
                    super.onNativeAdLoaded(p0, p1)
                    val binder: MaxNativeAdViewBinder =
                        MaxNativeAdViewBinder.Builder(R.layout.layout_applovinnative_ad)
                            .setTitleTextViewId(R.id.txtAdTitle)
                            .setBodyTextViewId(R.id.txtAdBody)
                            .setAdvertiserTextViewId(R.id.ad_advertiser_view)
                            .setIconImageViewId(R.id.imgIcon)
                            .setMediaContentViewGroupId(R.id.AdMedia)
                            .setOptionsContentViewGroupId(R.id.AdOptions)
                            .setCallToActionButtonId(R.id.btnAdCta)
                            .build()
                    val maxAdView = MaxNativeAdView(binder, context)
                    if(maxNativeAdLoader.render(maxAdView, p1)) {
                        parent.visibility = View.VISIBLE
                        parent.removeAllViews()
                        parent.addView(maxAdView)
                        CoroutineScope(Dispatchers.Main).launch {
                            loadMaxNativeAd(context)
                        }
                    }
                }

                override fun onNativeAdLoadFailed(p0: String?, p1: MaxError?) {
                    super.onNativeAdLoadFailed(p0, p1)
                    Log.d("MAX_NATIVE", "onNativeAdLoaded:native failed_splash${p1?.code} ")
                }

                override fun onNativeAdClicked(p0: MaxAd?) {
                    super.onNativeAdClicked(p0)
                }
            })
            maxNativeAdLoader.loadAd()
        }

        fun inflateSplashAds(context: Context?, frameLayout: FrameLayout) {

            Log.d("SPLASH_ADS", "inflateSplashAds: 0->$context")
            loadAmNativeAdSplash(context!!, frameLayout)

            Log.d("SPLASH_ADS", "inflateSplashAds: 1->$context")
        }

        fun loadMaxNativeAd(context: Context) {
            if(maxNativeAd != null) {
                maxNativeAd = null
            }
            maxNativeAdLoader = MaxNativeAdLoader(
                appLovingNative,
                context as Activity
            )
            maxNativeAdLoader!!.setNativeAdListener(object : MaxNativeAdListener() {
                override fun onNativeAdLoaded(p0: MaxNativeAdView?, p1: MaxAd?) {
                    super.onNativeAdLoaded(p0, p1)
                    maxNativeAd = p1
                    Log.e("APPLOVIN_NATIVE", "ApplovinAds:Loaded:${p1.toString()} ")

                }

                override fun onNativeAdLoadFailed(p0: String?, p1: MaxError?) {
                    super.onNativeAdLoadFailed(p0, p1)
                    Log.d("APPLOVIN_NATIVE", "ApplovinAds:Failed:${p1?.code} ")
                }

                override fun onNativeAdClicked(p0: MaxAd?) {
                    super.onNativeAdClicked(p0)
                    Log.d("APPLOVIN_NATIVE", "ApplovinAds:Clicked:${p0.toString()} ")
                }
            })

            maxNativeAdLoader!!.loadAd()

        }

        fun loadAmNativeAd(context: Context, onAdAdLoadedCallback: (Boolean) -> Unit) {
            Log.d("ADMOB_NATIVE", "AdmobAd:Load->$onAdAdLoadedCallback ")
            nativeRequesCounter++
            //   context.getString(R.string.AM_NATIVE_AD_ID)
            val builder = AdLoader.Builder(context, context.getString(R.string.AM_NATIVE_AD_ID))
                .forNativeAd { nativeAd ->
                    amNativeAd = nativeAd
                    onAdAdLoadedCallback.invoke(true)
                }
                .withAdListener(object : AdListener() {
                    override fun onAdClosed() {
                        super.onAdClosed()
                    }

                    override fun onAdFailedToLoad(p0: LoadAdError) {
                        super.onAdFailedToLoad(p0)
                        onAdAdLoadedCallback.invoke(false)
                        Log.e("ADMOB_NATIVE", "AdmobAd:failed-->${p0.code} ")
                    }

                    override fun onAdImpression() {
                        super.onAdImpression()
                        loadAmNativeAd(context) {}
                    }

                    override fun onAdLoaded() {
                        super.onAdLoaded()
                        Log.e("ADMOB_NATIVE", "AdmobAd:loaded ")
                    }
                })
                .build()

            builder.loadAd(AdRequest.Builder().build())
        }

        private fun inflateAdmobNativeAdBig(
            context: Context?, frameLayout: FrameLayout,
            admobnative: NativeAd?,
        ) {
            if(admobnative == null)
                return
            frameLayout.visibility = View.VISIBLE
            var admobview: NativeAdView? = null

            admobview = LayoutInflater.from(context)
                .inflate(R.layout.layout_admobnatne_main, null) as NativeAdView?
            frameLayout.removeAllViews()
            frameLayout.addView(admobview)

            // Set other ad assets.
            if(admobview != null) {
                admobview.mediaView = admobview.findViewById(R.id.AdMedia)
            }
            if(admobview != null) {
                admobview.headlineView = admobview.findViewById(R.id.txtAdTitle)
            }
            if(admobview != null) {
                admobview.bodyView = admobview.findViewById(R.id.txtAdBody)
            }
            if(admobview != null) {
                admobview.callToActionView = admobview.findViewById(R.id.btnAdCta)
            }
            if(admobview != null) {
                admobview.iconView = admobview.findViewById(R.id.imgIcon)
            }

            // The headline and media content are guaranteed to be in every UnifiedNativeAd.
            if(admobview != null) {
                (admobview.headlineView as TextView).text = admobnative?.headline
            }
            admobnative?.mediaContent?.let { admobview?.mediaView?.setMediaContent(it) }

            if(admobnative?.body == null) {
                if(admobview != null) {
                    admobview.bodyView?.visibility = View.INVISIBLE
                }
            } else {
                if(admobview != null) {
                    admobview.bodyView?.visibility = View.VISIBLE
                }
                if(admobview != null) {
                    (admobview.bodyView as TextView).text = admobnative.body
                }
            }

            if(admobnative?.callToAction == null) {
                if(admobview != null) {
                    admobview.callToActionView?.visibility = View.INVISIBLE
                }
            } else {
                if(admobview != null) {
                    admobview.callToActionView?.visibility = View.VISIBLE
                }
                if(admobview != null) {
                    (admobview.callToActionView as TextView).text = admobnative.callToAction
                }
            }

            if(admobnative?.icon == null) {
                if(admobview != null) {
                    admobview.iconView?.visibility = View.GONE
                }
            } else {
                (admobview?.iconView as ImageView).setImageDrawable(
                    admobnative.icon?.drawable
                )
                admobview.iconView?.visibility = View.VISIBLE
            }

            if(admobnative != null) {

                admobview?.setNativeAd(admobnative)

            }
        }

        private fun inflateNativeMainAd(context: Context, parent: FrameLayout) {
            val binder: MaxNativeAdViewBinder =
                MaxNativeAdViewBinder.Builder(R.layout.layout_applovinnative_ad)
                    .setTitleTextViewId(R.id.txtAdTitle)
                    .setBodyTextViewId(R.id.txtAdBody)
                    .setAdvertiserTextViewId(R.id.ad_advertiser_view)
                    .setIconImageViewId(R.id.imgIcon)
                    .setMediaContentViewGroupId(R.id.AdMedia)
                    .setOptionsContentViewGroupId(R.id.AdOptions)
                    .setCallToActionButtonId(R.id.btnAdCta)
                    .build()

            val maxAdView = MaxNativeAdView(binder, context)
            if (maxNativeAdLoader != null && maxNativeAd != null) {
                if (maxNativeAdLoader!!.render(maxAdView, maxNativeAd)) {
                    parent.visibility = View.VISIBLE
                    parent.removeAllViews()
                    parent.addView(maxAdView)
                    CoroutineScope(Dispatchers.Main).launch {
                        loadMaxNativeAd(context)
                    }
                }
            } else {
                loadMaxNativeAd(context)
            }
        }
        fun inflateBigAds(context: Context, frameLayout: FrameLayout) {
            if (amNativeAd != null) {
                inflateAdmobNativeAdBig(context, frameLayout, amNativeAd)
                Log.d("ADMOB_NATIVE", "inflate_AdMob:: ")
            } else if (maxNativeAd != null) {
                inflateNativeMainAd(context, frameLayout)
                Log.d("APPLOVIN_NATIVE", "inflate_AppLovin::")
            } else {
                loadAmNativeAd(context) {}
            }
        }

        fun inflateRvAds(context: Context, frameLayout: FrameLayout) {
            if(amNativeAd != null) {
                inflateAdmobNativeAdRv(context, frameLayout, amNativeAd)
                Log.d("ADMOD_APPlovin", "inflateBigAds:0$amNativeAd ")
            } else if(maxNativeAd != null) {
                inflateNativeRvAd(context, frameLayout)
                Log.d("ADMOD_APPlovin", "inflateBigAds:1$context ")
            } else {
                loadAmNativeAd(context) {}
            }
        }

        private fun inflateAdmobNativeAdRv(
            context: Context?,
            frameLayout: FrameLayout,
            admobnative: NativeAd?,
        ) {
            if(admobnative == null)
                return
            frameLayout.visibility = View.VISIBLE
            var admobview: NativeAdView? = null

            admobview = LayoutInflater.from(context)
                .inflate(R.layout.layout_admobnative_rv, null) as NativeAdView?
            frameLayout.removeAllViews()
            frameLayout.addView(admobview)
            if(admobview != null) {
                admobview.headlineView = admobview.findViewById(R.id.txtAdTitle)
            }
            if(admobview != null) {
                admobview.bodyView = admobview.findViewById(R.id.txtAdBody)
            }
            if(admobview != null) {
                admobview.callToActionView = admobview.findViewById(R.id.btnAdCta)
            }
            if(admobview != null) {
                admobview.iconView = admobview.findViewById(R.id.imgIcon)
            }

            // The headline and media content are guaranteed to be in every UnifiedNativeAd.
            if(admobview != null) {
                (admobview.headlineView as TextView).text = admobnative?.headline
            }
            admobnative?.mediaContent?.let { admobview?.mediaView?.setMediaContent(it) }

            if(admobnative?.body == null) {
                if(admobview != null) {
                    admobview.bodyView?.visibility = View.INVISIBLE
                }
            } else {
                if(admobview != null) {
                    admobview.bodyView?.visibility = View.VISIBLE
                }
                if(admobview != null) {
                    (admobview.bodyView as TextView).text = admobnative.body
                }
            }

            if(admobnative?.callToAction == null) {
                if(admobview != null) {
                    admobview.callToActionView?.visibility = View.INVISIBLE
                }
            } else {
                if(admobview != null) {
                    admobview.callToActionView?.visibility = View.VISIBLE
                }
                if(admobview != null) {
                    (admobview.callToActionView as TextView).text = admobnative.callToAction
                }
            }

            if(admobnative?.icon == null) {
                if(admobview != null) {
                    admobview.iconView?.visibility = View.GONE
                }
            } else {
                (admobview?.iconView as ImageView).setImageDrawable(
                    admobnative.icon?.drawable
                )
                admobview.iconView?.visibility = View.VISIBLE
            }

            if(admobnative != null) {

                admobview?.setNativeAd(admobnative)

            }

        }

        fun inflateNativeRvAd(context: Context, parent: FrameLayout) {
            Log.d("Applovin_Ads", "inflateNativeRvAd: ")
            val binder: MaxNativeAdViewBinder =
                MaxNativeAdViewBinder.Builder(R.layout.max_ad_native_ad_small)
                    .setTitleTextViewId(R.id.txtAdTitle)
                    .setBodyTextViewId(R.id.txtAdBody)
                    .setAdvertiserTextViewId(R.id.ad_advertiser_view)
                    .setIconImageViewId(R.id.imgIcon)
                    .setOptionsContentViewGroupId(R.id.AdOptions)
                    .setCallToActionButtonId(R.id.btnAdCta)
                    .build()

            val maxAdView = MaxNativeAdView(binder, context)
            if(maxNativeAdLoader != null && maxNativeAd != null) {
                if(maxNativeAdLoader!!.render(maxAdView, maxNativeAd)) {
                    parent.visibility = View.VISIBLE
                    parent.removeAllViews()
                    parent.addView(maxAdView)
                    CoroutineScope(Dispatchers.Main).launch {
                        loadMaxNativeAd(context)
                    }
                }
            } else {
                loadMaxNativeAd(context)
            }
        }
    }
}