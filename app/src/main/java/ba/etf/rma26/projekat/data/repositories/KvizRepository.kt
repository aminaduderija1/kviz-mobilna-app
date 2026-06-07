package ba.etf.rma26.projekat.data.repositories
import ba.etf.rma26.projekat.data.models.Kviz
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.time.ZonedDateTime
import kotlin.jvm.java

object KvizRepository {
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

    private val kvizApi: KvizApi
        get() = Retrofit.Builder()
            .baseUrl(ApiConfig.getBaseURL())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(KvizApi::class.java)

    suspend fun getAll(): List<Kviz> {
        return try {
            val odgovor = kvizApi.getAll()
            if (odgovor.isSuccessful) odgovor.body() ?: emptyList() else emptyList()
        }
        catch (e: Exception) {
            emptyList()
        }
    }
    suspend fun getById(id: Int): Kviz? {
        return try {
            val odgovor = kvizApi.getById(id)
            if (odgovor.isSuccessful) odgovor.body() else null
        } catch (e: Exception) {
            null
        }
    }
    suspend fun getUpisani(): List<Kviz> {
        return try {
            val studentHash = AccountRepository.getHash()
            val odgovor = kvizApi.getUpisani(studentHash)
            if (odgovor.isSuccessful) odgovor.body() ?: emptyList() else emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}