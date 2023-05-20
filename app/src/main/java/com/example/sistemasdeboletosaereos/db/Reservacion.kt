package com.example.sistemasdeboletosaereos.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

import java.util.Date

@Entity(
    tableName = "Reservacion",
    primaryKeys = ["id_Reservacion"],
    foreignKeys = [

        ForeignKey(
            entity = RutasEntity::class,
            parentColumns = ["id_ruta"],
            childColumns = ["id_ruta"],

        ),
        ForeignKey(
            entity = PasajerosEntity::class,
            parentColumns = ["id_pasajeros"],
            childColumns = ["id_pasajeros"],

        ),
        ForeignKey(
            entity = AgenciaEntity::class,
            parentColumns = ["id"],
            childColumns = ["id"],

        )
    ]
)
data class ReservacionEntity(
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "id_ruta")
    val id_ruta: Int,
    @ColumnInfo(name = "id_pasajero")
    val id_pasajero: Int,
    @ColumnInfo(name = "id_agencia")
    val id_agencia: Int,
    val feha:Date
)