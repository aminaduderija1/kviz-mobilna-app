package ba.etfrma.projekat.kviz.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ba.etfrma.projekat.kviz.data.GrupaStaticData
import ba.etfrma.projekat.kviz.data.KvizStaticData
import ba.etfrma.projekat.kviz.data.PredmetStaticData
import ba.etfrma.projekat.kviz.model.Kviz

enum class QuizFilter(val label: String) {
    MY("Svi moji kvizovi"),
    ALL("Svi kvizovi"),
    DONE("Urađeni kvizovi"),
    FUTURE("Budući kvizovi"),
    PAST("Prošli kvizovi")
}

class QuizViewModel : ViewModel() {
    var selectedYear by mutableStateOf("")
        private set
    var selectedSubject by mutableStateOf("")
        private set
    var selectedGroup by mutableStateOf("")
        private set
    var selectedFilter by mutableStateOf(QuizFilter.MY)
        private set

    private var dataVersion by mutableIntStateOf(0)

    val availableYears = listOf("1", "2", "3", "4", "5")

    val availableSubjects: List<String>
        get() {
            dataVersion
            if (selectedYear.isBlank()) return emptyList()
            val year = selectedYear.toIntOrNull() ?: return emptyList()
            val enrolled = PredmetStaticData.getUpisani().map { it.naziv }
            return PredmetStaticData.getAll()
                .filter { it.godina == year && it.naziv !in enrolled }
                .map { it.naziv }
        }

    val availableGroups: List<String>
        get() {
            dataVersion
            if (selectedSubject.isBlank()) return emptyList()
            return GrupaStaticData.getGrupaFromPredmet(selectedSubject).map { it.naziv }
        }

    val isEnrollEnabled: Boolean
        get() = selectedYear.isNotBlank() && selectedSubject.isNotBlank() && selectedGroup.isNotBlank()

    fun onYearSelected(year: String) {
        selectedYear = year
        selectedSubject = ""
        selectedGroup = ""
    }

    fun onSubjectSelected(subject: String) {
        selectedSubject = subject
        selectedGroup = ""
    }

    fun onGroupSelected(group: String) {
        selectedGroup = group
    }

    fun onFilterSelected(filter: QuizFilter) {
        selectedFilter = filter
    }

    fun enrollSelectedSubject() {
        val grupa = GrupaStaticData.getGrupaFromPredmet(selectedSubject)
            .find { it.naziv == selectedGroup } ?: return
        PredmetStaticData.upis(selectedSubject, grupa)
        selectedYear = ""
        selectedSubject = ""
        selectedGroup = ""
        dataVersion++
    }

    fun getFilteredQuizzes(): List<Kviz> {
        dataVersion
        return when (selectedFilter) {
            QuizFilter.MY -> KvizStaticData.getUpisani()
            QuizFilter.ALL -> KvizStaticData.getAll()
            QuizFilter.DONE -> KvizStaticData.getDone()
            QuizFilter.FUTURE -> KvizStaticData.getFuture()
            QuizFilter.PAST -> KvizStaticData.getNotTaken()
        }
    }

    fun getFilteredCount(): Int = getFilteredQuizzes().size
}
