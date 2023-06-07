package com.example.sistemasdeboletosaereos.db


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

import java.util.Date

@Entity(
    tableName = "Vuelos",
    primaryKeys = ["id_Vuelo"],
    foreignKeys = [

        ForeignKey(
            entity = AerolineasEntity::class,
            parentColumns = ["id_aerolineas"],
            childColumns = ["id_aerolineas"],

        )
    ]
)
data class VuelosEntity(

    val id: String,
    val aerolinea_id: String,
    val ruta_id: String,
    val avion_id: String,
    val fecha_salida: String,
    val hora_salida: String,
    val fecha_regreso: String,
    val duracion: String,
    val descripcion: String,
    val precio: String,
    val tarifa: String,
    val clase: String,
    val origen: String,
    val destino: String,
    val reservacion: String = "",
    var estado: String = "ACT"

)