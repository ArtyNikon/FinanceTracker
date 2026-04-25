package com.example.financetracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.financetracker.feature.cashback.ui.CashbackScreen
import com.example.financetracker.feature.deposit.ui.DepositScreen
import com.example.financetracker.feature.goals.ui.GoalsScreen
import com.example.financetracker.feature.home.ui.HomeScreen
import com.example.financetracker.feature.profile.ui.ProfileScreen
import com.example.financetracker.feature.wizard.ui.WizardFinishScreen
import com.example.financetracker.feature.wizard.ui.WizardQuestionScreen
import com.example.financetracker.feature.wizard.ui.WizardWelcomeScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppRoute.Home,
        modifier = modifier
    ) {
        composable<AppRoute.Home> {
            HomeScreen()
        }

        composable<AppRoute.Goals> {
            GoalsScreen()
        }

        composable<AppRoute.Deposit> {
            DepositScreen(
                onSetupRecommendationsClick = {
                    navController.navigate(DepositWizardGraph)
                }
            )
        }

        composable<AppRoute.Cashback> {
            CashbackScreen()
        }

        composable<AppRoute.Profile> {
            ProfileScreen()
        }

        navigation<DepositWizardGraph>(
            startDestination = DepositWizardRoute.Welcome
        ) {
            composable<DepositWizardRoute.Welcome> {
                WizardWelcomeScreen(
                    onNextClick = {
                        navController.navigate(DepositWizardRoute.Question)
                    },
                    onCloseClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable<DepositWizardRoute.Question> {
                WizardQuestionScreen(
                    onCloseClick = {
                        navController.popBackStack(AppRoute.Deposit, inclusive = false)
                    },
                    onFinishClick = {
                        navController.navigate(DepositWizardRoute.Finish)
                    }
                )
            }

            composable<DepositWizardRoute.Finish> {
                WizardFinishScreen(
                    onCloseClick = {
                        navController.popBackStack(AppRoute.Deposit, inclusive = false)
                    }
                )
            }
        }
    }
}