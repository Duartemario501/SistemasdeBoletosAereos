package com.example.sistemasdeboletosaereos.db

import androidx.lifecycle.LiveData
import androidx.room.*
@Dao
interface UsuariosDAO {
    @Query("SELECT * FROM usuarios")
    fun getAll(): LiveData<List<UsuarioEntity>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(usuario: UsuarioEntity)
    @Update
    suspend fun update(usuario: UsuarioEntity)
    @Delete
    suspend fun delete(usuario: UsuarioEntity)
    @Query("DELETE FROM usuarios")
    suspend fun deleteAll()
}