package com.example.grupa3.data

import com.example.grupa3.model.Plan
import com.example.grupa3.model.PlanCategory
import com.example.grupa3.model.PlanStatus

val mockPlans = listOf(
    Plan(
        id = "1",
        title = "Morning Run",
        description = "Run in the park",
        category = PlanCategory.HEALTH,
        status = PlanStatus.PLANNED
    ),
    Plan(
        id = "2",
        title = "Team Meeting",
        description = "Weekly team sync",
        category = PlanCategory.WORK,
        status = PlanStatus.ACTIVE
    ),
    Plan(
        id = "3",
        title = "Grocery Shopping",
        description = "Buy food for the week",
        category = PlanCategory.PERSONAL,
        status = PlanStatus.PLANNED
    ),
    Plan(
        id = "4",
        title = "Code Review",
        description = "Handle CX integration",
        category = PlanCategory.WORK,
        status = PlanStatus.COMPLETED
    ),
    Plan(
        id = "5",
        title = "Yoga Session",
        description = "Evening yoga",
        category = PlanCategory.HEALTH,
        status = PlanStatus.PLANNED
    )
)