package com.example.plantcertification.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.plantcertification.Model.Local.Database.PlantDataBase
import com.example.plantcertification.Model.Local.Entities.PlantDetailEntity
import com.example.plantcertification.Model.Local.Entities.PlantEntity
import com.example.plantcertification.Model.PlantRepository
import kotlinx.coroutines.launch

class PlantViewModel(application: Application): AndroidViewModel(application) {

    private val repository: PlantRepository

    private val plantDetailLiveData= MutableLiveData<PlantDetailEntity>()

    private var idSelected: String="-1"

    init {

        val PlantDao=PlantDataBase.getDataBase(application).getPlantDao()
        repository= PlantRepository(PlantDao)

        viewModelScope.launch {
            repository.fetchPlant()
        }
    }

    fun getPlantList(): LiveData<List<PlantEntity>> = repository.plantListLiveData

    fun getPlantDetail(): LiveData<PlantDetailEntity> = plantDetailLiveData

    fun getPlantDetailByIdFromInternet(id: String)=viewModelScope.launch {

        val plantDetail=repository.fetchPlantDetail(id)
        plantDetail?.let {
            plantDetailLiveData.postValue(it)
        }
    }


}