package com.example.sistemasdeboletosaereos.db

import androidx.lifecycle.LiveData
import androidx.room.*
@Dao
interface TarifasDAO {
    @Query("SELECT * FROM Tarifas")
    fun getAll(): LiveData<List<TarifasEntity>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tarifas: TarifasEntity)
    @Update
    suspend fun update(tarifas: TarifasEntity)
    @Delete
    suspend fun delete(tarifas: TarifasEntity)
    @Query("DELETE FROM tarifas")
    suspend fun deleteAll()

}