package com.example.lab1

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DataDao {
    @Query("SELECT * FROM data")
    suspend fun getAll(): List<DataEntity>

    @Query("DELETE FROM data")
    suspend fun deleteAll()

    @Insert
    suspend fun insert(data: DataEntity)
}