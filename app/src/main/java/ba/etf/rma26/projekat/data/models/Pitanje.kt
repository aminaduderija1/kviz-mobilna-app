package ba.etf.rma26.projekat.data.models

import com.google.gson.annotations.SerializedName

data class Pitanje (
    val id: Int,
    val idKviza: Int,
    val tekstPitanja: String,
    @SerializedName("opcije")
    val odgovori: List<String>
)