package com.example.gs_eco_dica.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.gs_eco_dica.data.EcoDicaDao
import com.example.gs_eco_dica.data.EcoDicaDatabase
import com.example.gs_eco_dica.model.EcoDicaModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EcoDicasViewModel(application: Application) : AndroidViewModel(application) {

    private val ecoDicaDao: EcoDicaDao

    val ecoDicasLiveData: LiveData<List<EcoDicaModel>>

    init {
        val database = Room.databaseBuilder(
            getApplication(),
            EcoDicaDatabase::class.java,
            "eco_dicas_database"
        ).build()

        ecoDicaDao = database.EcoDicaDao()

        ecoDicasLiveData = ecoDicaDao.getAll()
    }

    fun addEcoDica(title: String, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val newEcoDica = EcoDicaModel(title = title, description = description)
            ecoDicaDao.insert(newEcoDica)
        }
    }

    fun removeEcoDica(dica: EcoDicaModel) {
        viewModelScope.launch(Dispatchers.IO) {
            ecoDicaDao.delete(dica)
        }
    }
}