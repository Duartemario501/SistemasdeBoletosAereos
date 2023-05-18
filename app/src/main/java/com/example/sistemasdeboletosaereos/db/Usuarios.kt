package com.example.sistemasdeboletosaereos.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Usuarios")
data class UsuarioEntity(
    @PrimaryKey
    @ColumnInfo(name = "id", defaultValue = "1")
    val id: Int,
    val nombre: String,
    val Email: String,
    val Password: String,
    val fechaNacimiento: Date,
    val telefono:String,
    val rol:String

    )