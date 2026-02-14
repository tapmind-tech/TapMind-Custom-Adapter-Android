package com.tapminds.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import com.tapminds.adapter.listener.TapMindAdapterError
import com.tapminds.adapter.listener.TapMindAdapterResponseParameters
import com.tapminds.adapter.listener.TapMindMediationAdapterBase
import com.tapminds.adapter.listener.TapMindsAdapter
import com.tapminds.adapter.listener.TapMindsAdapterInitializationParameters
import com.tapminds.admob.AdMobManager
import com.tapminds.ads.banner.TapMindAdViewAdapter
import com.tapminds.ads.banner.TapMindAdViewAdapterListener
import com.tapminds.ads.interstitial.TapMindInterstitialAdapterListener
import com.tapminds.ads.native.TapMindNativeAdAdapterListener
import com.tapminds.ads.native.TapMindNativeAdapter
import com.tapminds.ads.reward.TapMindRewardedAdapterListener
import com.tapminds.ironSource.IronSourceManager
import com.tapminds.network.AdData
import com.tapminds.network.AdRequest
import com.tapminds.network.AdRequestPayloadHolder
import com.tapminds.network.AdRestManagerImpl
import com.tapminds.network.DataItem
import com.tapminds.network.Failure
import com.tapminds.network.Success
import com.tapminds.tapmindsads.TapMindAdFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class TapMindsMediationAdapter private constructor() : TapMindMediationAdapterBase(),
    TapMindAdViewAdapter, TapMindNativeAdapter, CoroutineScope {

    private val TAG = "APP@@@"
    private val TAG1 = "TapMindsMediationAdapter"
//    private var admobManager : AdMobManager?= null
//    private var fbManager: FbManager?= null


    private var job = Job()
    override val coroutineContext: CoroutineContext get() = Dispatchers.IO + job

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: TapMindsMediationAdapter? = null

        fun getInstance(): TapMindsMediationAdapter {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: TapMindsMediationAdapter().also {
                    INSTANCE = it
                }
            }
        }
    }

    override fun initialize(
        tapMindsAdapterInitializationParameters: TapMindsAdapterInitializationParameters?,
        context: Context?,
        onCompletionListener: TapMindsAdapter.OnCompletionListener?
    ) {

//        tapMindsAdapterInitializationParameters?.let {
//            onCompletionListener?.let { it1 ->
//                FbManager.Companion.getInstance().initialize(
//                    it,
//                    context as Activity?, it1
//                )
//            }
//        }
//        fbManager = FbManager()
//        fbManager?.initialize(tapMindsAdapterInitializationParameters!!, context as Activity, object : TapMindsAdapter.OnCompletionListener {
//            override fun onCompletion(p0: TapMindsAdapter.InitializationStatus?, p1: String?) {
//                Log.d(TAG, "$TAG1 TapMindsMediationAdapter Initializing failed")
//                onCompletionListener?.onCompletion(p0,p1)
////                checkCompletion(p0, p1)
//            }
//
//        })

//        admobManager = AdMobManager()
//        admobManager?.initialize(tapMindsAdapterInitializationParameters, context as Activity, object : TapMindsAdapter.OnCompletionListener {
//            override fun onCompletion(p0: TapMindsAdapter.InitializationStatus?, p1: String?) {
//                Log.d(TAG, "TapMindsMediationAdapter Initializing failed")
//                onCompletionListener?.onCompletion(p0,p1)
////                checkCompletion(p0, p1)
//            }
//
//        })
        onCompletionListener?.onCompletion(
            TapMindsAdapter.InitializationStatus.INITIALIZED_SUCCESS,
            "TapMinds Mediation Adapter initialized"
        )
    }

//    private lateinit var admobManager: AdMobManager
//    private lateinit var fbManager: FbManager
//
//    private val admob = "admob"
//    private val facebook = "facebook"
//    private val adsnetwork = "ads_network"
//
//    fun initialize(
//        parameters: MaxAdapterInitializationParameters,
//        activity: Activity,
//        onCompletionListener: MaxAdapter.OnCompletionListener
//    ) {
//
//        var completedCount = 0
//        val totalAdapters = 2
//        var finalStatus: MaxAdapter.InitializationStatus? = null
//        var finalMessage: String? = null
//
//        fun checkCompletion(status: MaxAdapter.InitializationStatus?, message: String?) {
//            synchronized(this) {
//                completedCount++
//                // Keep the latest status/message
//                finalStatus = status
//                finalMessage = message
//
//                if (completedCount == totalAdapters) {
//                    onCompletionListener.onCompletion(finalStatus, finalMessage)
//                }
//            }
//        }
//
//        admobManager = AdMobManager()
//        admobManager.initialize(parameters, activity, object : MaxAdapter.OnCompletionListener {
//            override fun onCompletion(p0: MaxAdapter.InitializationStatus?, p1: String?) {
//                checkCompletion(p0, p1)
//            }
//
//        })
//
//        fbManager = FbManager()
//        fbManager.initialize(parameters, activity, object : MaxAdapter.OnCompletionListener {
//            override fun onCompletion(p0: MaxAdapter.InitializationStatus?, p1: String?) {
//                checkCompletion(p0, p1)
//            }
//
//        })


