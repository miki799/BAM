package com.example.lab1

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DataEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dataDao(): DataDao
}

object DatabaseSingleton {
    private var database: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        if (database == null) {
            synchronized(AppDatabase::class.java) {
                if (database == null) {
                    database = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "database"
                    ).build()
                }
            }
        }
        return database!!
    }
}