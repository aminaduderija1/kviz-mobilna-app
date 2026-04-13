package ba.etfrma.projekat.kviz.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.platform.testTag
import ba.etfrma.projekat.kviz.data.KvizStaticData
import ba.etfrma.projekat.kviz.model.Kviz

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenKvizovi() {
    var otvoreno by remember { mutableStateOf(false) }
    var seketovanaOpcija by remember { mutableStateOf("Svi moji kvizovi") }
    var updateTrigger by remember { mutableStateOf(0) }
    val kvizoviZaPrikaz = remember(seketovanaOpcija, updateTrigger) {
        when(seketovanaOpcija) {
            "Svi moji kvizovi" -> KvizStaticData.getUpisani()
            "Svi kvizovi" -> KvizStaticData.getAll()
            "Uradeni kvizovi" -> KvizStaticData.getDone()
            "Buduci kvizovi" -> KvizStaticData.getFuture()
            else -> KvizStaticData.getNotTaken()
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        ExposedDropdownMenuBox(
            expanded = otvoreno,
            onExpandedChange = { otvoreno = !otvoreno },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("filterKvizova")
        ) {
            TextField(
                value = seketovanaOpcija,
                onValueChange = {},
                readOnly = true,
                label = { Text("Filter kvizova") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = otvoreno) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = otvoreno,
                onDismissRequest = { otvoreno = false }
            ) {
                val opcije = listOf(
                    "Svi moji kvizovi", "Svi kvizovi", "Uradeni kvizovi",
                    "Buduci kvizovi", "Prosli kvizovi"
                )
                opcije.forEach { opcija ->
                    DropdownMenuItem(
                        text = { Text(opcija) },
                        onClick = {
                            seketovanaOpcija = opcija
                            otvoreno = false
                        }
                    )
                }

            }
        }
        UnosKvizovi(onUpisano = { updateTrigger++ })
        Box(modifier = Modifier.weight(1f)) {
            ShowKviz(kvizovi = kvizoviZaPrikaz)
        }
    }
}