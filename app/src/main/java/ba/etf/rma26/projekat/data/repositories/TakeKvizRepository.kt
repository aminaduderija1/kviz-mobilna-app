package ba.etf.rma26.projekat.data.repositories

import ba.etf.rma26.projekat.data.models.KvizTaken
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object TakeKvizRepository {
    private val gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, JsonDeserializer { json, _, _ ->
            try {
                ZonedDateTime.parse(json.asString).toLocalDateTime()
            } catch (e: Exception) {
                try {
                    LocalDateTime.parse(json.asString)
                } catch (ex: Exception) {
                    LocalDateTime.of(2021, 5, 16, 0, 0)
                }
            }
        })
        .create()

    private val api: TakeKvizApi
        get() = Retrofit.Builder()
            .baseUrl(ApiConfig.getBaseURL())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(TakeKvizApi::class.java)

    suspend fun zapocniKviz(idKviza: Int): KvizTaken? {
        return try {
            val studentHash = AccountRepository.getHash()
            val apiKey = ApiConfig.getApiKey()
            val odgovor = api.zapocniKviz(studentHash, idKviza, apiKey)
            if (odgovor.isSuccessful) odgovor.body() else null
        } catch (e: Exception) {
            null
        }
    }
    suspend fun getPocetiKvizovi(): List<KvizTaken>? {
        return try {
            val studentHash = AccountRepository.getHash()
            val odgovor = api.getPocetiKvizovi(studentHash)
            if (odgovor.isSuccessful) odgovor.body() ?: emptyList() else emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}