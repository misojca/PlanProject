package com.example.grupa3.model

data class Plan(
    val id: String,
    val title: String,
    val description: String,
    val category: PlanCategory,
    val status: PlanStatus
)
enum class PlanCategory {
    WORK, PERSONAL, HEALTH
}
enum class PlanStatus {
    PLANNED, ACTIVE, COMPLETED
}