package com.tapmimd.ads.mediation.adapter

import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.mediation.NativeAdMapper
import com.google.android.gms.ads.nativead.NativeAd
import com.tapminds.ads.native.TapMindNativeAd

class SampleUnifiedNativeAdMapper(
    private val sampleAd: TapMindNativeAd
) : NativeAdMapper() {

    private val TAG = "APP@@@"
    private val TAG1 = "SampleUnifiedNativeAdMapper"

    init {

        sampleAd.title?.let { headline = it }

//        val imagess =  arrayListOf(SampleNativeMappedImage(sampleAd.mainImage?.drawable, sampleAd.mainImage?.uri, SAMPLE_SDK_IMAGE_SCALE))

//        Log.d("APP@@@","size "+imagess.size)


        sampleAd.mainImage?.let { mainImage ->
            images = mutableListOf<NativeAd.Image>(
                SampleNativeMappedImage(
                    mainImage.drawable,
                    mainImage.uri,
                    SAMPLE_SDK_IMAGE_SCALE
                )
            )
        }
        sampleAd.body?.let { body = it }
        sampleAd.icon?.let { iconImage ->
            icon =
                SampleNativeMappedImage(iconImage.drawable, iconImage.uri, SAMPLE_SDK_IMAGE_SCALE)
        }
        sampleAd.callToAction?.let { callToAction = it }
        sampleAd.advertiser?.let { advertiser = it }
        sampleAd.starRating?.let { starRating = it }
        sampleAd.store?.let { store = it }
        sampleAd.price?.let { price = it }
        setHasVideoContent(sampleAd.hasVideoContent)
        sampleAd.adChoise?.let { adChoicesContent = it }
        sampleAd.mediaView?.let { setMediaView(it) }
        sampleAd.mediaContentAspectRatio.let { mediaContentAspectRatio = it }

        val extras = Bundle()
        extras.putString("degree_of_awesomeness", "100") // example
        setExtras(extras)

        // GOOGLE HANDLES IMPRESSIONS + CLICKS
        overrideClickHandling = false
        overrideImpressionRecording = false

    }


//    private var callback: MediationNativeAdCallback? = null
//
//    fun setCallback(cb: MediationNativeAdCallback) {
//        callback = cb
//    }


    companion object {
        const val SAMPLE_SDK_IMAGE_SCALE = 1.0
    }

    override fun trackViews(
        containerView: View,
        clickableAssetViews: Map<String?, View?>,
        nonClickableAssetViews: Map<String?, View?>
    ) {
        val clickList = clickableAssetViews.values.toList()
        sampleAd.prepareForInteraction(clickList as List<View>, containerView as ViewGroup)
    }

//    override fun recordImpression() {
//        sampleAd.recordImpression();
//    }
//
//    override fun handleClick(view: View) {
//        sampleAd.handleClick(view)
//    }

//    override fun trackViews(containerView: View, clickableAssetViews: Map<String?, View?>, nonClickableAssetViews: Map<String?, View?>) {
//        super.trackViews(containerView, clickableAssetViews, nonClickableAssetViews)
//        clickableAssetViews.forEach { (key, view) ->
//            view?.setOnClickListener {
//                callback?.reportAdClicked()
//                handleClick(view)
//            }
//        }
//    }
}

class SampleNativeMappedImage(
    private val drawable: Drawable?,
    private val uri: Uri?,
    private val scale: Double
) : NativeAd.Image() {

    override fun getDrawable(): Drawable? = drawable

    override fun getUri(): Uri? = uri

    override fun getScale(): Double = scale
}
