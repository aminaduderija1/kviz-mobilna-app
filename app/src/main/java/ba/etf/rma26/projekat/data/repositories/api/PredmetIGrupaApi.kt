package ba.etf.rma26.projekat.data.repositories.api

import ba.etf.rma26.projekat.data.models.Grupa
import ba.etf.rma26.projekat.data.models.Predmet
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface PredmetIGrupaApi {
    @GET("predmet")
    suspend fun getPredmeti(): Response<List<Predmet>>

    @GET("grupa")
    suspend fun getGrupe(): Response<List<Grupa>>

    @GET("predmet/{id}/grupa")
    suspend fun getGrupeZaPredmet(@Path("id") idPredmeta: Int): Response<List<Grupa>>

    @POST("student/{hash}/grupa/{id}")
    suspend fun upisiUGrupu(
        @Path("hash") hash: String,
        @Path("id") idGrupa: Int,
        @Header("X-API-Key") apiKey: String?
    ): Response<ResponseBody>

    @GET("student/{hash}/grupa")
    suspend fun getUpisaneGrupe(@Path("hash") hash: String): Response<List<Grupa>>
}