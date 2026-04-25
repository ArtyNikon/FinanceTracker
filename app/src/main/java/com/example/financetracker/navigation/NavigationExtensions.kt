package com.example.financetracker.navigation

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy

fun NavDestination?.isTopLevelDestination(): Boolean {
    return this?.hierarchy?.any {
        it.hasRoute<AppRoute.Home>() ||
                it.hasRoute<AppRoute.Goals>() ||
                it.hasRoute<AppRoute.Deposit>() ||
                it.hasRoute<AppRoute.Cashback>() ||
                it.hasRoute<AppRoute.Profile>()
    } == true
}