package ba.etf.rma26.projekat.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ba.etf.rma26.projekat.viewmodel.TakeQuizViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TakeQuizScreen(
    kvizId: Int,
    nazivKviza: String,
    onNazadKlik: () -> Unit,
    viewModel: TakeQuizViewModel = viewModel()
) {
    LaunchedEffect(kvizId) {
        viewModel.pokreniIInicijalizirajKviz(kvizId)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = nazivKviza) })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (viewModel.ucitavanje) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (viewModel.osvojeniBodoviRezultat != null) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Kviz Zavrsen!", fontSize = 26.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Ukupno bodova: ${viewModel.osvojeniBodoviRezultat}",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(onClick = onNazadKlik) {
                        Text("Zatvori kviz")
                    }
                }
            } else if (viewModel.pitanja.isNotEmpty()) {
                val trenutnoPitanje = viewModel.pitanja[viewModel.trenutnoPitanjeIndex]

                Column(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "Pitanje ${viewModel.trenutnoPitanjeIndex + 1} od ${viewModel.pitanja.size}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = trenutnoPitanje.tekstPitanja,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        itemsIndexed(trenutnoPitanje.odgovori) { index, opcija ->
                            val jeOdabran = viewModel.odabraniOdgovorIndex == index
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = if (jeOdabran) MaterialTheme.colorScheme.primaryContainer
                                    else MaterialTheme.colorScheme.surfaceVariant
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { viewModel.onOdaberiOdgovor(index) }
                            ) {
                                Row(
                                    modifier = Modifier.padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = jeOdabran,
                                        onClick = { viewModel.onOdaberiOdgovor(index) }
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = opcija, fontSize = 16.sp)
                                }
                            }
                        }
                    }

                    Button(
                        onClick = { viewModel.odgovoriNaTrenutnoPitanje() },
                        enabled = viewModel.odabraniOdgovorIndex != null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        val tekstDugmeta = if (viewModel.trenutnoPitanjeIndex == viewModel.pitanja.size - 1)
                            "Zavrsi kviz i posalji" else "Sljedece pitanje"
                        Text(tekstDugmeta)
                    }
                }
            } else {
                Text(
                    text = "Greska pri ucitavanju.",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}