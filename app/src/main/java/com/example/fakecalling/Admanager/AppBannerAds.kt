package com.example.fakecalling.Admanager

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdViewAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxAdView
import com.google.android.gms.ads.*
import com.example.fakecalling.R

class AppBannerAds {

    companion object {
        var adRequests = 0
        var isAdmobAdFailedtoLoad=false
        var appBanner = "99c80cdf85ed4a3c"

        fun loadMaxBanner(context: Context, bannerContainer: FrameLayout) {
            val maxAdView = MaxAdView(appBanner, context)

            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = 130
            maxAdView.layoutParams = FrameLayout.LayoutParams(width, height)

            maxAdView.setListener(object : MaxAdViewAdListener {
                override fun onAdLoaded(ad: MaxAd?) {
                    bannerContainer.removeAllViews()
                    bannerContainer.addView(maxAdView)
                    Log.d("BannerAD", "onAdLoaded:$ad ")

                    maxAdView.setBackgroundColor(
                        ContextCompat.getColor(context,
                        R.color.white))
                    bannerContainer.visibility = View.VISIBLE
                    Log.e("BANNER_AD", "loaded: ")
                }

                override fun onAdDisplayed(ad: MaxAd?) {
                    Log.d("BannerAD", "onAdDisplayed:$ad ")
                }

                override fun onAdHidden(ad: MaxAd?) { }

                override fun onAdClicked(ad: MaxAd?) { }

                override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
                    Log.d("BannerAD", "onAdLoadFailed:$error ")
                }

                override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) { }

                override fun onAdExpanded(ad: MaxAd?) { }

                override fun onAdCollapsed(ad: MaxAd?) { }
            })

            maxAdView.loadAd()
        }

        fun loadAMBanner(context: Context, bannerContainer: FrameLayout) {

            val adView =AdView(context)

            adView.setAdSize(AdSize.BANNER)

            adView.adUnitId = context.getString(R.string.AM_BANNER_AD_ID)

            val adRequest = AdRequest.Builder().build()

            bannerContainer.removeAllViews()
            bannerContainer.addView(adView)

            adView.adListener = object : AdListener() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    loadMaxBanner(context, bannerContainer)
                }

                override fun onAdLoaded() {
                    bannerContainer.visibility = View.VISIBLE
                    super.onAdLoaded()
                }
            }
            adView.loadAd(adRequest)
        }
    }
}