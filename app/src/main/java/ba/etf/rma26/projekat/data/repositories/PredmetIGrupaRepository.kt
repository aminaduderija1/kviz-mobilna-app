package ba.etf.rma26.projekat.data.repositories

import ba.etf.rma26.projekat.data.models.Grupa
import ba.etf.rma26.projekat.data.models.Predmet
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PredmetIGrupaRepository {
    private val api: PredmetIGrupaApi
        get() = Retrofit.Builder()
            .baseUrl(ApiConfig.getBaseURL())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PredmetIGrupaApi::class.java)

    suspend fun getPredmeti(): List<Predmet> {
        return try {
            val odgovor = api.getPredmeti()
            if (odgovor.isSuccessful) odgovor.body() ?: emptyList() else emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
    suspend fun getGrupe(): List<Grupa> {
        return try {
            val odgovor = api.getGrupe()
            if (odgovor.isSuccessful) odgovor.body() ?: emptyList() else emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
    suspend fun getGrupeZaPredmet(idPredmeta: Int): List<Grupa>{
        return try {
            val odgovor = api.getGrupeZaPredmet(idPredmeta)
            if (odgovor.isSuccessful) odgovor.body() ?: emptyList() else emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
    suspend fun upisiUGrupu(idGrupa: Int): Boolean{
        return try {
            val studentHash = AccountRepository.getHash()
            val apiKey = ApiConfig.getApiKey()
            val odgovor = api.upisiUGrupu(studentHash, idGrupa, apiKey)
            if (odgovor.isSuccessful) {
                odgovor.body()?.string()?.toBoolean() ?: false
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }
    suspend fun getUpisaneGrupe(): List<Grupa> {
        return try {
            val studentHash = AccountRepository.getHash()
            val odgovor = api.getUpisaneGrupe(studentHash)
            if (odgovor.isSuccessful) odgovor.body() ?: emptyList() else emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}
