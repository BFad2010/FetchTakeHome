package com.example.fetchhomeassignment.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.fetchhomeassignment.R

@Composable
fun InfoDialog(
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
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    Text(
                        modifier = Modifier.weight(2f),
                        text = stringResource(R.string.need_help),
                        fontSize = 22.sp,
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    text = stringResource(R.string.info_step_one),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start,
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    text = stringResource(R.string.info_step_2),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start,
                )
            }
        }
    }
}