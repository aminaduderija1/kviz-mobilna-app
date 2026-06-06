package ba.etf.rma26.projekat.data

import ba.etf.rma26.projekat.model.Kviz
import java.time.LocalDateTime
import kotlin.collections.filter

object KvizStaticData {
    fun getReferentDate() : LocalDateTime {
        return LocalDateTime.of(2026, 4, 10, 0, 0)
    }

    fun getAll(): List<Kviz> {
        return listOf(
            Kviz(
                naziv = "Kviz 1", nazivPredmeta = "RPR",
                datumPocetak = LocalDateTime.of(2026, 4, 1, 12, 0),
                datumKraj = LocalDateTime.of(2026, 4, 3, 23, 59),
                datumRada = LocalDateTime.of(2026, 4, 2, 15, 0), // Ima datum rada
                trajanje = 5,
                nazivGrupe = "G1",
                osvojeniBodovi = 10.0f
            ),
            Kviz(
                naziv = "Kviz 2", nazivPredmeta = "RPR",
                datumPocetak = LocalDateTime.of(2026, 4, 8, 12, 0), // Počeo pre 10.4.
                datumKraj = LocalDateTime.of(2026, 4, 12, 23, 59),  // Završava posle 10.4.
                datumRada = null,
                trajanje = 5,
                nazivGrupe = "G1",
                osvojeniBodovi = null
            ),
            Kviz(
                naziv = "Kviz 3", nazivPredmeta = "RPR",
                datumPocetak = LocalDateTime.of(2026, 4, 15, 12, 0), // Počinje posle 10.4.
                datumKraj = LocalDateTime.of(2026, 4, 16, 23, 59),
                datumRada = null,
                trajanje = 5,
                nazivGrupe = "G1",
                osvojeniBodovi = null
            ),
            Kviz(
                naziv = "Kviz 4", nazivPredmeta = "RPR",
                datumPocetak = LocalDateTime.of(2026, 3, 20, 12, 0),
                datumKraj = LocalDateTime.of(2026, 3, 22, 23, 59), // Završio pre 10.4.
                datumRada = null,
                trajanje = 5,
                nazivGrupe = "G1",
                osvojeniBodovi = null
            ),
            Kviz(
                naziv = "Kviz 1",
                nazivPredmeta = "RMA",
                datumPocetak = LocalDateTime.of(2026, 4, 15, 12, 0),
                datumKraj = LocalDateTime.of(2026, 4, 16, 23, 59),
                datumRada = null,
                trajanje = 5,
                nazivGrupe = "G1",
                osvojeniBodovi = null
            ),
            Kviz(
                naziv = "Kviz 1",
                nazivPredmeta = "RMA",
                datumPocetak = LocalDateTime.of(2026, 4, 15, 12, 0),
                datumKraj = LocalDateTime.of(2026, 4, 16, 23, 59),
                datumRada = null,
                trajanje = 5,
                nazivGrupe = "G2",
                osvojeniBodovi = null
            ),
            Kviz(
                naziv = "Kviz 1",
                nazivPredmeta = "DM",
                datumPocetak = LocalDateTime.of(2026, 4, 20, 12, 0),
                datumKraj = LocalDateTime.of(2026, 4, 21, 23, 59),
                datumRada = null,
                trajanje = 5,
                nazivGrupe = "G1",
                osvojeniBodovi = null
            ),
            Kviz(
                naziv = "Kviz 1",
                nazivPredmeta = "DM",
                datumPocetak = LocalDateTime.of(2026, 4, 20, 12, 0),
                datumKraj = LocalDateTime.of(2026, 4, 21, 23, 59),
                datumRada = null,
                trajanje = 5,
                nazivGrupe = "G2",
                osvojeniBodovi = null
            ),
            Kviz(
                naziv = "Kviz 2",
                nazivPredmeta = "TP",
                datumPocetak = LocalDateTime.of(2026, 5, 10, 12, 0),
                datumKraj = LocalDateTime.of(2026, 5, 11, 23, 59),
                datumRada = null,
                trajanje = 10,
                nazivGrupe = "G1",
                osvojeniBodovi = null
            ),
            Kviz(
                naziv = "Kviz 2",
                nazivPredmeta = "TP",
                datumPocetak = LocalDateTime.of(2026, 5, 10, 12, 0),
                datumKraj = LocalDateTime.of(2026, 5, 11, 23, 59),
                datumRada = null,
                trajanje = 10,
                nazivGrupe = "G3",
                osvojeniBodovi = null
            )
        )
    }
    fun getUpisani(): List<Kviz> {
        val upisaniNazivi = PredmetStaticData.getUpisani().map { it.naziv }
        return getAll().filter { kviz ->
            upisaniNazivi.contains(kviz.nazivPredmeta)
        }
    }
    fun getDone(): List<Kviz> {
        return getUpisani().filter { it.datumRada != null}
    }
    fun getFuture(): List<Kviz> {
        return getUpisani().filter { it.datumPocetak.isAfter(getReferentDate()) }
    }
    fun getNotTaken(): List<Kviz> {
        return getUpisani().filter { it.datumRada == null && it.datumKraj.isBefore(getReferentDate())}
    }

}