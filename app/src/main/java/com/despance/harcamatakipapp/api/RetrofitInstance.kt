package com.despance.harcamatakipapp.api

import com.despance.harcamatakipapp.util.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }

    val api:CurrencyAPI by lazy {
        retrofit.create(CurrencyAPI::class.java)
    }
}