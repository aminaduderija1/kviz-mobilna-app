package ba.etf.rma26.projekat.data.repositories

import ba.etf.rma26.projekat.data.models.Odgovor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

data class OdgovorRequest(
    val idPitanje: Int,
    val odgovor: Int
)

object OdgovorRepository {
    private val api: OdgovorApi
        get() = Retrofit.Builder()
            .baseUrl(ApiConfig.getBaseURL())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OdgovorApi::class.java)

    suspend fun getOdgovoriKviz(idKviza: Int): List<Odgovor> {
        return try {
            val studentHash = AccountRepository.getHash()
            val odgovor = api.getOdgovoriKviz(studentHash, idKviza)
            if (odgovor.isSuccessful) odgovor.body() ?: emptyList() else emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
    suspend fun postaviOdgovorKviz(idKvizTaken: Int, idPitanje: Int, odgovor: Int): Int {
        return try {
            val hash = AccountRepository.getHash()
            val apiKey = ApiConfig.getApiKey()
            val requestBody = OdgovorRequest(idPitanje = idPitanje, odgovor = odgovor)
            val odgovor = api.postaviOdgovorKviz(hash, idKvizTaken, requestBody, apiKey)
            if (odgovor.isSuccessful) {
                odgovor.body() ?: -1
            } else {
                -1
            }
        } catch (e: Exception) {
            -1
        }
    }
}