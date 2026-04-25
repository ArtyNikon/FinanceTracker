package com.example.financetracker.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination

@Composable
fun AppBottomBar(
    currentDestination: NavDestination?,
    onItemClick: (AppRoute) -> Unit
) {
    val items = listOf(
        BottomBarItem(AppRoute.Home, Icons.Default.Home),
        BottomBarItem(AppRoute.Goals, Icons.Default.Savings),
        BottomBarItem(AppRoute.Deposit, Icons.Default.AccountBalance),
        BottomBarItem(AppRoute.Cashback, Icons.Default.Percent),
        BottomBarItem(AppRoute.Profile, Icons.Default.Person)
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = item.route.isSelected(currentDestination),
                onClick = { onItemClick(item.route) },
                icon = { Icon(item.icon, contentDescription = null) }
            )
        }
    }
}