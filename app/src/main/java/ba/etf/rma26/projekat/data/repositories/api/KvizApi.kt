package ba.etf.rma26.projekat.data.repositories.api

import ba.etf.rma26.projekat.data.models.Kviz
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface KvizApi {
    @GET("kviz")
    suspend fun getAll(): Response<List<Kviz>>

    @GET("kviz/{id}")
    suspend fun getById(@Path("id") id: Int): Response<Kviz>

    @GET("student/{hash}/kviz")
    suspend fun getUpisani(@Path("hash") hash: String): Response<List<Kviz>>
}