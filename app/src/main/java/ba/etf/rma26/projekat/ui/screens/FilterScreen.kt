package ba.etf.rma26.projekat.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import ba.etf.rma26.projekat.viewmodel.QuizFilter
import ba.etf.rma26.projekat.viewmodel.QuizViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(
    viewModel: QuizViewModel,
    onShowQuizzes: () -> Unit
) {
    var godinaExpanded by remember { mutableStateOf(false) }
    var predmetExpanded by remember { mutableStateOf(false) }
    var grupaExpanded by remember { mutableStateOf(false) }
    var filterExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(12.dp),
        verticalArrangement = Arrangement.Top
    ) {
        ExposedDropdownMenuBox(
            expanded = godinaExpanded,
            onExpandedChange = { godinaExpanded = !godinaExpanded },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("odabirGodina")
        ) {
            TextField(
                value = viewModel.selectedYear,
                onValueChange = {},
                readOnly = true,
                label = { Text("Odabir godine") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = godinaExpanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = godinaExpanded,
                onDismissRequest = { godinaExpanded = false }
            ) {
                viewModel.availableYears.forEach { year ->
                    DropdownMenuItem(
                        text = { Text(year) },
                        onClick = {
                            viewModel.onYearSelected(year)
                            godinaExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        ExposedDropdownMenuBox(
            expanded = predmetExpanded,
            onExpandedChange = { predmetExpanded = !predmetExpanded },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("odabirPredmet")
        ) {
            TextField(
                value = viewModel.selectedSubject,
                onValueChange = {},
                readOnly = true,
                label = { Text("Odabir predmeta") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = predmetExpanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = predmetExpanded,
                onDismissRequest = { predmetExpanded = false }
            ) {
                viewModel.availableSubjects.forEach { subject ->
                    DropdownMenuItem(
                        text = { Text(subject) },
                        onClick = {
                            viewModel.onSubjectSelected(subject)
                            predmetExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        ExposedDropdownMenuBox(
            expanded = grupaExpanded,
            onExpandedChange = { grupaExpanded = !grupaExpanded },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("odabirGrupa")
        ) {
            TextField(
                value = viewModel.selectedGroup,
                onValueChange = {},
                readOnly = true,
                label = { Text("Odabir grupe") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = grupaExpanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = grupaExpanded,
                onDismissRequest = { grupaExpanded = false }
            ) {
                viewModel.availableGroups.forEach { group ->
                    DropdownMenuItem(
                        text = { Text(group) },
                        onClick = {
                            viewModel.onGroupSelected(group)
                            grupaExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { viewModel.enrollSelectedSubject() },
            enabled = viewModel.isEnrollEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("dodajPredmetDugme")
        ) {
            Text("Upiši me")
        }

        Spacer(modifier = Modifier.height(18.dp))
        Text("Filteri")
        Spacer(modifier = Modifier.height(8.dp))

        ExposedDropdownMenuBox(
            expanded = filterExpanded,
            onExpandedChange = { filterExpanded = !filterExpanded },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("filterKvizova")
        ) {
            TextField(
                value = viewModel.selectedFilter.label,
                onValueChange = {},
                readOnly = true,
                label = { Text("Filter kvizova") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = filterExpanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = filterExpanded,
                onDismissRequest = { filterExpanded = false }
            ) {
                QuizFilter.entries.forEach { filter ->
                    DropdownMenuItem(
                        text = { Text(filter.label) },
                        onClick = {
                            viewModel.onFilterSelected(filter)
                            filterExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Pronađeno je ${viewModel.getFilteredCount()} kvizova",
            modifier = Modifier.testTag("brojKvizova")
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onShowQuizzes,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("prikaziKvizoveDugme")
        ) {
            Text("Prikaži kvizove")
        }
    }
}
