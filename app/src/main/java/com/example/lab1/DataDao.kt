package com.example.lab1

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DataDao {
    @Query("SELECT * FROM data_table")
    fun getAll(): List<DataEntity>

    @Insert
    suspend fun insert(data: DataEntity)

    @Query("DELETE FROM data_table")
    suspend fun deleteAll()
}