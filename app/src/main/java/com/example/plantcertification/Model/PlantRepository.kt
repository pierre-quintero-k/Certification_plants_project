package com.example.plantcertification.Model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.plantcertification.Model.Local.Entities.PlantDetailEntity
import com.example.plantcertification.Model.Local.PlantDao
import com.example.plantcertification.Model.Remote.RetrofitClient

class PlantRepository(private val plantDao: PlantDao) {

    private val retrofitClient= RetrofitClient.retrofitInstance()

    val plantListLiveData= plantDao.getAllPlants()

    val plantDetailListLiveData= MutableLiveData<PlantDetailEntity>()

    suspend fun fetchPlant(){

        val service= kotlin.runCatching { retrofitClient.fetchPlantList() }

        service.onSuccess {
            when (it.code()){
                in 200..299 ->it.body()?.let {
                    plantDao.insertAllPlants(fromInternetPlantEntity(it))
                }
                else-> Log.d("****Repo****","${it.code()}-${it.errorBody()}")
            }
            service.onFailure {
                Log.e("<<<<<<Error>>>>>>>", "${it.message}")
            }
        }
    }

    suspend fun fetchPlantDetail(id: String): PlantDetailEntity?{
        val service= kotlin.runCatching { retrofitClient.fetchPlantDetail(id) }
        return service.getOrNull()?.body()?.let { 
            plantDetail ->
            val plantDetailEntity= fromInternetPlantDetailEntity(plantDetail)
            plantDao.insertPlantDetail(plantDetailEntity)
            plantDetailEntity
        }
    }

}