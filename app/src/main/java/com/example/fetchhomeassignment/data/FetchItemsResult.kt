package com.example.fetchhomeassignment.data

sealed class FetchItemsResult {
    data class Success(val items: List<FetchListItem>) : FetchItemsResult()
    data class Failure(val errorMessage: String) : FetchItemsResult()
}