package com.example.fetchhomeassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fetchhomeassignment.ui.compose.InfoDialog
import com.example.fetchhomeassignment.ui.compose.MainScreen
import com.example.fetchhomeassignment.ui.theme.FetchHomeAssignmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var showInfoDialog = remember { mutableStateOf(false) }
            AnimatedVisibility(
                visible = showInfoDialog.value,
            ) {
                InfoDialog { showInfoDialog.value = false }
            }
            FetchHomeAssignmentTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 24.dp)
                                .background(Color.LightGray),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Image(
                                modifier = Modifier.size(60.dp),
                                painter = painterResource(R.drawable.fetch_icon),
                                colorFilter = ColorFilter.tint(Color.Black),
                                contentDescription = "Fetch-Icon"
                            )
                            Text(
                                modifier = Modifier
                                    .padding(start = 12.dp),
                                text = stringResource(R.string.app_bar_title),
                                fontSize = 24.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 8.dp),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Image(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .shadow(6.dp, shape = RoundedCornerShape(24.dp))
                                        .clip(RoundedCornerShape(24.dp))
                                        .clickable {
                                            showInfoDialog.value = true
                                        },
                                    painter = painterResource(R.drawable.more_info),
                                    contentDescription = "Fetch-Icon"
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    MainScreen(innerPadding)
                }
            }
        }
    }
}