package ba.etf.rma26.projekat.data.models

import java.time.LocalDateTime

data class KvizTaken(
    val id: Int,
    val idKviza: Int,
    val studentHash: String,
    val datumRada: LocalDateTime
)