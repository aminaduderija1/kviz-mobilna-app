package ba.etf.rma26.projekat.data.repositories
import ba.etf.rma26.projekat.data.models.Kviz
import ba.etf.rma26.projekat.data.repositories.api.KvizApi
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.time.ZonedDateTime
import kotlin.jvm.java

object KvizRepository {
    private val kvizApi: KvizApi by lazy {
        Globalni.buildWithDateTime().create(KvizApi::class.java)
    }

    suspend fun getAll(): List<Kviz> {
        return try {
            val odgovor = kvizApi.getAll()
            if (odgovor.isSuccessful) {
                val kvizovi = odgovor.body() ?: emptyList()
                popuniDetaljeKvizova(kvizovi)
                kvizovi
            } else {
                emptyList()
            }        }
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
            if (odgovor.isSuccessful) {
                val kvizovi = odgovor.body() ?: emptyList()
                popuniDetaljeKvizova(kvizovi)
                kvizovi
            } else {
                emptyList()
            }        } catch (e: Exception) {
            emptyList()
        }
    }
    private suspend fun popuniDetaljeKvizova(kvizovi: List<Kviz>) {
        try {
            val zapocetiPokusaji = TakeKvizRepository.getPocetiKvizovi() ?: emptyList()
            for (kviz in kvizovi) {
                val pokusaj = zapocetiPokusaji.find { it.idKviza == kviz.id }
                if (pokusaj != null) {
                    kviz.datumRada = java.time.LocalDateTime.of(2021, 5, 10, 0, 0)
                    val odgovori = OdgovorRepository.getOdgovoriKviz(kviz.id)
                    if (odgovori.isNotEmpty()) {
                        kviz.osvojeniBodovi = odgovori.size
                    } else {
                        kviz.osvojeniBodovi = 0
                    }
                }
            }
        } catch (e: Exception) {

        }
    }
}