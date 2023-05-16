package com.example.sistemasdeboletosaereos.db

import androidx.lifecycle.LiveData
import androidx.room.*
@Dao
interface AgenciaDAO {

    @Query("SELECT * FROM agencia")
    fun getAll(): LiveData<List<AerolineasEntity>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(agencia: AgenciaEntity)
    @Update
    suspend fun update(agencia: AgenciaEntity)
    @Delete
    suspend fun delete(agencia: AgenciaEntity)
    @Query("DELETE FROM agencia")
    suspend fun deleteAll()
}