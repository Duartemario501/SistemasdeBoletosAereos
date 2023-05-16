package com.example.sistemasdeboletosaereos.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import java.util.Date

@Entity(
    tableName = "Recompensas",
    primaryKeys = ["id_Recopensas"],
    foreignKeys = [

        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id_Usuario"],
            childColumns = ["id_Usuario"],
            onDelete = CASCADE
        )
    ]
)
data class RecompensasEntity(


    @ColumnInfo(name = "id_Recompensas")
    val id_Recompensas: Int,
    val nombre: String,
    val descripcion: String,
    @ColumnInfo(name = "Puntos")
    val Puntos: Int,
    @ColumnInfo(name = "is_Usuario")
    val id_Usuario: Int

)