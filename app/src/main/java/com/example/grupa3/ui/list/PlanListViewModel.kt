package com.example.grupa3.ui.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grupa3.data.mockPlans
import com.example.grupa3.model.Plan
import com.example.grupa3.model.PlanCategory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.grupa3.model.PlanStatus

sealed interface PlanListUiState {
    object Loading : PlanListUiState
    data class Success(
        val plans: List<Plan>,
        val selectedCategory: PlanCategory? = null
    ) : PlanListUiState
    data class Error(val message: String) : PlanListUiState
}

class PlanListViewModel : ViewModel() {
    private var allPlans = mutableStateListOf<Plan>()

    var state by mutableStateOf<PlanListUiState>(PlanListUiState.Loading)
        private set

    init {
        loadPlans()
    }

    private fun loadPlans() {
        viewModelScope.launch {
            delay(2000)
            allPlans.clear()
            allPlans.addAll(mockPlans)

            state = PlanListUiState.Success(allPlans)
        }
    }

    fun filterByCategory(category: PlanCategory?) {
        val filteredList = if (category == null) allPlans
        else allPlans.filter { it.category == category }

        state = PlanListUiState.Success(filteredList, category)
    }

    fun updatePlanStatus(planId: String, newStatus: PlanStatus) {
        val index = allPlans.indexOfFirst { it.id == planId }
        if (index != -1) {
            val old = allPlans[index]
            allPlans[index] = old.copy(status = newStatus)

            val currentCategory =
                (state as? PlanListUiState.Success)?.selectedCategory

            filterByCategory(currentCategory)
        }
    }

    fun getPlanById(id: String): Plan? {
        return allPlans.find { it.id == id }
    }
}