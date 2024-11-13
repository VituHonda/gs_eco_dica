package com.example.gs_eco_dica.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gs_eco_dica.model.EcoDicaModel

@Database(entities = [EcoDicaModel::class], version = 1)
abstract class EcoDicaDatabase : RoomDatabase() {

    abstract fun EcoDicaDao(): EcoDicaDao
}