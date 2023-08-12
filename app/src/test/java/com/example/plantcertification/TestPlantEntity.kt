package com.example.plantcertification

import com.example.plantcertification.Model.Local.Entities.PlantEntity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class TestPlantEntity {

    private lateinit var plantEntity: PlantEntity

    @Before
    fun setup(){

        plantEntity= PlantEntity(
            id = 2,
            nombre = "prueba unitaria",
            tipo = "tipo",
            imagen = "probando la clase plant",
            descripcion = "descripcion 1"
        )
    }

    @After
    fun tearDown(){
        //después aprenderé a hacer esto
    }

    @Test
    fun testPlant(){
        assert(plantEntity.id==2)
        assert(plantEntity.nombre=="prueba unitaria")
        assert(plantEntity.tipo=="tipo")
        assert(plantEntity.imagen=="probando la clase plant")
        assert(plantEntity.descripcion=="descripcion 1")
    }
}