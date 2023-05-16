package com.example.sistemasdeboletosaereos.db

import androidx.lifecycle.LiveData
import androidx.room.*
@Dao
interface PasajerosDAO {
    @Query("SELECT * FROM pasajeros")
    fun getAll(): LiveData<List<PasajerosEntity>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pasajeros: PasajerosEntity)
    @Update
    suspend fun update(pasajeros: PasajerosEntity)
    @Delete
    suspend fun delete(pasajeros: PasajerosEntity)
    @Query("DELETE FROM pasajeros")
    suspend fun deleteAll()
}