package com.example.plantcertification

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.plantcertification.Model.Local.Database.PlantDataBase
import com.example.plantcertification.Model.Local.Entities.PlantDetailEntity
import com.example.plantcertification.Model.Local.Entities.PlantEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.lang.Exception
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import kotlin.jvm.Throws


@RunWith(AndroidJUnit4::class)
//@Config(sdk=[Build.VERSION_CODES.Q], manifest= Config.NONE)
class PlantDaoInstrumentalTest {

    //la regla dice que haremos las pruebas en el hilo principal
    @get:Rule val instantTaskExecutorRule= InstantTaskExecutorRule()

    //private lateinit var plantDao: PlantDao
    private lateinit var db: PlantDataBase

    @Before
    fun setupDB() {
        //setteamos la base de datos antes del test
        db= Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
            PlantDataBase::class.java).build()
    }

    @After
    @Throws(IOException::class)
    fun shutDown(){
        //despues del test apagamos la base de datos
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun coroutineDBInsertPlant(){
        //primer test instanciamos el dao

        val plantDao= db.getPlantDao()

        //creamos 2 plant entity

        val plant1= PlantEntity(1,"pruena","tipo1","prueba imagen", "image1")
        val plant2= PlantEntity(2,"prueba 2","tipo2","prueba imagen 2", "imagen2")
        val plants= listOf(plant1,plant2)

        //en la coroutine

        runBlocking(Dispatchers.Default) {
            //insertamos plant 1 y2(plants)
            plantDao.insertAllPlants(plants)
        }

        plantDao.getAllPlants().observeForever{
            //observamos el livedata y comprobamos que su tamaño sea 2
            assertThat(plants.size, equalTo(2))
        }





    }



    @ExperimentalCoroutinesApi
    @Test
    @Throws(Exception::class)
    fun insertPlantDetail() {
        //acá probaremos la otra funcion del dao
        val plantDao = db.getPlantDao()

        val plantDetail = PlantDetailEntity(
            2,
            "nombre insertado",
            "tipo",
            "imagen2",
            "descripcion2"
        )

        //variable para manejar coroutine en ambiente de prueba
        val testDispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(testDispatcher)

        testDispatcher.runBlockingTest {
            plantDao.insertPlantDetail(plantDetail)
            val plantLiveData = plantDao.getPlantDetailById("2")
            val plantValue = plantLiveData.getOrAwaitValue()

            plantDao.getPlantDetailById("2")
                assertThat(plantValue?.tipo, equalTo("tipo"))
                assertThat(plantValue?.imagen, equalTo("imagen2"))

        }
        Dispatchers.resetMain()

    }

    //funcion auxiliar obtiene o espera valor de un livedata
    fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS,
        afterObserve: () -> Unit = {}
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        //el observer captura el valor del livedata
        val observer = object : Observer<T> {
            override fun onChanged(o: T) {
                data = o
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }
        this.observeForever(observer)

        afterObserve.invoke()

        // Si no se obtiene el valor en el tiempo esperado lanza una excepcion
        if (!latch.await(time, timeUnit)) {
            this.removeObserver(observer)
            throw TimeoutException("El valor del LiveData no se obtuvo en el tiempo esperado.")
        }

        @Suppress("UNCHECKED_CAST")
        return data as T
    }

}