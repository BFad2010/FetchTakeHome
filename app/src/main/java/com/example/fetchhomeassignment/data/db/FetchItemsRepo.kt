package com.example.fetchhomeassignment.data.db

import javax.inject.Inject

class FetchItemsRepo @Inject constructor(
    private val fetchItemsDao: FetchItemsDao,
) {
    suspend fun insertItems(items: List<FetchItemsEntity>) {
        fetchItemsDao.insertAll(items)
    }

    suspend fun getItemById(id: String): FetchItemsEntity? = fetchItemsDao.getItemById(id)

    suspend fun getAllItems(): List<FetchItemsEntity> = fetchItemsDao.getAllItems()

    suspend fun deleteAllItems() {
        fetchItemsDao.deleteAll()
    }
}