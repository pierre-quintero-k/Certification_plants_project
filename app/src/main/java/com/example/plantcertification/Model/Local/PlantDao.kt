package com.example.plantcertification.Model.Local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.plantcertification.Model.Local.Entities.PlantEntity
import com.example.plantcertification.Model.Local.Entities.PlantDetailEntity


@Dao
interface PlantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPlants(plantList: List<PlantEntity>)

    @Query("SELECT * FROM PLANT_TABLE ORDER BY id ASC")
    fun getAllPlants(): LiveData<List<PlantEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlantDetail(plant: PlantDetailEntity)

    @Query("SELECT * FROM PLANT_DETAIL_TABLE WHERE id=:id")
    fun getPlantDetailById(id: String): LiveData<PlantDetailEntity>





}