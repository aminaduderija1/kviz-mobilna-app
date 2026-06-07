package ba.etf.rma26.projekat.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import ba.etf.rma26.projekat.ui.components.ShowKviz
import ba.etf.rma26.projekat.viewmodel.QuizViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KvizoviScreen(
    viewModel: QuizViewModel,
    onNavigateBack: () -> Unit,
    onKvizKlik: (Int, String) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.osvjeziSvePodatke()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(viewModel.odabraniFilter.label) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        ShowKviz(
            kvizovi = viewModel.getFilterKviz(),
            onKvizKlik = onKvizKlik,
            modifier = Modifier.padding(paddingValues)
        )
    }
}
