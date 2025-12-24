package com.tapmimd.ads.mediation.adapter

abstract class SampleInterstitialListener {

    open fun onAdLoaded() {}
    open fun onAdFailed(errorCode: Int) {}
    open fun onAdShown() {}
    open fun onAdClicked() {}
    open fun onAdClosed() {}
}
