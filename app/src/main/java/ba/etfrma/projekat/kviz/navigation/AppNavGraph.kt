package ba.etfrma.projekat.kviz.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ba.etfrma.projekat.kviz.ui.screens.FilterScreen
import ba.etfrma.projekat.kviz.ui.screens.KvizoviScreen
import ba.etfrma.projekat.kviz.viewmodel.QuizViewModel


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
