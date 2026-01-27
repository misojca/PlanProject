package com.example.grupa3.data

import androidx.compose.runtime.mutableStateListOf
import com.example.grupa3.model.Plan
import com.example.grupa3.model.PlanStatus

object PlanRepository {

    private val _plans = mutableStateListOf<Plan>().apply {
        addAll(mockPlans)
    }

    fun getPlans(): List<Plan> = _plans

    fun getPlanById(id: String?): Plan? =
        _plans.find { it.id == id }


    fun updatePlanStatus(id: String?, newStatus: PlanStatus) {
        val index = _plans.indexOfFirst { it.id == id }
        if (index != -1) {
            _plans[index] = _plans[index].copy(status = newStatus)
        }
    }
}