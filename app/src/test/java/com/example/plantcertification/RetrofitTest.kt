package com.example.plantcertification

import com.example.plantcertification.Model.Remote.RetrofitClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitTest {

    private lateinit var mockWebServer: MockWebServer


    @Before
    fun setup(){
        mockWebServer= MockWebServer()
    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }
    @Test
    fun testRetrofit(){
        val expectedBaseUrl= mockWebServer.url("agua/").toString()

        val retrofit= Retrofit.Builder().baseUrl(expectedBaseUrl)
            .addConverterFactory(GsonConverterFactory.create()).build()

        RetrofitClient.retrofitInstance=retrofit
        val retrofitInstance= RetrofitClient.retrofitInstance
        assertEquals(expectedBaseUrl,retrofitInstance.baseUrl().toString())

    }


}