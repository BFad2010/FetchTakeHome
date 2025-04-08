package com.example.fetchhomeassignment.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FetchItemsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertItem(item: FetchItemsEntity)

    @Insert
    suspend fun insertAll(items: List<FetchItemsEntity>)

    @Query("SELECT * FROM FetchItemsEntity")
    fun getAllItems(): List<FetchItemsEntity>

    @Query("SELECT * FROM FetchItemsEntity WHERE id =:id")
    fun getItemById(id: String): FetchItemsEntity

    @Query("DELETE FROM FetchItemsEntity")
    fun deleteAll()
}