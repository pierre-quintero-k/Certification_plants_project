package com.example.plantcertification.Model.Local.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "plant_table")
data class PlantEntity (

    @PrimaryKey
    val id: Int,
    val nombre: String,
    val tipo: String,
    val descripcion: String,
    val imagen: String
        )