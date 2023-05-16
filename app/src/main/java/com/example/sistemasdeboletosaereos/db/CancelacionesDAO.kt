package com.example.sistemasdeboletosaereos.db

import androidx.lifecycle.LiveData
import androidx.room.*
@Dao
interface CancelacionesDAO {
    @Query("SELECT * FROM cancelaciones")
    fun getAll(): LiveData<List<CancelacionesEntity>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cancelaciones: CancelacionesEntity)
    @Update
    suspend fun update(cancelaciones: CancelacionesEntity)
    @Delete
    suspend fun delete(cancelaciones: CancelacionesEntity)
    @Query("DELETE FROM cancelaciones")
    suspend fun deleteAll()
}