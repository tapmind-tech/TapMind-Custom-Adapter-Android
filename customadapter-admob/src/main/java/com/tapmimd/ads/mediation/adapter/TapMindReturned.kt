package com.tapmimd.ads.mediation.adapter


import android.content.Context
import android.view.View
import com.google.android.gms.ads.mediation.MediationBannerAd
import com.google.android.gms.ads.mediation.MediationBannerAdCallback
import com.google.android.gms.ads.mediation.MediationInterstitialAd
import com.google.android.gms.ads.mediation.MediationInterstitialAdCallback
import com.google.android.gms.ads.mediation.MediationRewardedAd
import com.google.android.gms.ads.mediation.MediationRewardedAdCallback

class TapMindAdmobBannerReturned(
    private val bannerView: View
) : MediationBannerAd {

    var callback: MediationBannerAdCallback? = null

    override fun getView(): View = bannerView
}

class TapMindAdmobInterstitialReturned(
    private val loader: SampleInterstitialCustomEventLoader
) : MediationInterstitialAd {

    var callback: MediationInterstitialAdCallback? = null

    override fun showAd(context: Context) {
        loader.showAd(context)
    }
}

class TapMindAdmobRewardedReturned(
    private val loader: SampleRewardedCustomEventLoader
) : MediationRewardedAd {

    var callback: MediationRewardedAdCallback? = null

    override fun showAd(context: Context) {
        loader.showAd(context)
    }
}
