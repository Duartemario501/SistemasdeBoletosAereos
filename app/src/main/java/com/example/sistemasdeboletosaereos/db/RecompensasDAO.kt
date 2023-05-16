package com.example.sistemasdeboletosaereos.db

import androidx.lifecycle.LiveData
import androidx.room.*
@Dao
interface RecompensasDAO {
    @Query("SELECT * FROM recompensas")
    fun getAll(): LiveData<List<RecompensasEntity>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(recompensas: RecompensasEntity)
    @Update
    suspend fun update(recompensas: RecompensasEntity)
    @Delete
    suspend fun delete(recompensas: RecompensasEntity)
    @Query("DELETE FROM recompensas")
    suspend fun deleteAll()
}