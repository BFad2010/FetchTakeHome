package com.example.fetchhomeassignment.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FetchItemsEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "listId") val listId: String,
    @ColumnInfo(name = "name") val name: String,
)