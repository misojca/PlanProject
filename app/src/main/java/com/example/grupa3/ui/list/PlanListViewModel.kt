package com.example.grupa3.ui.list

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grupa3.data.PlanRepository

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

    private var currentCategory: PlanCategory? = null

    private var canRefresh = false

    init {
        loadPlans()
    }

    fun loadPlans() {
        viewModelScope.launch {
            state = PlanListUiState.Loading
            delay(5000)
            canRefresh = true
            val allPlans = PlanRepository.getPlans()
            val filtered = if (currentCategory == null) allPlans else allPlans.filter { it.category == currentCategory }
            state = PlanListUiState.Success(filtered)
        }
    }

    fun filterByCategory(category: PlanCategory?) {
        currentCategory = category
        val allPlans = PlanRepository.getPlans()
        val filtered = if (category == null) allPlans else allPlans.filter { it.category == category }
            state = PlanListUiState.Success(filtered)
    }

    fun refreshList() {
        if (canRefresh) {
            filterByCategory(currentCategory)
        }
    }
}