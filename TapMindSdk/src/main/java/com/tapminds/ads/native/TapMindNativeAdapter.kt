package com.tapminds.ads.native

import android.content.Context
import com.tapminds.adapter.listener.TapMindAdapterResponseParameters

interface TapMindNativeAdapter {
    fun loadNativeAd(
        var1: TapMindAdapterResponseParameters?,
        var2: Context?,
        index: Int,
        var3: TapMindNativeAdAdapterListener?
    )
}