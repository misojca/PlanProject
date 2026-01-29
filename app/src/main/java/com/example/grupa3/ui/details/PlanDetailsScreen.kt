package com.example.grupa3.ui.details

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    val filterButtonColors = ButtonDefaults.buttonColors(
        containerColor = Color(0xFF1E293B),
        contentColor = Color(0xFF94A3B8)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    0.0f to Color(0xFF1A1A1A),
                    0.5f to Color(0xFF121212),
                    1.0f to Color(0xFF080808)
                )
            )
            .padding(top = 55.dp, start = 16.dp, end = 16.dp),
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
                        modifier = Modifier.size(65.dp),
                        tint = when (plan.category) {
                            PlanCategory.WORK -> Color(0xFF60A5FA)
                            PlanCategory.PERSONAL -> Color(0xFF34D399)
                            PlanCategory.HEALTH -> Color(0xFFF87171)
                        }
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = plan.title,
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color(0xFFFDE68A)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = plan.description,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = Color(0xFFFDE68A)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Status: ${plan.status}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontStyle = FontStyle.Italic,
                        fontFamily = FontFamily.Serif
                    ),
                    color = Color(0xFFE2D1B3)
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

                Button(
                    onClick = { viewModel.activatePlan() },
                    shape = RoundedCornerShape(15.dp),
                    colors = filterButtonColors,
                ) {
                    Text("Activate Plan")
                }

                Spacer(modifier = Modifier.height(15.dp))

                Button(
                    onClick = { viewModel.completePlan() },
                    shape = RoundedCornerShape(15.dp),
                    colors = filterButtonColors,
                ) {
                    Text("Complete Plan")
                }

                Spacer(modifier = Modifier.height(15.dp))

                Button(
                    onClick = { locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION) },
                    shape = RoundedCornerShape(15.dp),
                    colors = filterButtonColors,
                ) {
                    Text("Show Location")
                }
            }
            is PlanDetailsUiState.Error -> {
                Text("Plan not found", color = MaterialTheme.colorScheme.error)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if (navController.previousBackStackEntry != null) {
                    navController.popBackStack()
                    }
                      },
            shape = RoundedCornerShape(15.dp),
            colors = filterButtonColors,
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            Text("Back")
        }
    }
}