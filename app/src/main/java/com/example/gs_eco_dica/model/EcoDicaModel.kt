package com.example.gs_eco_dica.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EcoDicaModel(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String
)