package com.example.sistemasdeboletosaereos.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface HorariosDAO {
    @Query("SELECT * FROM horarios")
    fun getAll(): LiveData<List<HorariosEntity>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(horarios: HorariosEntity)
    @Update
    suspend fun update(horarios: HorariosEntity)
    @Delete
    suspend fun delete(horarios: HorariosEntity)
    @Query("DELETE FROM horarios")
    suspend fun deleteAll()
}