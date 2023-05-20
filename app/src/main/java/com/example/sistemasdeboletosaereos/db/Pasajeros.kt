package com.example.sistemasdeboletosaereos.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

import java.util.Date

@Entity(
    tableName = "Pasajeros",
    primaryKeys = ["id_Pasajeros"],
    foreignKeys = [

        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id_Usuario"],
            childColumns = ["id_Usuario"],

        )
    ]
)
data class PasajerosEntity(

    @ColumnInfo(name = "id_Pasajeros")
    val id_Pasajeros: Int,
    @ColumnInfo(name = "id_Usuario")
    val id_Usuario: Int,
    val nombre: String,
    val telefono: String,
    val Email: String,

    )