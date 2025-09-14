package com.example.consumerferiadog

import com.example.consumerferiadog.model.Feriado
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BrasilApi {
    @GET("api/feriados/v1/{year}")
    suspend fun getFeriados(@Path("year") year: Int) : Response<List<Feriado>>
}