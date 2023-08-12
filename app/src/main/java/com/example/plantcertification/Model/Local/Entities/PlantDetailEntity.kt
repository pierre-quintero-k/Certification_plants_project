package com.example.plantcertification.Model.Local.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "plant_detail_table")
data class PlantDetailEntity (

    @PrimaryKey
    val id: Int,
    val nombre: String,
    val tipo: String,
    val descripcion: String,
    val imagen: String
        )