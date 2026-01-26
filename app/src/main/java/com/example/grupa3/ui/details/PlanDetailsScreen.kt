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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.grupa3.model.PlanStatus
import com.example.grupa3.ui.list.PlanListUiState
import com.example.grupa3.ui.list.PlanListViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@Composable
fun PlanDetailsScreen(
    navController: NavHostController,
    planId: String,
    viewModel: PlanListViewModel
) {
    val plan = viewModel.getPlanById(planId)
    val currentState = viewModel.state
    var permissionMessage by remember { mutableStateOf("") }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        permissionMessage = if (isGranted) "Location allowed" else "Location denied"
        //if (currentState is PlanListUiState.Success) {
        //    val msg = if (isGranted) "Location allowed" else "Location denied"
        //}
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (currentState) {
            is PlanListUiState.Loading -> {
                CircularProgressIndicator()
            }
            is PlanListUiState.Success -> {
                val plan = plan ?: return@Column

                Text(text = plan.title, style = MaterialTheme.typography.headlineLarge)
                Text(text = "Status: ${plan.status}")

                if (permissionMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = permissionMessage,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                //val plan = currentState.plan

                //Text(text = plan.title, style = MaterialTheme.typography.headlineLarge)
                //Text(text = "Status: ${plan.status}")

                //if (currentState.permissionMessage.isNotEmpty()) {
                //    Spacer(modifier = Modifier.height(8.dp))
                //    Text(
                //        text = currentState.permissionMessage,
                //        color = MaterialTheme.colorScheme.primary,
                //        style = MaterialTheme.typography.bodyMedium
                //    )
                //}

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { viewModel.updatePlanStatus(planId, PlanStatus.ACTIVE) }) {
                    Text("Activate Plan")
                }

                Button(onClick = {
                    locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }) {
                    Text("Show Location")
                }

                Button(onClick = { viewModel.updatePlanStatus(planId, PlanStatus.COMPLETED) }) {
                    Text("Complete Plan")
                }
            }
            is PlanListUiState.Error -> {
                Text("Plan not found")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }
    }
}