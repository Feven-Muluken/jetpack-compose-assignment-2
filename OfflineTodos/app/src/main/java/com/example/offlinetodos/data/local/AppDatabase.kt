package com.example.offlinetodos.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [TodoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}
