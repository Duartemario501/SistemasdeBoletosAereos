package com.example.sistemasdeboletosaereos.db

import androidx.lifecycle.LiveData
import androidx.room.*
@Dao
interface RutasDAO {
    @Query("SELECT * FROM Rutas")
    fun getAll(): LiveData<List<RutasEntity>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(rutas: RutasEntity)
    @Update
    suspend fun update(rutas: RutasEntity)
    @Delete
    suspend fun delete(rutas: RutasEntity)
    @Query("DELETE FROM rutas")
    suspend fun deleteAll()

}