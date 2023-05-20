package com.example.sistemasdeboletosaereos.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

import java.util.Date

@Entity(
    tableName = "Tripulacion",
    primaryKeys = ["id_Tripulacion"],
    foreignKeys = [

        ForeignKey(
            entity = VuelosEntity::class,
            parentColumns = ["id_Vuelo"],
            childColumns = ["id_Vuelo"],

        )
    ]
)
data class TripulacionEntity(


    @ColumnInfo(name = "id_tripulacion")
    val id_tripulacion: Int,
    @ColumnInfo(name = "id_vuelo")
    val id_vuelo: Int,
    val nombre: String,
    val cargo: String,

    )