package com.example.grupa3.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.grupa3.model.PlanCategory
import com.example.grupa3.navigation.Screen

@Composable
fun PlanListScreen(navController: NavHostController, viewModel: PlanListViewModel) {
    val currentState = viewModel.state

    Column(modifier = Modifier.fillMaxSize().padding(top = 32.dp, start = 16.dp, end = 16.dp)) {
        Text(
            text = "Daily plans",
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
                    CircularProgressIndicator()
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
                            Text(text = "${plan.title} - ${plan.status}")
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