package com.example.sistemasdeboletosaereos.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Aviones")
data class AvionesEntity(
    @PrimaryKey
    @ColumnInfo(name = "id", defaultValue = "1")
    val id: Int,
    val modelo: String

    )