package ba.etf.rma26.projekat.data.repositories

import ba.etf.rma26.projekat.data.models.Odgovor
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface OdgovorApi {

    @GET("student/{hash}/kviz/{id}/odgovori")
    suspend fun getOdgovoriKviz(
        @Path("hash") hash: String,
        @Path("id") idKviza: Int): Response<List<Odgovor>>

    @POST("student/{hash}/kviztaken/{id}/odgovor")
    suspend fun postaviOdgovorKviz(
        @Path("hash") hash: String,
        @Path("id") idKvizTaken: Int,
        @Body request: OdgovorRequest,
        @Header("X-API-Key") apiKey: String?): Response<Int>
}