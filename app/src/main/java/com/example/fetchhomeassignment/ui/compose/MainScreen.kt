package com.example.fetchhomeassignment.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.room.ColumnInfo
import com.example.fetchhomeassignment.R
import com.example.fetchhomeassignment.ui.vm.FetchItemsViewModel
import com.example.fetchhomeassignment.ui.vm.UiState

@Composable
fun MainScreen(
    innerPadding: PaddingValues,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val fetchItemsViewModel: FetchItemsViewModel = hiltViewModel()
    val fetchUiState = fetchItemsViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            fetchItemsViewModel.retrieveAllFetchItems()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray)
            .padding(innerPadding)
    ) {
        Column {
            when (val uiState = fetchUiState.value) {
                is UiState.CONTENT -> ItemsList(uiState.items)
                is UiState.ERROR -> FetchItemsError(uiState.message) {
//                    fetchItemsViewModel.retrieveAllFetchItems()
                }
                UiState.LOADING -> LoadingFetchItems()
            }
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    text = stringResource(R.string.bio_stamp),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontStyle = FontStyle.Italic,

                )
            }
        }
    }
}