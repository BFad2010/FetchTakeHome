package com.example.fetchhomeassignment.ui.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.fetchhomeassignment.R
import com.example.fetchhomeassignment.data.FetchListItem

@Composable
fun ItemsList(items: Map<String, List<FetchListItem>>) {
    var isExpanded = remember {
        List(items.size) { index: Int -> index to false }
            .toMutableStateMap()
    }

    fun collapseAll() {
        items.keys.forEachIndexed { index, month ->
            isExpanded[index] = false
        }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.DarkGray)
            .padding(top = 8.dp)
    ) {
        itemsIndexed(items.keys.toList()) { index, item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp)
                    .background(color = Color.DarkGray)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .clickable {
                        val isExpandedAlready =
                            isExpanded[index] ?: false
                        if (isExpandedAlready) {
                            collapseAll()
                        } else {
                            collapseAll()
                            isExpanded[index] = !(isExpanded[index] ?: true)
                        }
                    }) {
                Row(
                    modifier = Modifier.padding(start = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier.weight(2f),
                        text = "List ${item}",
                        textAlign = TextAlign.Start,
                        fontSize = 22.sp,
                    )
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
                        Image(
                            modifier = Modifier
                                .size(40.dp)
                                .graphicsLayer {
                                    rotationX = if (isExpanded[index] == true) 180f else 0f
                                },
                            colorFilter = ColorFilter.tint(Color.Gray),
                            painter = painterResource(R.drawable.down_arrow),
                            contentDescription = "Expand Item",
                        )
                    }
                }
            }
            AnimatedVisibility(
                visible = isExpanded[index] ?: false,
                enter = expandVertically(),
                exit = shrinkVertically(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(color = Color.LightGray)
                ) {
                    val sortItems = items.get(item)?.sortedBy { it.id }
                    sortItems?.forEach { item ->
                        FetchItem(item)
                    }
                }
            }
        }
    }
}

@Composable
private fun FetchItem(
    item: FetchListItem,
) {
    var showItemDialog = remember { mutableStateOf(false) }
    if (showItemDialog.value) {
        FetchItemDialog(item) {
            showItemDialog.value = false
        }
    }
    Text(
        modifier = Modifier
            .padding(start = 16.dp)
            .clickable { showItemDialog.value = true },
        text = item.name.orEmpty(),
        textAlign = TextAlign.Start,
        fontSize = 18.sp,
    )
    HorizontalDivider(
        modifier = Modifier.padding(
            vertical = 4.dp,
            horizontal = 12.dp
        ), color = Color.Black
    )
}

@Composable
private fun FetchItemDialog(
    item: FetchListItem,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .padding(vertical = 12.dp, horizontal = 36.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .background(Color.Transparent),
                horizontalAlignment = Alignment.Start,
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    Text(
                        modifier = Modifier.weight(2f),
                        text = "Item Details",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onDismiss() },
                        contentAlignment = Alignment.TopEnd,
                    ) {
                        Image(
                            modifier = Modifier.size(36.dp),
                            painter = painterResource(R.drawable.close),
                            colorFilter = ColorFilter.tint(Color.Black),
                            contentDescription = "Close Dialog",
                        )
                    }
                }
                HorizontalDivider()
                Text(
                    text = "ID: ${item.id}",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "List ID: ${item.listId}",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "Name: ${item.name}",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
fun LoadingFetchItems() {
    var currentRotation by remember { mutableStateOf(0f) }
    val rotation = remember { Animatable(currentRotation) }
    LaunchedEffect(Unit) {
        for (i in 0..100) {
            rotation.animateTo(
                targetValue = currentRotation + 360f,
                animationSpec = tween(850),
            ) {
                currentRotation = value
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .graphicsLayer(
                        rotationZ = rotation.value
                    ),
                painter = painterResource(R.drawable.fetch_icon),
                colorFilter = ColorFilter.tint(Color.LightGray),
                contentDescription = "Fetch-Icon"
            )
            Text(
                text = "FETCH-ing your items...",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontStyle = FontStyle.Italic,
            )
        }
    }
}

@Composable
fun FetchItemsError(message: String, onRefresh: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.padding(horizontal = 32.dp)) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = message,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.height(16.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                onClick = {
                    onRefresh()
                },
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.try_again),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}