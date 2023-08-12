package com.example.plantcertification.Model.Remote

import com.example.plantcertification.Model.Remote.FromInet.PlantDetail
import com.example.plantcertification.Model.Remote.FromInet.Plant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PlantApi {

    @GET("plantas")
    suspend fun fetchPlantList(): Response<List<Plant>>

    @GET("plantas/{id}")
    suspend fun fetchPlantDetail(@Path("id")id: String): Response<PlantDetail>
}