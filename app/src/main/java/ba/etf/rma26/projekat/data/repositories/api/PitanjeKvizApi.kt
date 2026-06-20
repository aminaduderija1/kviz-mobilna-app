package ba.etf.rma26.projekat.data.repositories.api

import ba.etf.rma26.projekat.data.models.Pitanje
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PitanjeKvizApi {
    @GET("kviz/{id}/pitanja")
    suspend fun getPitanja(@Path("id") idKviza: Int): Response<List<Pitanje>>
}