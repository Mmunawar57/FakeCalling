package com.example.fakecalling.Admanager

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd
import com.bumptech.glide.Glide
import com.example.fakecalling.R
import com.example.fakecalling.databinding.AdLoadinDialoagBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Timer
import kotlin.concurrent.schedule

class AppInterstitialAds {

    companion object {
        private var dialog: Dialog? = null
        var maxInterAd: MaxInterstitialAd? = null
        var adRequest = 0
        var isInterstitialAdsShowing=false
        var testID= "ca-app-pub-3940256099942544/1033173"
        var appLovingInter = "6923accf33a93270"

        var amInterAD: InterstitialAd? = null

        fun loadAmInterstitial(context: Context) {
            if (amInterAD != null)
                amInterAD = null

            val adRequest = AdRequest.Builder().build()

            InterstitialAd.load(
                context,
                context.getString(R.string.AM_INTER_AD_ID),
                adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        Log.d("Interstitial_ADS", adError.message)
                        amInterAD = null
                        Log.d("Interstitial_ADS", "onAdFailedToLoad:$ ")
                    }
                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        Log.d("Interstitial_ADS", "Ad Loaded")
                        amInterAD = interstitialAd
                    }
                })
        }

        fun loadMaxInterAd(context: Context) {
            maxInterAd = null

            maxInterAd = MaxInterstitialAd(appLovingInter, context as Activity)

            maxInterAd!!.loadAd()

        }

        fun showInterAd(context: Context, adCloseCallback: (Boolean) -> Unit) {
            val ctx = context as Activity
            if (amInterAD != null) {

                if (!ctx.isDestroyed && !ctx.isFinishing) {
                    showDialog(ctx)
                    amInterAD!!.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent()
                            adCloseCallback.invoke(true)
                            loadAmInterstitial(context)
                            isInterstitialAdsShowing =false

                        }

                        override fun onAdShowedFullScreenContent() {
                            super.onAdShowedFullScreenContent()
                            isInterstitialAdsShowing =true
                        }

                        override fun onAdImpression() {
                            super.onAdImpression()

                        }
                    }

                    CoroutineScope(Dispatchers.Default).launch {
                        delay(1000)
                        withContext(Dispatchers.Main) {
                            hideDialog(context)
                            if (amInterAD != null) {
                                amInterAD!!.show(context as Activity)
                            }
                        }
                    }
                }
            } else if (maxInterAd != null && maxInterAd!!.isReady) {

                if (!ctx.isDestroyed && !ctx.isFinishing) {
                    showDialog(ctx)

                    maxInterAd!!.setListener(object : MaxAdListener {
                        override fun onAdLoaded(ad: MaxAd?) { }

                        override fun onAdDisplayed(ad: MaxAd?) {
                            isInterstitialAdsShowing =true
                            Log.d("Interstitial_ADS", "onAdDisplayed: $")
                        }

                        override fun onAdHidden(ad: MaxAd?) {
                            adCloseCallback.invoke(true)
                            loadMaxInterAd(context)
                            isInterstitialAdsShowing =false
                        }

                        override fun onAdClicked(ad: MaxAd?) { }

                        override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) { }

                        override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) { }
                    })

                    Timer("ad").schedule(1000) {
                        (context as Activity).runOnUiThread {
                            val ctx = context as Activity
                            if (!ctx.isDestroyed && !ctx.isFinishing) {
                                hideDialog(context)
                                if (maxInterAd != null) {
                                    maxInterAd!!.showAd()
                                }
                            }
                        }
                    }
                }
            } else {
                loadMaxInterAd(context)
                loadAmInterstitial(context)
                adCloseCallback.invoke(false)
            }
        }

        private fun showDialog(context: Context) {
            val ctx = context as Activity
            if (!ctx.isDestroyed && !ctx.isFinishing) {
                val binding = AdLoadinDialoagBinding.inflate(LayoutInflater.from(context))
                dialog = Dialog(context)
                dialog!!.setContentView(binding.root)
                dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                Glide.with(context as Context).load(R.drawable.loading).into(binding.loadingImage)
                dialog!!.setCancelable(false)
                dialog!!.show()
            }
        }

        private fun hideDialog(context: Context) {
            val ctx = context as Activity
            if (!ctx.isDestroyed && !ctx.isFinishing) {
                if (dialog != null) {
                    dialog!!.dismiss()
                }
            }
        }
    }
}