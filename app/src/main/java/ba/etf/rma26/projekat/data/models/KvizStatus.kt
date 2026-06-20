package ba.etf.rma26.projekat.data.models

import java.time.LocalDateTime

enum class KvizStatusBoja {
    PLAVA, ZUTA, ZELENA, CRVENA
}

data class KvizStatus(
    val boja: KvizStatusBoja,
    val datum: LocalDateTime,
)

fun Kviz.getStatus(): KvizStatus {
    val referentniDatum = LocalDateTime.of(2021, 5, 9, 0, 0)
    val datumPocetka = datumPocetka ?: referentniDatum
    val datumKraja = datumkraj ?: referentniDatum

    return when {
        datumRada != null || osvojeniBodovi != null ->
            KvizStatus(KvizStatusBoja.PLAVA, datumRada ?: referentniDatum)

        datumPocetka.isAfter(referentniDatum) ->
            KvizStatus(KvizStatusBoja.ZUTA, datumPocetka)

        datumKraja.isBefore(referentniDatum) ->
            KvizStatus(KvizStatusBoja.CRVENA, datumKraja)

        else ->
            KvizStatus(KvizStatusBoja.ZELENA, datumKraja)
    }
}