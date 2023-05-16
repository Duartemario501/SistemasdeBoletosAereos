package com.example.sistemasdeboletosaereos.db

import androidx.lifecycle.LiveData
import androidx.room.*
@Dao
interface ReservacionDAO {
    @Query("SELECT * FROM Reservacion")
    fun getAll(): LiveData<List<ReservacionEntity>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(reservacion: ReservacionEntity)
    @Update
    suspend fun update(reservacion: ReservacionEntity)
    @Delete
    suspend fun delete(reservacion: ReservacionEntity)
    @Query("DELETE FROM reservacion")
    suspend fun deleteAll()
}