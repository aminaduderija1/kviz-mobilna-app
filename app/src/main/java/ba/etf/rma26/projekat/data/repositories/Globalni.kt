package ba.etf.rma26.projekat.data.repositories

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.time.ZonedDateTime

object Globalni {
    private val gsonSaDateTimeom = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, JsonDeserializer { json, _, _ ->
            try {
                ZonedDateTime.parse(json.asString).toLocalDateTime()
            } catch (e: Exception) {
                try {
                    LocalDateTime.parse(json.asString)
                } catch (ex: Exception) {
                    null
                }
            }
        })
        .create()
    fun buildWithDateTime(): Retrofit = Retrofit.Builder()
        .baseUrl(ApiConfig.getBaseURL())
        .addConverterFactory(GsonConverterFactory.create(gsonSaDateTimeom))
        .build()
    fun build(): Retrofit = Retrofit.Builder()
        .baseUrl(ApiConfig.getBaseURL())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}