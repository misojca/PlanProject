package com.example.grupa3.ui.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.grupa3.data.PlanRepository
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

    private var currentPlanId: String? = null

    fun initPlan(planId: String?) {
        this.currentPlanId = planId

        val foundPlan = PlanRepository.getPlanById(planId)

        state = if (foundPlan != null) {
            PlanDetailsUiState.Success(plan = foundPlan)
        } else {
            PlanDetailsUiState.Error
        }
    }

    fun activatePlan() {
        currentPlanId?.let { id ->
            PlanRepository.updatePlanStatus(id, PlanStatus.ACTIVE)
            initPlan(id)
        }
    }

    fun completePlan() {
        currentPlanId?.let { id ->
            PlanRepository.updatePlanStatus(id, PlanStatus.COMPLETED)
            initPlan(id)
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