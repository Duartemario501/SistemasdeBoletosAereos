package com.example.sistemasdeboletosaereos.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Aerolineas")
data class AerolineasEntity(
    @PrimaryKey
    @ColumnInfo(name = "id", defaultValue = "1")
    val id: Int,
    val nombre: String,
    val pais_origen: String,
    val pais_destino: String,

)
