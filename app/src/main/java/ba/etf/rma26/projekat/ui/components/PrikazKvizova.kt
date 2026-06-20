package ba.etf.rma26.projekat.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import ba.etf.rma26.projekat.data.models.Kviz

@Composable
fun ShowKviz(
    kvizovi: List<Kviz>,
    onKvizKlik: (Int, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val sortiraniKvizovi = kvizovi.sortedBy { it.datumPocetka }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .padding(10.dp)
            .fillMaxSize()
            .testTag("listaKvizova"),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(sortiraniKvizovi) { kviz ->
            KvizCard(
                kviz = kviz,
                modifier = Modifier.clickable { onKvizKlik(kviz.id, kviz.naziv) }
            )
        }
    }
}