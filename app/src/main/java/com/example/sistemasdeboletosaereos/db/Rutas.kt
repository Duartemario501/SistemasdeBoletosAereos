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

    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "origen")
    val origen: String,
    @ColumnInfo(name = "destino")
    val destino: String

)