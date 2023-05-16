package com.example.sistemasdeboletosaereos.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import java.util.Date

@Entity(
    tableName = "Cancelaciones",
    primaryKeys = ["id_cancelaciones"],
    foreignKeys = [

        ForeignKey(
            entity = ReservacionEntity::class,
            parentColumns = ["id_reservacion"],
            childColumns = ["id_reservacion"],
            onDelete = CASCADE
        )
    ]
)
data class CancelacionesEntity(


    @ColumnInfo(name = "id_cancelaciones")
    val id_cancelaciones: Int,
    @ColumnInfo(name = "cod_materia")
    val fecha_cancelacion: Date,
    val motivodeCancelacion: String,

)

