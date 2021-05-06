package com.despance.harcamatakipapp.api

import com.despance.harcamatakipapp.data.CurrencyModel
import com.despance.harcamatakipapp.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET

interface CurrencyAPI {
    @GET("$API_KEY ")
    suspend fun getCurrency(): Response<CurrencyModel>

}