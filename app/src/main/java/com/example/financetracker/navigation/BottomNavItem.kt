package com.example.financetracker.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy

class BottomBarItem(
    val route: AppRoute,
    val icon: ImageVector
)

fun AppRoute.isSelected(currentDestination: NavDestination?): Boolean {
    return when (this) {
        AppRoute.Home ->
            currentDestination?.hierarchy?.any { it.hasRoute<AppRoute.Home>() } == true
        AppRoute.Goals ->
            currentDestination?.hierarchy?.any { it.hasRoute<AppRoute.Goals>() } == true
        AppRoute.Deposit ->
            currentDestination?.hierarchy?.any { it.hasRoute<AppRoute.Deposit>() } == true
        AppRoute.Cashback ->
            currentDestination?.hierarchy?.any { it.hasRoute<AppRoute.Cashback>() } == true
        AppRoute.Profile ->
            currentDestination?.hierarchy?.any { it.hasRoute<AppRoute.Profile>() } == true
    }
}