package com.example.grupa3.ui.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.grupa3.data.mockPlans
import com.example.grupa3.model.Plan
import com.example.grupa3.model.PlanStatus

sealed interface PlanDetailsUiState {
    object Loading : PlanDetailsUiState
    data class Success(
        val plan: Plan,
        val permissionMessage: String = ""
    ) : PlanDetailsUiState
    object Error : PlanDetailsUiState
}

class PlanDetailsViewModel : ViewModel() {

    var state by mutableStateOf<PlanDetailsUiState>(PlanDetailsUiState.Loading)
        private set

    fun initPlan(planId: String?) {
        val foundPlan = mockPlans.find { it.id == planId }
        state = if (foundPlan != null) {
            PlanDetailsUiState.Success(plan = foundPlan)
        } else {
            PlanDetailsUiState.Error
        }
    }

    fun activatePlan() {
        val currentState = state
        if (currentState is PlanDetailsUiState.Success) {
            val updatedPlan = currentState.plan.copy(status = PlanStatus.ACTIVE)
            state = currentState.copy(plan = updatedPlan)
        }
    }

    fun completePlan() {
        val currentState = state
        if (currentState is PlanDetailsUiState.Success) {
            val updatedPlan = currentState.plan.copy(status = PlanStatus.COMPLETED)
            state = currentState.copy(plan = updatedPlan)
        }
    }

    fun onPermissionResult(isGranted: Boolean) {
        val currentState = state
        if (currentState is PlanDetailsUiState.Success) {
            val msg = if (isGranted) "Location allowed" else "Location denied"
            state = currentState.copy(permissionMessage = msg)
        }
    }
}