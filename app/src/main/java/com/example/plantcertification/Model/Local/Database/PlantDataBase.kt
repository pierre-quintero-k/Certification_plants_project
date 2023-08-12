package com.example.plantcertification.Model.Local.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.plantcertification.Model.Local.Entities.PlantEntity
import com.example.plantcertification.Model.Local.PlantDao
import com.example.plantcertification.Model.Local.Entities.PlantDetailEntity

@Database(entities = [PlantEntity::class, PlantDetailEntity::class], version = 1, exportSchema = false)
abstract class PlantDataBase: RoomDatabase() {

    abstract fun getPlantDao(): PlantDao

    companion object{

        @Volatile
        private var
                INSTANCE : PlantDataBase? = null
        fun getDataBase(context: Context) : PlantDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlantDataBase::class.java, "plantDb")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}