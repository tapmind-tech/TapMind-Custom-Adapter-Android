package com.tapminds.network

import android.util.Log
import com.tapminds.tapmindsads.UTCTimeUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiUtils {

    fun callImpressionRequestAPI(
        appId: String,
        adapterId: String,
        placementId: String,
        adType: String,
        event: String,
        errorMessage: String,
        partner: String,
        result: String,
        requestId: String,
        versionId: String,
        responseId: String,
        adapterName: String,
    ) {
        val apiInter = ApiClient.retrofit.create(APInterface::class.java)

        val impressionRequest =
            ImpressionRequestV2(
                appId,
                adapterId,
                placementId,
                adType,
                event,
                errorMessage,
                partner,
                result,
                requestId,
                versionId,
                responseId,
                adapterName,
                UTCTimeUtils.getUtcTimeString()
            )

        Log.e("TapMindAdapterAdmob", "callImpressionRequestAPI: $impressionRequest")
        val callImpression = apiInter.impressionRequestV2(impressionRequest)

        callImpression.enqueue(object : Callback<ImpressionResponseV2> {
            override fun onResponse(
                call: Call<ImpressionResponseV2?>,
                response: Response<ImpressionResponseV2?>
            ) {
                Log.e("TapMindAdapterAdmob", "onResponseImpression: ${response.raw()}")
                Log.e("TapMindAdapterAdmob", "onResponseImpression: ${response.body()?.message}")
            }

            override fun onFailure(
                call: Call<ImpressionResponseV2?>,
                t: Throwable
            ) {
                Log.e("TapMindAdapterAdmob", "onFailure: ${t.message}")
            }
        })
    }
}