//        AdMobAdapter.initialize(parameters,activity,object : MaxAdapter.OnCompletionListener{
//            override fun onCompletion(adapter: MaxAdapter.InitializationStatus?, p1: String?) {
//                if(adapter!!.equals(MaxAdapter.InitializationStatus.INITIALIZED_SUCCESS)){
//                    onCompletionListener.onCompletion(adapter,null)
//                }else{
//                    onCompletionListener.onCompletion(adapter,null)
//                }
//            }
//        })
//    }


    //region MaxSignalProvider Methods
//    fun collectSignal(
//        parameters: MaxAdapterSignalCollectionParameters?,
//        activity: Activity?,
//        callback: MaxSignalCollectionListener?
//    ) {
//
//        val map = HashMap<String, Any>()
//        map.put("test","value")
//        val restManager = AdRestManagerImpl()
//
//        launch {
//            val response = restManager.getAd(map)
//            withContext(Dispatchers.Main) {
//                when (response) {
//                    is Success -> {
//                        Log.d(TAG, "api call : "+response.data)
//                    }
//                    is Failure ->{
//                        Log.d(TAG, "api call error: "+response.error)
//                    }
//                }
////                when (response) {
////                    is Success -> listener.onAdLoaded(response.data)
////                    is Failure -> listener.onAdLoadFailure(response.error)
////                }
//            }
//        }
//
//        Log.d(TAG, "collectSignal")
//    }
    //endregion

    override fun loadAdViewAd(
        params: TapMindAdapterResponseParameters,
        format: TapMindAdFormat,
        activity: Context,
        listener: TapMindAdViewAdapterListener
    ) {
        Log.d(TAG, "$TAG1 : loadBannerAd")

        fetchAdFromServer(onSuccess = { adapters, adData, adapterName ->
            if (adapters[0].partner == "GAM" || adapters[0].partner == "AdMob") {
                AdMobManager.getInstance().loadAdViewAd(
                    adData, adapters, params, format, activity as Activity, listener, adapterName
                )
            } else if (adapters[0].partner == "IronSource") {
                IronSourceManager.getInstance().loadAdViewAd(
                    adData, adapters, params, format, activity as Activity, listener, adapterName
                )
            }
        }, onFailure = { error ->
            listener.onAdViewAdLoadFailed(error)
        })

//        val adPayload = AdRequestPayloadHolder.playLoad
//        val appName = adPayload?.appName
//        val placementName = adPayload?.placementId
//        val appVersion = adPayload?.appVersion
//        val adType = adPayload?.adType
//        val country = adPayload?.country
//
//        Log.e("TapMindAdapterAdmob", "loadAdViewAd: $adPayload")
//
//        val map =
//            AdRequest(appName, placementName, appVersion, "Android", "dev", adType, country)
//
//        launch {
//            val restManager = AdRestManagerImpl()
//            val response = restManager.bidRequest(map)
//            withContext(Dispatchers.Main) {
//                when (response) {
//                    is Success -> {
//                        Log.d(TAG, "api call : " + response.data)
//                        val adId = response.data.adapters?.get(0)?.adUnitId
//                            ?: "ca-app-pub-3940256099942544/6300978111"
//                        AdMobManager.getInstance().loadAdViewAd(
//                            parameters,
//                            adId,
//                            adFormat,
//                            activity as Activity,
//                            callback
//                        )
//                    }
//
//                    is Failure -> {
//                        Log.d(TAG, "api call : " + response.error.errorCode)
//                        callback.onAdViewAdLoadFailed(
//                            TapMindAdapterError(
//                                response.error.errorCode,
//                                response.error.errorMessage!!
//                            )
//                        )
//                    }
//                }
//            }
//        }

//        FbManager.getInstance().loadAdViewAd(parameters, adFormat, activity as Activity, callback)
    }


    fun loadInterstitialAd(
        parameters: TapMindAdapterResponseParameters,
        context: Context,
        callback: TapMindInterstitialAdapterListener
    ) {
        Log.d(TAG, "$TAG1 : loadInterstitialAd")
        val adPayload = AdRequestPayloadHolder.playLoad

        Log.e("TapMindAdapterAdmob", "loadAdViewAd: $adPayload")

        fetchAdFromServer(onSuccess = { adapters, adData, adapterName ->
            if (adapters[0].partner == "GAM" || adapters[0].partner == "AdMob") {
                AdMobManager.getInstance().loadInterstitialAd(
                    adData, parameters, adapters, context, callback, adapterName
                )
            } else if (adapters[0].partner == "IronSource") {
                IronSourceManager.getInstance().loadInterstitialAd(
                    adData, parameters, adapters, context, callback, adapterName
                )
            }
        }, onFailure = { error ->
            callback.onInterstitialAdLoadFailed(error)
        })
    }

    fun showInterstitialAd(
        parameters: TapMindAdapterResponseParameters,
        activity: Activity,
        callback: TapMindInterstitialAdapterListener
    ) {
        AdMobManager.getInstance().showInterstitialAd(parameters, activity, callback)
//        FbManager.getInstance().showInterstitialAd(parameters, activity, callback)
    }


    fun loadRewardedAd(
        parameters: TapMindAdapterResponseParameters,
        context: Context,
        callback: TapMindRewardedAdapterListener
    ) {
//        val adPayload = AdRequestPayloadHolder.playLoad
//        val appName = adPayload?.appName
//        val appVersion = adPayload?.appVersion
//        val adType = adPayload?.adType
//        val country = adPayload?.country
//
//        Log.e("TapMindAdapterAdmob", "loadAdViewAd: $adPayload")
//        AdMobManager.getInstance().loadRewardedAd(parameters, context, callback)

        fetchAdFromServer(
            onSuccess = { adapters, adData, adapterName ->
                if (adapters[0].partner == "GAM" || adapters[0].partner == "AdMob") {
                    AdMobManager.getInstance().loadRewardedAd(
                        adData, parameters, adapters, context, callback, adapterName
                    )
                } else if (adapters[0].partner == "IronSource") {
                    IronSourceManager.getInstance()
                        .loadRewardedAd(
                            adData,
                            parameters,
                            adapters,
                            context,
                            callback,
                            adapterName
                        )
                }
            },
            onFailure = { error ->
                callback.onRewardedAdLoadFailed(error)
            }
        )

//        FbManager.getInstance().loadRewardedAd(parameters, context as Activity, callback)
    }

    fun showRewardedAd(
        parameters: TapMindAdapterResponseParameters,
        activity: Activity,
        callback: TapMindRewardedAdapterListener
    ) {
        AdMobManager.getInstance().showRewardedAd(parameters, activity, callback)
//        FbManager.getInstance().showRewardedAd(parameters, activity, callback)
    }


    override fun loadNativeAd(
        var1: TapMindAdapterResponseParameters?,
        var2: Activity?,
        var3: TapMindNativeAdAdapterListener?
    ) {
//        val adPayload = AdRequestPayloadHolder.playLoad
//        val appName = adPayload?.appName
//        val appVersion = adPayload?.appVersion
//        val adType = adPayload?.adType
//        val country = adPayload?.country
//
//        Log.e("TapMindAdapterAdmob", "loadAdViewAd: $adPayload")
//        AdMobManager.getInstance().loadNativeAd(parameters!!, activity!!, callback!!)
//        FbManager.getInstance().loadNativeAd(parameters!!,  activity as Activity, callback!!)
        fetchAdFromServer(onSuccess = { adapters, adData, adapterName ->
            AdMobManager.getInstance().loadNativeAd(
                adData, var1!!, adapters, var2!!, var3!!, adapterName
            )
        }, onFailure = { error ->
            var3?.onNativeAdLoadFailed(error)
        })
    }

    private fun fetchAdFromServer(
        onSuccess: (List<DataItem>, AdData, String) -> Unit,
        onFailure: (TapMindAdapterError) -> Unit
    ) {
        val adPayload = AdRequestPayloadHolder.playLoad

        Log.e("App@@@", "adPayload: $adPayload")

        val request = AdRequest(
            adPayload?.appName,
            adPayload?.placementId,
            adPayload?.packageName,
            adPayload?.appVersion,
            "Android",
            "dev",
            adPayload?.adType,
            adPayload?.country
        )

        Log.e("TapMindAdapterAdmob", "AdRequest: $request")

        launch {
            val restManager = AdRestManagerImpl()
            val response = restManager.bidRequest(request)

            withContext(Dispatchers.Main) {
                when (response) {
                    is Success -> {
                        val sortedAdapters = response.data.adapters?.sortedBy {
                            it.priority ?: Int.MAX_VALUE
                        } ?: emptyList()

                        Log.e("TapMindAdapterAdmob", "fetchAdFromServer: ${response.data}")
                        onSuccess(sortedAdapters, response.data, adPayload?.adapterName.toString())
                    }

                    is Failure -> {
                        Log.e("TapMindAdapterAdmob", "fetchAdFromServer: ${response.error}")
                        onFailure(
                            TapMindAdapterError(
                                response.error.errorCode,
                                response.error.errorMessage ?: "Unknown error"
                            )
                        )
                    }
                }
            }
        }
    }
}