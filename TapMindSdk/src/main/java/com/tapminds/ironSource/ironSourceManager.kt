package com.tapminds.ironSource

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.tapminds.adapter.listener.TapMindAdapterError
import com.tapminds.adapter.listener.TapMindAdapterResponseParameters
import com.tapminds.ads.banner.TapMindAdViewAdapterListener
import com.tapminds.ads.interstitial.TapMindInterstitialAdapterListener
import com.tapminds.ads.reward.TapMindRewardedAdapterListener
import com.tapminds.network.ApiUtils
import com.tapminds.network.DataItem
import com.tapminds.tapmindsads.TapMindAdFormat
import com.unity3d.mediation.LevelPlayAdError
import com.unity3d.mediation.LevelPlayAdInfo
import com.unity3d.mediation.LevelPlayAdSize
import com.unity3d.mediation.banner.LevelPlayBannerAdView
import com.unity3d.mediation.banner.LevelPlayBannerAdViewListener
import com.unity3d.mediation.interstitial.LevelPlayInterstitialAd
import com.unity3d.mediation.interstitial.LevelPlayInterstitialAdListener
import com.unity3d.mediation.rewarded.LevelPlayReward
import com.unity3d.mediation.rewarded.LevelPlayRewardedAd
import com.unity3d.mediation.rewarded.LevelPlayRewardedAdListener
import java.util.concurrent.atomic.AtomicBoolean

class IronSourceManager {

    val TAG = "APP@@@"
    val TAG1 = "IronSourceManager"
    private val initialized = AtomicBoolean()
    private val ADAPTIVE_BANNER_TYPE_INLINE = "inline"
    private val apiUtils = ApiUtils()

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: IronSourceManager? = null

