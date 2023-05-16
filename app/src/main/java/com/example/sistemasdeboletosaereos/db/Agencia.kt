package com.example.sistemasdeboletosaereos.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Agencia")
data class AgenciaEntity(
    @PrimaryKey
    @ColumnInfo(name = "id", defaultValue = "1")
    val id: Int,
    val nombre: String,
    val modelo: String,
    @ColumnInfo(name = "capacidad", defaultValue = "0")
    val capacidad: Int,

    )