package com.example.financetracker.app

import android.app.Application
import com.example.financetracker.core.di.FinanceTrackerContainer

class FinanceTrackerApplication : Application() {
    lateinit var financeTrackerContainer: FinanceTrackerContainer
        private set

    override fun onCreate() {
        super.onCreate()

        financeTrackerContainer = FinanceTrackerContainer(
            context = applicationContext
        )
    }
}