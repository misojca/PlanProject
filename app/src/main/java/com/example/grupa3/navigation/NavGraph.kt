package com.example.grupa3.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.grupa3.ui.details.PlanDetailsScreen
//import com.example.grupa3.ui.details.PlanDetailsViewModel
import com.example.grupa3.ui.list.PlanListScreen
import com.example.grupa3.ui.list.PlanListViewModel

@Composable
fun Navigation(mainViewModel: PlanListViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.PlanListScreen.route
    ) {

        composable(route = Screen.PlanListScreen.route) {
            //val listViewModel: PlanListViewModel = viewModel()
            PlanListScreen(
                navController = navController,
                viewModel = mainViewModel
            )
        }

        composable(
            route = Screen.PlanDetailsScreen.route + "/{planId}",
            arguments = listOf(navArgument("planId") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("planId")!!

            //val detailsViewModel: PlanDetailsViewModel = viewModel()

            PlanDetailsScreen(
                navController = navController,
                planId = id,
                viewModel = mainViewModel
            )
        }
    }
}