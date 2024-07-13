package com.hamza.test.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hamza.test.ui.feature.category_details.MedCategoryDetailsScreen
import com.hamza.test.ui.feature.category_details.MedCategoryDetailsViewModel
import com.hamza.test.ui.feature.categories.MainCategoriesScreen
import com.hamza.test.ui.feature.categories.MedCategoriesViewModel
import com.hamza.test.ui.NavigationKeys.Arg.Med_CATEGORY_ID
import com.hamza.test.ui.theme.ComposeSampleTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.receiveAsFlow


// Single Activity per app
@AndroidEntryPoint
class EntryPointActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeSampleTheme {
                MedApp()
            }
        }
    }

}

@Composable
private fun MedApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = NavigationKeys.Route.Med_CATEGORIES_LIST) {
        composable(route = NavigationKeys.Route.Med_CATEGORIES_LIST) {
            MedCategoriesDestination(navController)
        }
        composable(
            route = NavigationKeys.Route.Med_CATEGORY_DETAILS,
            arguments = listOf(navArgument(NavigationKeys.Arg.Med_CATEGORY_ID) {
                type = NavType.StringType
            })
        ) {
            MedCategoryDetailsDestination()
        }
    }
}

@Composable
private fun MedCategoriesDestination(navController: NavHostController) {
    val viewModel: MedCategoriesViewModel = hiltViewModel()
    MainCategoriesScreen(
        state = viewModel.state,
        effectFlow = viewModel.effects.receiveAsFlow(),
        onNavigationRequested = { itemId ->
            navController.navigate("${NavigationKeys.Route.Med_CATEGORIES_LIST}/${itemId}")
        })
}

@Composable
private fun MedCategoryDetailsDestination() {
    val viewModel: MedCategoryDetailsViewModel = hiltViewModel()
    MedCategoryDetailsScreen(viewModel.state)
}

object NavigationKeys {

    object Arg {
        const val Med_CATEGORY_ID = "MedCategoryName"
    }

    object Route {
        const val Med_CATEGORIES_LIST = "Med_categories_list"
        const val Med_CATEGORY_DETAILS = "$Med_CATEGORIES_LIST/{$Med_CATEGORY_ID}"
    }

}