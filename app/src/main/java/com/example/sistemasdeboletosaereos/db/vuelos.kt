package com.example.sistemasdeboletosaereos.db


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import java.util.Date

@Entity(
    tableName = "Vuelos",
    primaryKeys = ["id_Vuelo"],
    foreignKeys = [

        ForeignKey(
            entity = AerolineasEntity::class,
            parentColumns = ["id_aerolineas"],
            childColumns = ["id_aerolineas"],
            onDelete = CASCADE
        )
    ]
)
data class VuelosEntity(

    @ColumnInfo(name = "id_vuelos")
    val id_Vuelo: Int,
    @ColumnInfo(name = "id_aerolineas")
    val id_aerolineas: Int,
    val origen: String,
    val destino: String,
    val fecha_salida: String,
    val fecha_llegada: String,


)