package ba.etfrma.projekat.kviz.model

import java.time.LocalDateTime


data class Kviz (
    val naziv : String,
    val nazivPredmeta : String,
    val datumPocetak: LocalDateTime,
    val datumKraj: LocalDateTime,
    val datumRada: LocalDateTime?,
    val trajanje: Int,
    val nazivGrupe: String,
    val osvojeniBodovi: Float?
)