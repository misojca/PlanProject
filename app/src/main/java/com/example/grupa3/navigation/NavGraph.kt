package com.example.grupa3.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.grupa3.data.mockPlans

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController=navController,
        startDestination = Screen.PlanListScreen.route
    ) {
        composable(route = Screen.PlanListScreen.route){
            PlanListScreen(navController = navController)
        }

        composable(
            route = Screen.PlanDetailsScreen.route + "/{planId}",
            arguments = listOf(navArgument("planId") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("planId")
            PlanDetailsScreen(navController = navController, planId = id)
        }
    }
}

@Composable
fun PlanListScreen(navController: NavHostController) {
    val plans = mockPlans

    Column(modifier = Modifier.fillMaxSize().padding(top = 32.dp, start = 16.dp, end = 16.dp)) {
        Text(text = "Daily plans", modifier = Modifier.padding(bottom = 16.dp))

        //dugmici za filtirranje work personal health

        LazyColumn (
            modifier = Modifier.fillMaxSize()
        ){
            items(plans) { plan ->
                Button(onClick = {
                    navController.navigate(Screen.PlanDetailsScreen.appendId(plan.id))
                } ) {
                    Text(text = "${plan.title} - ${plan.status}")
                }
            }
        }
    }
}


@Composable
fun PlanDetailsScreen(navController: NavHostController, planId: String?) {
    val plan = mockPlans.find { it.id == planId }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (plan != null) {
            Text(text = plan.title)
            Text(text = plan.description)
            Text(text = "Category: ${plan.category}")
            Text(text = "Status: ${plan.status}")

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { /* TODO: Change status in ACTIVE */ }) {
                Text("Activate")
            }

            Button(onClick = { /* TODO: Check permission and show loc */ }) {
                Text("Show Location")
            }
        } else {
            Text(text = "Plan not found")
        }

        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }
    }
}

