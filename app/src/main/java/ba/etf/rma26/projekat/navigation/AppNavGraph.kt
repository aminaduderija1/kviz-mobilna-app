package ba.etf.rma26.projekat.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ba.etf.rma26.projekat.ui.screens.FilterScreen
import ba.etf.rma26.projekat.ui.screens.KvizoviScreen
import ba.etf.rma26.projekat.viewmodel.QuizViewModel


private object Destinations {
    const val FILTER = "filter"
    const val QUIZZES = "kvizovi"
}

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val quizViewModel: QuizViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Destinations.FILTER
    ) {
        composable(Destinations.FILTER) {
            FilterScreen(
                viewModel = quizViewModel,
                onShowQuizzes = { navController.navigate(Destinations.QUIZZES) }
            )
        }
        composable(Destinations.QUIZZES) {
            KvizoviScreen(
                viewModel = quizViewModel,
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}
