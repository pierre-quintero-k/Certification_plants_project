package com.example.plantcertification.Model

import com.example.plantcertification.Model.Local.Entities.PlantDetailEntity
import com.example.plantcertification.Model.Local.Entities.PlantEntity
import com.example.plantcertification.Model.Remote.FromInet.Plant
import com.example.plantcertification.Model.Remote.FromInet.PlantDetail


fun fromInternetPlantEntity(plantList: List<Plant>): List<PlantEntity>{

    return plantList.map {
        PlantEntity(
            id=it.id,
            nombre = it.nombre,
            tipo = it.tipo,
            descripcion = it.descripcion,
            imagen = it.imagen
        )
    }
}

fun fromInternetPlantDetailEntity(plant: PlantDetail): PlantDetailEntity{

    return PlantDetailEntity(
        id = plant.id,
        nombre = plant.nombre,
        tipo = plant.tipo,
        imagen = plant.imagen,
        descripcion = plant.descripcion
    )
}