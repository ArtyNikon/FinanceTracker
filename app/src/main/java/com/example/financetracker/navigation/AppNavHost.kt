package com.example.financetracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.financetracker.feature.cashback.ui.CashbackScreen
import com.example.financetracker.feature.deposit.presentation.adddeposit.AddDepositBottomSheetViewModel
import com.example.financetracker.feature.deposit.presentation.depositDetail.DepositDetailScreen
import com.example.financetracker.feature.deposit.presentation.depositDetail.DepositDetailViewModel
import com.example.financetracker.feature.deposit.presentation.main.DepositScreen
import com.example.financetracker.feature.deposit.presentation.main.DepositViewModel
import com.example.financetracker.feature.goals.ui.GoalsScreen
import com.example.financetracker.feature.home.ui.HomeScreen
import com.example.financetracker.feature.profile.ui.ProfileScreen
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
            val depositViewModel: DepositViewModel = viewModel(
                factory = DepositViewModel.Factory
            )

            val addDepositBottomSheetViewModel: AddDepositBottomSheetViewModel = viewModel(
                factory = AddDepositBottomSheetViewModel.Factory
            )

            DepositScreen(
                onSetupRecommendationsClick = {
                    navController.navigate(DepositWizardGraph)
                },
                viewModel = depositViewModel,
                addDepositBottomSheetViewModel = addDepositBottomSheetViewModel,
                onDepositClick = { deposit ->
                    navController.navigate(AppRoute.DepositDetail(deposit.id))
                }
            )
        }

        composable<AppRoute.DepositDetail> { backStackEntry ->
            val route = backStackEntry.toRoute<AppRoute.DepositDetail>()
            val depositId = route.depositId

            val detailViewModel: DepositDetailViewModel = viewModel(
                factory = DepositDetailViewModel.Factory(id = depositId)
            )

            DepositDetailScreen(
                viewModel = detailViewModel,
                onBackClick = { navController.popBackStack() },
                onDeleteClick = {
                    detailViewModel.onDeleteClick(
                        onDeleted = {
                            navController.popBackStack()
                        }
                    )
                },
                onEditClick = {}
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
                        navController.popBackStack(AppRoute.Deposit, inclusive = false)
                    }
                )
            }
        }
    }
}