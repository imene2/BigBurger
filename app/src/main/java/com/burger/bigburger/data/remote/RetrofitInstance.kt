package com.burger.bigburger.data.remote

import android.content.Context
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance {

    companion object {

        private lateinit var retrofit: Retrofit
        private val BASE_URL = "https://bigburger.useradgents.com/"

        fun retrofitInstance(context: Context): ApiService {

            val cert = CertificatePinner.Builder()
                .add("bigburger.useradgents.com", "sha256/2fLB1yr1EFzJB8vLTX+SKkvJqjYaXij+74gq1MflTmM=")
                .build()


            val okHttpClient = OkHttpClient.Builder()
                .certificatePinner(cert)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build()

            retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(ApiService::class.java)

        }
    }






}
