package com.example.grupa3.ui.list

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import com.example.grupa3.R
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.grupa3.model.PlanCategory
import com.example.grupa3.navigation.Screen
import com.airbnb.lottie.compose.*

@Composable
fun PlanListScreen(navController: NavHostController, viewModel: PlanListViewModel) {
    val currentState = viewModel.state

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animation))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    Column(modifier = Modifier.fillMaxSize().padding(top = 32.dp, start = 16.dp, end = 16.dp)) {
        Text(
            text = "My Plans",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = { viewModel.filterByCategory(null) },
                modifier = Modifier.weight(1f)
            ) {
                Text("All", style = MaterialTheme.typography.bodySmall)
            }

            Button(
                onClick = { viewModel.filterByCategory(PlanCategory.WORK) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Work", style = MaterialTheme.typography.bodySmall)
            }

            Button(
                onClick = { viewModel.filterByCategory(PlanCategory.PERSONAL) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Pers.", style = MaterialTheme.typography.bodySmall)
            }

            Button(
                onClick = { viewModel.filterByCategory(PlanCategory.HEALTH) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Health", style = MaterialTheme.typography.bodySmall)
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
                        modifier = Modifier.size(200.dp)
                    )
                    Text("Loading plans...", modifier = Modifier.padding(top = 8.dp))
                }
            }
            is PlanListUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(currentState.plans) { plan ->
                        Button(
                            onClick = {
                                navController.navigate(Screen.PlanDetailsScreen.appendId(plan.id))
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    imageVector = when(plan.category) {
                                        PlanCategory.WORK -> Icons.Default.DateRange
                                        PlanCategory.PERSONAL -> Icons.Default.Person
                                        PlanCategory.HEALTH -> Icons.Default.Favorite
                                    },
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                    tint = Color.White
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Text(
                                    text = "${plan.title} - ${plan.status}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
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