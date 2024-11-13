package com.example.gs_eco_dica.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EcoDicasViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EcoDicasViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EcoDicasViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}