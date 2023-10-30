package com.example.lab1

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "data_table")
data class DataEntity(
    @PrimaryKey val uid: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "number") val number: Int?
)