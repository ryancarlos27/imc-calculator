package br.com.ryan.imccalculator.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.ryan.imccalculator.ui.screens.DetailScreen
import br.com.ryan.imccalculator.ui.screens.HistoryScreen
import br.com.ryan.imccalculator.ui.screens.HomeScreen
import br.com.ryan.imccalculator.ui.screens.SettingsScreen
import br.com.ryan.imccalculator.ui.screens.HelpScreen

@Composable
fun AppNavHost() {
    val nav = rememberNavController()

    NavHost(
        navController = nav,
        startDestination = Route.Home.path
    ) {
        composable(Route.Home.path) {
            HomeScreen(
                onOpenHistory = { nav.navigate(Route.History.path) },
                onOpenSettings = { nav.navigate(Route.Settings.path) },
                onOpenHelp = { nav.navigate(Route.Help.path) }
            )
        }


        composable(Route.History.path) {
            HistoryScreen(
                onBack = { nav.popBackStack() },
                onOpenDetail = { id -> nav.navigate(Route.Detail.create(id)) }
            )
        }

        composable(Route.Settings.path) { // NOVO
            SettingsScreen(onBack = { nav.popBackStack() })
        }

        composable(
            route = Route.Detail.path,
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStack ->
            val id = backStack.arguments?.getLong("id") ?: 0L
            DetailScreen(id = id, onBack = { nav.popBackStack() })
        }
        composable(Route.Help.path) {
            HelpScreen(onBack = { nav.popBackStack() })
        }

    }
}
