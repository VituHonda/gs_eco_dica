package com.example.gs_eco_dica.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.gs_eco_dica.model.EcoDicaModel

@Dao
interface EcoDicaDao {

    @Query("SELECT * FROM EcoDicaModel")
    fun getAll(): LiveData<List<EcoDicaModel>>

    @Insert
    fun insert(dica: EcoDicaModel)

    @Delete
    fun delete(dica: EcoDicaModel)
}