package com.example.grupa3.navigation

sealed class Screen (val route: String) {
    object PlanListScreen: Screen("plan_list")
    object PlanDetailsScreen: Screen("plan_details")

    fun appendId(vararg args: String) : String {
        return buildString {
            append(route)
            args.forEach { args -> append("/$args") }
        }
    }
}