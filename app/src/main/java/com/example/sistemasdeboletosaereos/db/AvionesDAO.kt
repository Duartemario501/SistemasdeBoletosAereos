package com.example.sistemasdeboletosaereos.db

import androidx.lifecycle.LiveData
import androidx.room.*
@Dao
interface AvionesDAO {
    @Query("SELECT * FROM aviones")
    fun getAll(): LiveData<List<AvionesEntity>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(aviones: AvionesEntity)
    @Update
    suspend fun update(aviones: AvionesEntity)
    @Delete
    suspend fun delete(aviones: AvionesEntity)
    @Query("DELETE FROM aviones")
    suspend fun deleteAll()
}