        fun getInstance(): IronSourceManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: IronSourceManager().also {
                    INSTANCE = it
                }
            }
        }
    }

    fun loadInterstitialAd(
        appId: String,
        placementId: String,
        parameters: TapMindAdapterResponseParameters,
        adapters: List<DataItem>,
        context: Context,
        listener: TapMindInterstitialAdapterListener
    ) {
        if (adapters.isEmpty()) {
            listener.onInterstitialAdLoadFailed(
                TapMindAdapterError(204, "No interstitial adapters available")
            )
            return
        }

        updateMuteState(parameters.getServerParameters())

        val sortedAdapters = adapters.sortedBy { it.priority }

        fun loadAt(index: Int) {
            if (index >= sortedAdapters.size) {
                listener.onInterstitialAdLoadFailed(
                    TapMindAdapterError(204, "No fill from all interstitial adapters")
                )
                return
            }

            val adapter = sortedAdapters[index]
            val adUnitId = adapter.adUnitId.toString()

            Log.d(
                "TapMindAdapterAdmob",
                "Interstitial Waterfall → priority=${adapter.priority}, partner=${adapter.partner}, adUnitId=$adUnitId"
            )
            val mInterstitialAd = LevelPlayInterstitialAd(adUnitId)
            mInterstitialAd.setListener(object : LevelPlayInterstitialAdListener {

                override fun onAdLoaded(levelPlayAdInfo: LevelPlayAdInfo) {

                    Log.d(TAG, "LevelPlay Interstitial : onAdLoaded")
                    Log.e(TAG, "isAdReady: ${mInterstitialAd.isAdReady}")
                    Log.e(TAG, "isAdReady: $levelPlayAdInfo")

                    Handler(Looper.getMainLooper()).postDelayed({
                        if (mInterstitialAd.isAdReady) {
                            Log.d(
                                TAG,
                                "Showing ad with activity: ${context::class.java.simpleName}"
                            )
                            if (context is Activity && !context.isFinishing && !context.isDestroyed) {
                                mInterstitialAd.showAd(context)
                            } else {
                                Log.e(TAG, "Activity is not valid")
                            }
                        } else {
                            Log.e(TAG, "Ad is not ready to show")
                        }
                    }, 100)
                    apiUtils.callImpressionRequestAPI(
                        appId,
                        adapter.adapterId.toString(),
                        placementId,
                        "Interstitial",
                        adapter.partner.toString(),
                        "SUCCESS"
                    )
                }

                override fun onAdLoadFailed(levelPlayAdError: LevelPlayAdError) {
                    Log.d(
                        TAG,
                        "LevelPlay Interstitial : onAdLoadFailed " + levelPlayAdError.errorMessage
                    )
                    Log.d(
                        TAG,
                        "LevelPlay Interstitial : onAdLoadFailed " + levelPlayAdError.errorCode
                    )
                    loadAt(index + 1)
                }

                override fun onAdDisplayed(levelPlayAdInfo: LevelPlayAdInfo) {

                }

                override fun onAdDisplayFailed(
                    error: LevelPlayAdError,
                    levelPlayAdInfo: LevelPlayAdInfo
                ) {

                }

                override fun onAdClicked(levelPlayAdInfo: LevelPlayAdInfo) {

                }

                override fun onAdClosed(levelPlayAdInfo: LevelPlayAdInfo) {

                }

                override fun onAdInfoChanged(levelPlayAdInfo: LevelPlayAdInfo) {

                }
            })

            mInterstitialAd.loadAd()
        }
        // 🚀 Start waterfall
        loadAt(0)
    }

    fun loadRewardedAd(
        appId: String,
        placementId: String,
        parameters: TapMindAdapterResponseParameters,
        adapters: List<DataItem>,
        context: Context,
        listener: TapMindRewardedAdapterListener
    ) {
        if (adapters.isEmpty()) {
            listener.onRewardedAdLoadFailed(
                TapMindAdapterError(204, "No rewarded adapters available")
            )
            return
        }
        updateMuteState(parameters.getServerParameters())
        val sortedAdapters = adapters.sortedBy { it.priority }

        fun loadAt(index: Int) {
            if (index >= sortedAdapters.size) {
                listener.onRewardedAdLoadFailed(
                    TapMindAdapterError(204, "No fill from all rewarded adapters")
                )
                return
            }

            val adapter = sortedAdapters[index]
            val adUnitId = adapter.adUnitId.toString()

            Log.d(
                TAG,
                "Rewarded Waterfall → priority=${adapter.priority}, partner=${adapter.partner}, adUnitId=$adUnitId"
            )
            val mRewardedAd = LevelPlayRewardedAd(adUnitId)
            mRewardedAd.setListener(object : LevelPlayRewardedAdListener {

                override fun onAdLoaded(p0: LevelPlayAdInfo) {
                    Log.d(TAG, "LevelPlay Rewarded : onAdLoaded")
                    if (mRewardedAd.isAdReady) {
                        mRewardedAd.showAd(context as Activity)
                    }
                    apiUtils.callImpressionRequestAPI(
                        appId,
                        adapter.adapterId.toString(),
                        placementId,
                        "Rewarded",
                        adapter.partner.toString(),
                        "SUCCESS"
                    )
                }

                override fun onAdLoadFailed(p0: LevelPlayAdError) {
                    Log.d(TAG, "LevelPlay Rewarded : onAdLoadFailed " + p0.errorMessage)
                    loadAt(index + 1)
                }

                override fun onAdDisplayed(p0: LevelPlayAdInfo) {
                }

                override fun onAdRewarded(p0: LevelPlayReward, p1: LevelPlayAdInfo) {
                }
            })
        }

        loadAt(0)
    }

    fun loadAdViewAd(
        appId: String,
        placementId: String,
        adapters: List<DataItem>,
        parameters: TapMindAdapterResponseParameters,
        adFormat: TapMindAdFormat,
        activity: Activity,
        listener: TapMindAdViewAdapterListener
    ) {
        if (adapters.isEmpty()) {
            listener.onAdViewAdLoadFailed(
                TapMindAdapterError(204, "No banner adapters available")
            )
            return
        }

        val sortedAdapters = adapters.sortedBy { it.priority }


        fun loadAt(index: Int) {
            if (index >= sortedAdapters.size) {
                listener.onAdViewAdLoadFailed(
                    TapMindAdapterError(204, "No fill from all banner adapters")
                )
                return
            }

            val adapter = sortedAdapters[index]
            val adUnitId = adapter.adUnitId.toString()


//            val width = adapter.sizes?.firstOrNull()?.width ?: 0
//            val height = adapter.sizes?.firstOrNull()?.height ?: 0
//
//            val adSize = getAdMobAdSize(activity, width, height)


            val adSize = LevelPlayAdSize.createAdaptiveAdSize(activity)

            val adConfig = LevelPlayBannerAdView.Config.Builder()
                .setAdSize(adSize!!)
                .setPlacementName("DefaultBanner")
                .build()

            val levelPlayBanner = LevelPlayBannerAdView(activity, adUnitId, adConfig)

            levelPlayBanner.bannerListener = object : LevelPlayBannerAdViewListener {
                override fun onAdLoaded(adInfo: LevelPlayAdInfo) {
                    Log.d(TAG, "onAdLoaded: adContainer")
                    apiUtils.callImpressionRequestAPI(
                        appId,
                        adapter.adapterId.toString(),
                        placementId,
                        "Banner",
                        adapter.partner.toString(),
                        "SUCCESS"
                    )
                }

                override fun onAdLoadFailed(error: LevelPlayAdError) {
                    Log.d(TAG, "onAdLoadFailed: adContainer ${error.errorMessage}")
                    Log.d(TAG, "onAdLoadFailed: adContainer ${error.errorCode}")
                    loadAt(index + 1)
                }

                override fun onAdDisplayed(adInfo: LevelPlayAdInfo) {
                    Log.d(TAG, "onAdDisplayed: adContainer")
                }

                override fun onAdDisplayFailed(adInfo: LevelPlayAdInfo, error: LevelPlayAdError) {
                    Log.d(TAG, "onAdDisplayFailed: adContainer")
                }

                override fun onAdClicked(adInfo: LevelPlayAdInfo) {
                    Log.d(TAG, "onAdClicked: adContainer")
                }

                override fun onAdExpanded(adInfo: LevelPlayAdInfo) {
                    Log.d(TAG, "onAdExpanded: adContainer")
                }

                override fun onAdCollapsed(adInfo: LevelPlayAdInfo) {
                    Log.d(TAG, "onAdCollapsed: adContainer")
                }

                override fun onAdLeftApplication(adInfo: LevelPlayAdInfo) {
                    Log.d(TAG, "onAdLeftApplication: adContainer")
                }
            }
            levelPlayBanner.loadAd()
        }
        loadAt(0)
    }

    fun getAdMobAdSize(activity: Activity, width: Int, height: Int): AdSize {
        return if (width > 0 && height > 0) {
            Log.d(TAG, "Using dynamic AdSize: ${width}x${height}")
            AdSize(width, height)   // Custom size from API
        } else {
            Log.d(TAG, "Using fallback AdSize.BANNER")
            AdSize.BANNER          // Fallback to Google default banner
        }
    }

    private fun updateMuteState(serverParameters: Bundle) {
        if (serverParameters.containsKey("is_muted")) {
            MobileAds.setAppMuted(serverParameters.getBoolean("is_muted"))
        }
    }
}