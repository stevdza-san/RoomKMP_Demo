package com.stevdza_san.demo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.stevdza_san.demo.presentation.screen.details.DetailsScreen
import com.stevdza_san.demo.presentation.screen.home.HomeScreen
import com.stevdza_san.demo.presentation.screen.manage.ManageScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                onBookSelect = {
                    navController.navigate(Screen.Details.passId(it))
                },
                onCreateClick = {
                    navController.navigate(Screen.Manage.passId())
                }
            )
        }
        composable(
            route = Screen.Manage.route,
            arguments = listOf(
                navArgument(
                    name = BOOK_ID_ARG
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            val id = it.arguments?.getInt(BOOK_ID_ARG) ?: -1
            ManageScreen(
                id = id,
                onBackClick = { navController.navigateUp() }
            )
        }
        composable(
            route = Screen.Details.route,
            arguments = listOf(
                navArgument(
                    name = BOOK_ID_ARG
                ) {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) {
            val id = it.arguments?.getInt(BOOK_ID_ARG) ?: 0
            DetailsScreen(
                onEditClick = {
                    navController.navigate(Screen.Manage.passId(id))
                },
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}