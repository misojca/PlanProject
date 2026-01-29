package com.example.grupa3.ui.list

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import com.example.grupa3.R
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.grupa3.model.PlanCategory
import com.example.grupa3.navigation.Screen
import com.airbnb.lottie.compose.*
import androidx.compose.ui.text.font.FontStyle

@Composable
fun PlanListScreen(navController: NavHostController, viewModel: PlanListViewModel) {
    val currentState = viewModel.state

    LaunchedEffect(Unit) {
        viewModel.refreshList()
    }

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading4_animation))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    Column(modifier = Modifier.fillMaxSize()
        .background(brush = Brush.verticalGradient(
            0.0f to Color(0xFF1A1A1A),
            0.5f to Color(0xFF121212),
            1.0f to Color(0xFF080808)
        ))
        .padding(top = 32.dp, start = 16.dp, end = 16.dp)) {
        Text(
            text = "My Plans",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.5.sp
            ),
            color = Color(0xFFF1F5F9),
            modifier = Modifier.padding(bottom = 16.dp)
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 22.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val filterButtonColors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1E293B),
                contentColor = Color(0xFF94A3B8)
            )

            Button(
                onClick = { viewModel.filterByCategory(null) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(15.dp),
                colors = filterButtonColors,
            ) {
                Text("All", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold))
            }

            Button(
                onClick = { viewModel.filterByCategory(PlanCategory.WORK) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(15.dp),
                colors = filterButtonColors,
            ) {
                Text("Work", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold))
            }

            Button(
                onClick = { viewModel.filterByCategory(PlanCategory.PERSONAL) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(15.dp),
                colors = filterButtonColors,
            ) {
                Text("Pers.", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold))
            }

            Button(
                onClick = { viewModel.filterByCategory(PlanCategory.HEALTH) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(15.dp),
                colors = filterButtonColors,
            ) {
                Text("Health", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold))
            }
        }

        when (currentState) {
            is PlanListUiState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LottieAnimation(
                        composition = composition,
                        progress = { progress },
                        modifier = Modifier.size(500.dp)
                    )
                    Text("Loading plans...", modifier = Modifier.padding(top = 8.dp))
                }
            }
            is PlanListUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(13.dp)
                ) {
                    items(currentState.plans) { plan ->
                        Button(
                            onClick = {
                                navController.navigate(Screen.PlanDetailsScreen.appendId(plan.id)) {
                                    launchSingleTop = true
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF242424)
                            ),
                            shape = RoundedCornerShape(15.dp),
                            border = BorderStroke(0.05.dp, Color(0xFF334155)),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    imageVector = when (plan.category) {
                                        PlanCategory.WORK -> Icons.Default.DateRange
                                        PlanCategory.PERSONAL -> Icons.Default.Person
                                        PlanCategory.HEALTH -> Icons.Default.Favorite
                                    },
                                    contentDescription = null,
                                    modifier = Modifier.size(49.dp),
                                    tint = when (plan.category) {
                                        PlanCategory.WORK -> Color(0xFF60A5FA)
                                        PlanCategory.PERSONAL -> Color(0xFF34D399)
                                        PlanCategory.HEALTH -> Color(0xFFF87171)
                                    }
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Column {
                                    Text(
                                        text = plan.title,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.SemiBold,
                                            letterSpacing = 0.5.sp
                                        ),
                                        color = Color(0xFFF1F5F9)
                                    )

                                    Text(
                                        text = plan.status.name,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontStyle = FontStyle.Italic,
                                            fontFamily = FontFamily.Serif
                                        ),
                                        color = Color(0xFFE2D1B3)
                                    )
                                }
                            }
                        }
                    }
                }
            }
            is PlanListUiState.Error -> {
                Text(text = "Error: ${currentState.message}", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}