package com.example.sistemasdeboletosaereos.db

import androidx.lifecycle.LiveData
import androidx.room.*
@Dao
interface TrupulacionDAo {
    @Query("SELECT * FROM tripulacion")
    fun getAll(): LiveData<List<TripulacionEntity>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tripulacion: TripulacionEntity)
    @Update
    suspend fun update(tripulacion: TripulacionEntity)
    @Delete
    suspend fun delete(tripulacion: TripulacionEntity)
    @Query("DELETE FROM tripulacion")
    suspend fun deleteAll()
}