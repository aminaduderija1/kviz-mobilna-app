package ba.etf.rma26.projekat.data.repositories
import ba.etf.rma26.projekat.data.models.Pitanje
import ba.etf.rma26.projekat.data.repositories.api.PitanjeKvizApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PitanjeKvizRepository {
    private val api: PitanjeKvizApi by lazy {
        Globalni.build().create(PitanjeKvizApi::class.java)
    }

    suspend fun getPitanja(idKviza: Int): List<Pitanje> {
        return try {
            val odgovor = api.getPitanja(idKviza)
            if (odgovor.isSuccessful) odgovor.body() ?: emptyList() else emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}