package com.example.sistemasdeboletosaereos.db



import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

import java.util.Date

@Entity(
    tableName = "Tarifas",
    primaryKeys = ["id_Tarifas"],
    foreignKeys = [

        ForeignKey(
            entity = RutasEntity::class,
            parentColumns = ["id_Vuelo"],
            childColumns = ["id_Vuelo"],

        )
    ]
)
data class TarifasEntity(

    @ColumnInfo(name = "id_Tarifas")
    val id_Tarifas: Int,
    @ColumnInfo(name = "id_Ruta")
    val id_Ruta: Int,
    val tipo: String,
    @ColumnInfo(name = "precio")
    val precio: Double

    )