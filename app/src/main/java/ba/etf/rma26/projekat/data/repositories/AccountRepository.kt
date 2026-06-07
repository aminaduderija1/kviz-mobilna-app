package ba.etf.rma26.projekat.data.repositories

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object AccountRepository {
    private val _studentHash = MutableStateFlow("demo")
    val studentHash: StateFlow<String> = _studentHash.asStateFlow()
    suspend fun postaviHash(acHash: String): Boolean {
        if (acHash.trim().isEmpty()) {
            return false
        }
        _studentHash.value = acHash
        return true
    }

    suspend fun getHash(): String {
        return _studentHash.value
    }
}