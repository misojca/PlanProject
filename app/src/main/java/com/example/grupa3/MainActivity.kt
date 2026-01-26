package com.example.grupa3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels

import com.example.grupa3.navigation.Navigation
import com.example.grupa3.ui.list.PlanListViewModel


class MainActivity : ComponentActivity() {
    private val mainViewModel: PlanListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Navigation(mainViewModel)
        }
    }
}


