package com.example.sistemasdeboletosaereos.db

import androidx.lifecycle.LiveData
import androidx.room.*
@Dao
interface VuelosDAO {

    @Query("SELECT * FROM Vuelos")
    fun getAll(): LiveData<List<VuelosEntity>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vuelos: VuelosEntity)
    @Update
    suspend fun update(vuelos: VuelosEntity)
    @Delete
    suspend fun delete(vuelos: VuelosEntity)
    @Query("DELETE FROM vuelos")
    suspend fun deleteAll()
}