package com.example.grupa3.ui.details

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

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

                Text(text = plan.title, style = MaterialTheme.typography.headlineLarge)
                Text(text = "Status: ${plan.status}")

                if (currentState.permissionMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = currentState.permissionMessage,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { viewModel.activatePlan() }) {
                    Text("Activate Plan")
                }

                Button(onClick = {
                    locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }) {
                    Text("Show Location")
                }

                Button(onClick = { viewModel.completePlan() }) {
                    Text("Complete Plan")
                }
            }
            is PlanDetailsUiState.Error -> {
                Text("Plan not found")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }
    }
}