package com.example.sistemasdeboletosaereos.db


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import java.util.Date

@Entity(
    tableName = "Horarios",
    primaryKeys = ["id_Horarios"],
    foreignKeys = [

        ForeignKey(
            entity = VuelosEntity::class,
            parentColumns = ["id_Vuelo"],
            childColumns = ["id_Vuelo"],

        )
    ]
)
data class HorariosEntity(

    @ColumnInfo(name = "id_Horario")
    val id_Horario: Int,
    @ColumnInfo(name = "id_Vuelo")
    val id_Vuelo: Int,
    val hora_salida: String,
    val hora_llegada: String,

)