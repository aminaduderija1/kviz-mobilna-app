package ba.etf.rma26.projekat.data.repositories.api

import ba.etf.rma26.projekat.data.models.KvizTaken
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface TakeKvizApi {

    @POST("student/{hash}/kviz/{id}")
    suspend fun zapocniKviz(
        @Path("hash") hash: String,
        @Path("id") idKviza: Int,
        @Header("X-API-Key") apiKey: String?
    ): Response<KvizTaken>

    @GET("student/{hash}/kviztaken")
    suspend fun getPocetiKvizovi(
        @Path("hash") hash: String
    ): Response<List<KvizTaken>?>
}