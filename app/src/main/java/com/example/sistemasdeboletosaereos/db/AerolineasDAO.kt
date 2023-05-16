package com.example.sistemasdeboletosaereos.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AerolineasDAO {

    @Query("SELECT * FROM aerolineas")
    fun getAll(): LiveData<List<AerolineasEntity>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(aerolineas: AerolineasEntity)
    @Update
    suspend fun update(aerolineas: AerolineasEntity)
    @Delete
    suspend fun delete(aerolineas: AerolineasEntity)
    @Query("DELETE FROM aerolineas")
    suspend fun deleteAll()

}