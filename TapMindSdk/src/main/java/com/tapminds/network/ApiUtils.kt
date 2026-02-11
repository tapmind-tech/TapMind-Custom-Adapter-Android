package com.tapminds.network

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiUtils {

    fun callImpressionRequestAPI(
        appId: String,
        adapterId: String,
        placementId: String,
        adType: String,
        partner: String,
        result: String
    ) {
        val apiInter = ApiClient.retrofit.create(APInterface::class.java)

        val impressionRequest =
            ImpressionRequest(appId, adapterId, placementId, adType, partner, result)

        Log.e("TapMindAdapterAdmob", "callImpressionRequestAPI: $impressionRequest")
        val callImpression = apiInter.impressionRequest(impressionRequest)

        callImpression.enqueue(object : Callback<ImpressionResponse> {
            override fun onResponse(
                call: Call<ImpressionResponse?>,
                response: Response<ImpressionResponse?>
            ) {
                Log.e("TapMindAdapterAdmob", "onResponseImpression: ${response.raw()}")
                Log.e("TapMindAdapterAdmob", "onResponseImpression: ${response.body()?.message}")
            }

            override fun onFailure(
                call: Call<ImpressionResponse?>,
                t: Throwable
            ) {
                Log.e("TapMindAdapterAdmob", "onFailure: ${t.message}")
            }
        })
    }
}