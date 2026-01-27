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
    ),
    Plan(
        id = "6",
        title = "Client Presentation",
        description = "Present the final UI/UX designs",
        category = PlanCategory.WORK,
        status = PlanStatus.PLANNED
    ),
    Plan(
        id = "7",
        title = "Dentist Appointment",
        description = "Regular dental check-up and cleaning",
        category = PlanCategory.PERSONAL,
        status = PlanStatus.ACTIVE
    ),
    Plan(
        id = "8",
        title = "Gym Workout",
        description = "Heavy lifting session focusing on chest and triceps",
        category = PlanCategory.HEALTH,
        status = PlanStatus.ACTIVE
    ),
    Plan(
        id = "9",
        title = "Reading Session",
        description = "Read 50 pages of the favorite book",
        category = PlanCategory.PERSONAL,
        status = PlanStatus.PLANNED
    ),
    Plan(
        id = "10",
        title = "Bug Fixing",
        description = "Resolve high-priority crashes ",
        category = PlanCategory.WORK,
        status = PlanStatus.ACTIVE
    ),
    Plan(
        id = "11",
        title = "Meal Prep",
        description = "Prepare healthy lunches for the next three work days",
        category = PlanCategory.HEALTH,
        status = PlanStatus.COMPLETED
    ),
    Plan(
        id = "12",
        title = "Car Wash",
        description = "Full exterior and interior cleaning of the car",
        category = PlanCategory.PERSONAL,
        status = PlanStatus.COMPLETED
    ),
    Plan(
        id = "13",
        title = "Meditation",
        description = "10 minutes of guided meditation",
        category = PlanCategory.HEALTH,
        status = PlanStatus.PLANNED
    ),
    Plan(
        id = "14",
        title = "Database Backup",
        description = "Perform a manual backup of the production database",
        category = PlanCategory.WORK,
        status = PlanStatus.COMPLETED
    ),
    Plan(
        id = "15",
        title = "House Cleaning",
        description = "Vacuum the carpets and dust the shelves in the living room",
        category = PlanCategory.PERSONAL,
        status = PlanStatus.PLANNED
    )
)