package com.example.fetchhomeassignment.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchhomeassignment.data.FetchItemsResult
import com.example.fetchhomeassignment.data.FetchListItem
import com.example.fetchhomeassignment.data.db.FetchItemsEntity
import com.example.fetchhomeassignment.data.db.FetchItemsRepo
import com.example.fetchhomeassignment.data.service.FetchItemsServiceAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FetchItemsViewModel @Inject constructor(
    private val fetchItemsServiceAdapter: FetchItemsServiceAdapter,
    private val fetchItemsRepo: FetchItemsRepo,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {
    private val mutableUiState = MutableStateFlow<UiState>(UiState.LOADING)
    val uiState: StateFlow<UiState> = mutableUiState.asStateFlow()

    suspend fun retrieveAllFetchItems() = withContext(dispatcher) {
        mutableUiState.update { UiState.LOADING }
        delay(2000)
        // check database for list
        val dbItems = try {
            fetchItemsRepo.getAllItems()
        } catch (e: Exception) {
            emptyList()
        }
        if (!dbItems.isNullOrEmpty()) {
            val fetchItems = dbItems.map {
                FetchListItem(
                    id = it.id.toInt(),
                    listId = it.listId,
                    name = it.name,
                )
            }.groupBy { it.listId }.toSortedMap()
            mutableUiState.update { UiState.CONTENT(fetchItems) }
        } else {
            fetchItemsFromService()
        }
    }

    private suspend fun fetchItemsFromService() {
        val fetchItemsResponse = fetchItemsServiceAdapter.getFetchItems()
        when (val response = fetchItemsResponse) {
            is FetchItemsResult.Failure -> mutableUiState.update {
                UiState.ERROR(response.errorMessage)
            }

            is FetchItemsResult.Success -> {
                updateDatabase(response.items)
                val filterItems = filterItems(response.items)
                val groupItems = filterItems.groupBy { it.listId }.toSortedMap()
                mutableUiState.update { UiState.CONTENT(groupItems) }
            }
        }
    }

    private suspend fun updateDatabase(items: List<FetchListItem>) {
        fetchItemsRepo.deleteAllItems()
        val filterItems = filterItems(items)
        val entities = filterItems.map { item ->
            item.name?.let {
                FetchItemsEntity(item.id.toString(), item.listId, item.name)
            }
        }.filterNotNull()
        fetchItemsRepo.insertItems(entities)
    }

    private fun filterItems(items: List<FetchListItem>): List<FetchListItem> {
        return items.filter { !it.name.isNullOrEmpty() }
    }
}

sealed class UiState {
    data object LOADING : UiState()
    data class CONTENT(val items: Map<String, List<FetchListItem>>) : UiState()
    data class ERROR(val message: String) : UiState()
}