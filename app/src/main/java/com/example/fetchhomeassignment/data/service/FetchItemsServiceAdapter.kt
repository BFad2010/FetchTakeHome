package com.example.fetchhomeassignment.data.service

import com.example.fetchhomeassignment.data.FetchItemsResult
import com.example.fetchhomeassignment.data.FetchListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class FetchItemsServiceAdapter @Inject constructor(
    private val fetchJsonApi: FetchJsonApi,
) {
    suspend fun getFetchItems(): FetchItemsResult = withContext(Dispatchers.IO) {
        Result.runCatching {
            val response = retrieveFetchItems()
            when {
                response.isSuccessful -> {
                    response.body()?.let { fetchItemsesponse ->
                        FetchItemsResult.Success(fetchItemsesponse)
                    } ?: run {
                        FetchItemsResult.Failure(ERROR_FETCHING_ITEMS)
                    }
                }

                else -> FetchItemsResult.Failure(ERROR_FETCHING_ITEMS)
            }
        }.onFailure {
            FetchItemsResult.Failure(ERROR_FETCHING_ITEMS)
        }.getOrElse { FetchItemsResult.Failure(ERROR_FETCHING_ITEMS) }
    }

    private fun retrieveFetchItems(): Response<List<FetchListItem>> {
        val requestUrl = "$FETCH_JSON_ENDPOINT"
        return fetchJsonApi.getFetchItems(requestUrl).execute()
    }

    companion object {
        private const val ERROR_FETCHING_ITEMS =
            "Error fetching items... \n\n Please check your internet connection and try again."
    }
}