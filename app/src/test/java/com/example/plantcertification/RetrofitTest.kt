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

    //usaremos mockito para simular la conexion

    private lateinit var mockWebServer: MockWebServer


    //primero el mock server
    @Before
    fun setup(){
        mockWebServer= MockWebServer()
    }

    @After
    fun tearDown(){
        //apagamos el mock server
        mockWebServer.shutdown()
    }
    //hacemos la prueba
    @Test
    fun testRetrofit(){
        //entrregamos una url base simulada
        val expectedBaseUrl= mockWebServer.url("agua/").toString()

        val retrofit= Retrofit.Builder().baseUrl(expectedBaseUrl)
            .addConverterFactory(GsonConverterFactory.create()).build()


        //usamos retrofit para pasar nuestra conexion simulada
        RetrofitClient.retrofitInstance=retrofit
        val retrofitInstance= RetrofitClient.retrofitInstance
        assertEquals(expectedBaseUrl,retrofitInstance.baseUrl().toString())

    }


}