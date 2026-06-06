package ba.etf.rma26.projekat.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import ba.etf.rma26.projekat.model.Kviz
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.dp

@Composable
fun ShowKviz(
    kvizovi : List<Kviz>,
    modifier: Modifier = Modifier
) {
    val sortiraniKvizovi = kvizovi.sortedBy { it.datumPocetak }
    var listapo2 = mutableListOf<List<Kviz>>()
    var listaPrivremena = mutableListOf<Kviz>()
    for (kviz in sortiraniKvizovi) {
        listaPrivremena.add(kviz)
        if (listaPrivremena.size == 2) {
            listapo2.add(listaPrivremena)
            listaPrivremena = mutableListOf()
        }
    }
    if (listaPrivremena.isNotEmpty()) {
        listapo2.add(listaPrivremena)
    }

    LazyColumn(
        modifier = modifier
            .padding(10.dp)
            .fillMaxSize()
            .testTag("listaKvizova")
    ) {
        items(listapo2) { dvije ->
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    KvizCard(kviz = dvije[0])
                }
                if (dvije.size > 1) {
                    Box(modifier = Modifier.weight(1f)) {
                        KvizCard(kviz = dvije[1])
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}