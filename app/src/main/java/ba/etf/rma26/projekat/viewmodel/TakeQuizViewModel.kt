package ba.etf.rma26.projekat.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ba.etf.rma26.projekat.data.models.KvizTaken
import ba.etf.rma26.projekat.data.models.Pitanje
import ba.etf.rma26.projekat.data.repositories.OdgovorRepository
import ba.etf.rma26.projekat.data.repositories.PitanjeKvizRepository
import ba.etf.rma26.projekat.data.repositories.TakeKvizRepository
import kotlinx.coroutines.launch

class TakeQuizViewModel : ViewModel() {
    var pitanja by mutableStateOf(listOf<Pitanje>())
        private set
    var trenutnoPitanjeIndex by mutableStateOf(0)
        private set
    var kvizTaken by mutableStateOf<KvizTaken?>(null)
        private set
    var odabraniOdgovorIndex by mutableStateOf<Int?>(null)
        private set
    var osvojeniBodoviRezultat by mutableStateOf<Int?>(null)
        private set
    var ucitavanje by mutableStateOf(false)
        private set
    fun pokreniIInicijalizirajKviz(kvizId: Int) {
        if (kvizTaken != null || ucitavanje) return

        ucitavanje = true
        viewModelScope.launch {
            val pokrenutPokusaj = TakeKvizRepository.zapocniKviz(kvizId)
            kvizTaken = pokrenutPokusaj

            if (pokrenutPokusaj != null) {
                pitanja = PitanjeKvizRepository.getPitanja(kvizId)
            }
            ucitavanje = false
        }
    }
    fun onOdaberiOdgovor(index: Int) {
        odabraniOdgovorIndex = index
    }
    fun odgovoriNaTrenutnoPitanje() {
        val pokusajId = kvizTaken?.id ?: return
        val trenutnoPitanje = pitanja.getOrNull(trenutnoPitanjeIndex) ?: return
        val odabir = odabraniOdgovorIndex ?: return

        viewModelScope.launch {
            val trenutniBodovi = OdgovorRepository.postaviOdgovorKviz(
                idKvizTaken = pokusajId,
                idPitanje = trenutnoPitanje.id,
                odgovor = odabir
            )
            if (trenutnoPitanjeIndex >= pitanja.size - 1) {
                osvojeniBodoviRezultat = trenutniBodovi
            } else {
                trenutnoPitanjeIndex++
                odabraniOdgovorIndex = null
            }
        }
    }
}