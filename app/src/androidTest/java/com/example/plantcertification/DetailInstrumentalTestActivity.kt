package com.example.plantcertification

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.plantcertification.View.MainActivity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DetailInstrumentalTestActivity {

    //esta prueba simula un intent que envia datos a la main activity


    @Test
    fun testShowValidatePlant(){

        //creamos contexto

        val context= androidx.test.platform.app.InstrumentationRegistry.getInstrumentation()
            .targetContext

        //creamos el intent que va de este contexto a la main activity

        val intent= Intent(context, MainActivity::class.java)

        //enviamos los datos con key value

        intent.putExtra("id",1)
        intent.putExtra("nombre","plant prueba")
        intent.putExtra("tipo","tipo prueba")
        intent.putExtra("imagen","test imagen")
        intent.putExtra("descripcion","test descripcion")

        //lanzamos la mainactivity de prueba con el intent simulado

        val activityScenario=ActivityScenario.launch<MainActivity>(intent)

        //una vez lanzada la actividad la declaramos y empezamos a revisar lo recibido en el intent

        activityScenario.onActivity { activity->

            //primero ver que no sea null
            assertNotNull(activity)

            //revisar que lo enviado sea recibido en la actividad

            assertEquals(1,activity.intent.getIntExtra("id",-1))
            assertEquals("plant prueba",activity.intent.getStringExtra("nombre",))
            assertEquals("tipo prueba",activity.intent.getStringExtra("tipo",))
            assertEquals("test imagen",activity.intent.getStringExtra("imagen",))
            assertEquals("test description",activity.intent.getStringExtra("descripcion",))




        }




    }
}