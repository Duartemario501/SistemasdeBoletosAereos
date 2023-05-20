package com.example.sistemasdeboletosaereos.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

import java.util.Date

@Entity(
    tableName = "Rutas",
    primaryKeys = ["id_Rutas"],
    foreignKeys = [

        ForeignKey(
            entity = VuelosEntity::class,
            parentColumns = ["id_Vuelo"],
            childColumns = ["id_Vuelo"],

        )
    ]
)
data class RutasEntity(

    @ColumnInfo(name = "id_Ruta")
    val id_Recompensas: Int,
    @ColumnInfo(name = "id_Vuelo")
    val id_Vuelo: Int,
    @ColumnInfo(name = "precio")
    val precio: Int
)