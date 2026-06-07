package ba.etf.rma26.projekat.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ba.etf.rma26.projekat.data.models.Grupa
import ba.etf.rma26.projekat.data.models.Kviz
import ba.etf.rma26.projekat.data.models.Predmet
import ba.etf.rma26.projekat.data.repositories.AccountRepository
import ba.etf.rma26.projekat.data.repositories.KvizRepository
import ba.etf.rma26.projekat.data.repositories.PredmetIGrupaRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDateTime


enum class QuizFilter(val label: String) {
    MY("Svi moji kvizovi"),
    ALL("Svi kvizovi"),
    DONE("Urađeni kvizovi"),
    FUTURE("Budući kvizovi"),
    PAST("Prošli kvizovi")
}

class QuizViewModel : ViewModel() {
    var odabranaGodina by mutableStateOf("")
        private set
    var odabraniPredmet by mutableStateOf("")
        private set
    var odabranaGrupa by mutableStateOf("")
        private set
    var odabraniFilter by mutableStateOf(QuizFilter.MY)
        private set

    private var sviPredmeti by mutableStateOf(listOf<Predmet>())
    private var sveGrupeZaOdabraniPredmet by mutableStateOf(listOf<Grupa>())

    private var sveGrupe by mutableStateOf(listOf<Grupa>())
    private var upisaneGrupe by mutableStateOf(listOf<Grupa>())

    private var sviKvizovi by mutableStateOf(listOf<Kviz>())
    private var upisaniKvizovi by mutableStateOf(listOf<Kviz>())

    val dostupneGodine = listOf("1", "2", "3", "4", "5")

    val dostupniPredmeti: List<String>
        get() {
            if (odabranaGodina.isBlank()) return emptyList()
            val godina = odabranaGodina.toIntOrNull() ?: return emptyList()
            val upisaniPredmetiIds = upisaneGrupe.map { it.idPredmeta }

            return sviPredmeti
                .filter { it.godina == godina && it.id !in upisaniPredmetiIds }
                .map { it.naziv }
        }

    val dostupneGrupe: List<String>
        get() {
            if (odabraniPredmet.isBlank()) return emptyList()
            return sveGrupeZaOdabraniPredmet.map { it.naziv }
        }

    val daLiJeUpisDostupan: Boolean
        get() = odabranaGodina.isNotBlank() && odabraniPredmet.isNotBlank() && odabranaGrupa.isNotBlank()

    init {
        osvjeziSvePodatke()
    }

    fun osvjeziSvePodatke() {
        viewModelScope.launch {
            sviPredmeti = PredmetIGrupaRepository.getPredmeti()
            sveGrupe = PredmetIGrupaRepository.getGrupe()
            upisaneGrupe = PredmetIGrupaRepository.getUpisaneGrupe()
            sviKvizovi = KvizRepository.getAll()
            upisaniKvizovi = KvizRepository.getUpisani()
        }
    }
    init {
        viewModelScope.launch {
            AccountRepository.studentHash.collectLatest { noviHash ->
                osvjeziSvePodatke()
            }
        }
    }

    fun onOdabranaGodina(year: String) {
        odabranaGodina = year
        odabraniPredmet = ""
        odabranaGrupa = ""
        sveGrupeZaOdabraniPredmet = emptyList()
    }

    fun onPredmetOdabran(subject: String) {
        odabraniPredmet = subject
        odabranaGrupa = ""
        val predmetObjekat = sviPredmeti.find { it.naziv == subject }
        if (predmetObjekat != null) {
            viewModelScope.launch {
                sveGrupeZaOdabraniPredmet = PredmetIGrupaRepository.getGrupeZaPredmet(predmetObjekat.id)
            }
        } else {
            sveGrupeZaOdabraniPredmet = emptyList()
        }
    }

    fun onGroupaOdabrana(group: String) {
        odabranaGrupa = group
    }

    fun onFilterOdabran(filter: QuizFilter) {
        odabraniFilter = filter
    }

    fun upisiPredmet() {
        val grupaObjekt = sveGrupeZaOdabraniPredmet.find { it.naziv == odabranaGrupa } ?: return

        viewModelScope.launch {
            val uspjeh = PredmetIGrupaRepository.upisiUGrupu(grupaObjekt.id)
            if (uspjeh) {
                odabranaGodina = ""
                odabraniPredmet = ""
                odabranaGrupa = ""
                sveGrupeZaOdabraniPredmet = emptyList()

                sviPredmeti = PredmetIGrupaRepository.getPredmeti()
                sveGrupe = PredmetIGrupaRepository.getGrupe()
                upisaneGrupe = PredmetIGrupaRepository.getUpisaneGrupe()
                sviKvizovi = KvizRepository.getAll()
                upisaniKvizovi = KvizRepository.getUpisani()
            }
        }
    }


    fun getFilterKviz(): List<Kviz> {
        val referentniDatum = LocalDateTime.of(2021, 5, 1, 0, 0)
        return when (odabraniFilter) {
            QuizFilter.ALL -> sviKvizovi
            QuizFilter.MY -> upisaniKvizovi

            QuizFilter.DONE -> upisaniKvizovi.filter { kviz ->
                kviz.datumRada != null || kviz.osvojeniBodovi != null
            }
            QuizFilter.FUTURE -> upisaniKvizovi.filter { kviz ->
                val nijeUraden = kviz.datumRada == null && kviz.osvojeniBodovi == null
                val pocetak = kviz.datumPocetka ?: referentniDatum
                nijeUraden && pocetak.isAfter(referentniDatum)
            }

            QuizFilter.PAST -> upisaniKvizovi.filter { kviz ->
                val nijeUraden = kviz.datumRada == null && kviz.osvojeniBodovi == null
                val kraj = kviz.datumkraj ?: referentniDatum
                nijeUraden && kraj.isBefore(referentniDatum)
            }
        }
    }

    fun getFilteredCount(): Int = getFilterKviz().size
}