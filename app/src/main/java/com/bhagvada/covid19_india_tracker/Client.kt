package com.bhagvada.covid19_india_tracker

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

object Client {
    private val okHttpClient = OkHttpClient()

    private val request  = Request.Builder().url("https://api.covid19india.org/data.json").build()
    private val district_request = Request.Builder().url("https://api.covid19india.org/state_district_wise.json").build()
    val api = okHttpClient.newCall(request)
    val district_api = okHttpClient.newCall(district_request)


}