package com.example.grupa3.ui.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grupa3.data.mockPlans
import com.example.grupa3.model.Plan
import com.example.grupa3.model.PlanCategory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed interface PlanListUiState {
    object Loading : PlanListUiState
    data class Success(
        val plans: List<Plan>,
        val selectedCategory: PlanCategory? = null
    ) : PlanListUiState
    data class Error(val message: String) : PlanListUiState
}

class PlanListViewModel : ViewModel() {

    var state by mutableStateOf<PlanListUiState>(PlanListUiState.Loading)
        private set

    init {
        loadPlans()
    }

    private fun loadPlans() {
        viewModelScope.launch {
            delay(2000)
            state = PlanListUiState.Success(mockPlans)
        }
    }

    fun filterByCategory(category: PlanCategory?) {
        val filteredList = if (category == null) mockPlans
        else mockPlans.filter { it.category == category }

        state = PlanListUiState.Success(filteredList, category)
    }
}