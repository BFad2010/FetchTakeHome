package com.example.fetchhomeassignment.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FetchItemsEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class FetchItemsDatabase : RoomDatabase() {
    abstract fun FetchItemsDao(): FetchItemsDao
}