package ba.etf.rma26.projekat.data.models

import java.time.LocalDateTime

data class Kviz(
    val id: Int,
    val naziv: String,
    val idPredmeta: Int,
    val idGrupe: Int,
    val datumPocetka: LocalDateTime,
    val datumkraj: LocalDateTime,

    val trajanje: Int,
    val nazivPredmeta: String?,
    val nazivGrupe: String?,
    var datumRada: LocalDateTime? = null,
    var osvojeniBodovi: Float? = null
)