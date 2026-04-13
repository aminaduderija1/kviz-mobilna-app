package ba.etfrma.projekat.kviz.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import ba.etfrma.projekat.kviz.data.GrupaStaticData
import ba.etfrma.projekat.kviz.data.PredmetStaticData
import ba.etfrma.projekat.kviz.model.Kviz
import ba.etfrma.projekat.kviz.model.Predmet
import ba.etfrma.projekat.kviz.ui.theme.ljubS1
import ba.etfrma.projekat.kviz.ui.theme.ljubS2
import ba.etfrma.projekat.kviz.ui.theme.ljubT1
import ba.etfrma.projekat.kviz.ui.theme.ljubT2
import ba.etfrma.projekat.kviz.ui.theme.ljubT3

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnosKvizovi(onUpisano: () -> Unit) {
    var otvorenoGodina by remember { mutableStateOf(false) }
    var seleketovanaGodina by rememberSaveable { mutableStateOf("") }

    var otvorenoPredmet by remember { mutableStateOf(false) }
    var seleketovaniPredmet by remember { mutableStateOf("") }

    var otvorenoGrupa by remember { mutableStateOf(false) }
    var seleketovanaGrupa by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = otvorenoGodina,
            onExpandedChange = { otvorenoGodina = !otvorenoGodina },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .testTag("odabirGodina")
        ) {
            TextField(
                value = seleketovanaGodina,
                onValueChange = {},
                readOnly = true,
                label = { Text("Odabir godine") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = otvorenoGodina) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = otvorenoGodina,
                onDismissRequest = { otvorenoGodina = false }
            ) {
                val opcijeGodina = listOf(
                    "1", "2", "3", "4", "5"
                )
                opcijeGodina.forEach { opcija ->
                    DropdownMenuItem(
                        text = { Text(opcija) },
                        onClick = {
                            seleketovanaGodina = opcija
                            seleketovanaGrupa = ""
                            seleketovaniPredmet = ""
                            otvorenoGodina = false
                        }
                    )
                }
            }
        }
        ExposedDropdownMenuBox(
            expanded = otvorenoPredmet,
            onExpandedChange = { otvorenoPredmet = !otvorenoPredmet },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .testTag("odabirPredmet")
        ) {
            TextField(
                value = seleketovaniPredmet,
                onValueChange = {},
                readOnly = true,
                label = { Text("Odabir predmeta") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = otvorenoPredmet) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = otvorenoPredmet,
                onDismissRequest = { otvorenoPredmet = false }
            ) {
                val upisaniNazivi = PredmetStaticData.getUpisani().map { it.naziv }
                val opcijePredmet = if (seleketovanaGodina.isEmpty()) emptyList()
                else PredmetStaticData.getAll().filter {
                    it.godina == seleketovanaGodina.toInt() && it.naziv !in upisaniNazivi
                }
                opcijePredmet.forEach { predmet ->
                    DropdownMenuItem(
                        text = { Text(predmet.naziv) },
                        onClick = {
                            seleketovaniPredmet = predmet.naziv
                            seleketovanaGrupa = ""
                            otvorenoPredmet = false
                        }
                    )
                }
            }
        }
        ExposedDropdownMenuBox(
            expanded = otvorenoGrupa,
            onExpandedChange = { otvorenoGrupa = !otvorenoGrupa },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .testTag("odabirGrupa")
        ) {
            TextField(
                value = seleketovanaGrupa,
                onValueChange = {},
                readOnly = true,
                label = { Text("Odabir grupe") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = otvorenoGrupa) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = otvorenoGrupa,
                onDismissRequest = { otvorenoGrupa = false }
            ) {
                val opcijeGrupa = GrupaStaticData.getGrupaFromPredmet(seleketovaniPredmet)

                opcijeGrupa.forEach { grupa ->
                    DropdownMenuItem(
                        text = { Text(grupa.naziv) },
                        onClick = {
                            seleketovanaGrupa = grupa.naziv
                            otvorenoGrupa = false
                        }
                    )
                }
            }
        }
        Button(
            onClick = {
                PredmetStaticData.upis(seleketovaniPredmet)
                onUpisano()
                seleketovaniPredmet = ""
                seleketovanaGrupa = ""
                seleketovanaGodina = ""
            },colors = ButtonDefaults.buttonColors(
                containerColor = ljubT1,      // Boja pozadine dugmeta
                contentColor = Color.White,    // Boja teksta i ikone unutar dugmeta
                disabledContainerColor = ljubS2, // Boja kada je dugme onemogućeno
                disabledContentColor = ljubT1  // Boja teksta kada je onemogućeno
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("dodajPredmetDugme"),
            enabled = seleketovanaGodina.isNotEmpty() &&
                    seleketovaniPredmet.isNotEmpty() &&
                    seleketovanaGrupa.isNotEmpty()
        ) {
            Text(text = "Upiši me")
        }
    }

}
