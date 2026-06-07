package ba.etf.rma26.projekat.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ba.etf.rma26.projekat.ui.screens.FilterScreen
import ba.etf.rma26.projekat.ui.screens.KvizoviScreen
import ba.etf.rma26.projekat.ui.screens.TakeQuizScreen
import ba.etf.rma26.projekat.viewmodel.QuizViewModel
import ba.etf.rma26.projekat.viewmodel.TakeQuizViewModel

private object Destinations {
    const val FILTER = "filter"
    const val QUIZZES = "kvizovi"
    const val TAKE_QUIZ = "take_quiz"
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
                onNavigateBack = { navController.navigateUp() },
                onKvizKlik = { id, naziv ->
                    navController.navigate("${Destinations.TAKE_QUIZ}/$id/$naziv")
                }
            )
        }
        composable(
            route = "${Destinations.TAKE_QUIZ}/{kvizId}/{naziv}",
            arguments = listOf(
                navArgument("kvizId") { type = NavType.IntType },
                navArgument("naziv") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val kvizId = backStackEntry.arguments?.getInt("kvizId") ?: 0
            val naziv = backStackEntry.arguments?.getString("naziv") ?: ""

            val takeQuizViewModel: TakeQuizViewModel = viewModel()

            TakeQuizScreen(
                kvizId = kvizId,
                nazivKviza = naziv,
                viewModel = takeQuizViewModel,
                onNazadKlik = {
                    quizViewModel.osvjeziSvePodatke()
                    navController.popBackStack()
                }
            )
        }
    }
}