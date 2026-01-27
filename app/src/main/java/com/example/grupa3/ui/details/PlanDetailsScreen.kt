package com.example.grupa3.ui.details

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.grupa3.model.PlanCategory

@Composable
fun PlanDetailsScreen(
    navController: NavHostController,
    planId: String?,
    viewModel: PlanDetailsViewModel
) {
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        viewModel.onPermissionResult(isGranted)
    }

    LaunchedEffect(planId) {
        viewModel.initPlan(planId)
    }

    val currentState = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (currentState) {
            is PlanDetailsUiState.Loading -> {
                CircularProgressIndicator()
            }
            is PlanDetailsUiState.Success -> {
                val plan = currentState.plan

                val icon = when (plan.category) {
                    PlanCategory.WORK -> Icons.Default.DateRange
                    PlanCategory.PERSONAL -> Icons.Default.Person
                    PlanCategory.HEALTH -> Icons.Default.Favorite
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = plan.title, style = MaterialTheme.typography.headlineLarge)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = plan.description,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Status: ${plan.status}",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.secondary
                )

                if (currentState.permissionMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = currentState.permissionMessage,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(onClick = { viewModel.activatePlan() }) {
                    Text("Activate Plan")
                }

                Button(onClick = { viewModel.completePlan() }) {
                    Text("Complete Plan")
                }

                Button(onClick = {
                    locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }) {
                    Text("Show Location")
                }
            }
            is PlanDetailsUiState.Error -> {
                Text("Plan not found", color = MaterialTheme.colorScheme.error)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            Text("Back")
        }
    }
}