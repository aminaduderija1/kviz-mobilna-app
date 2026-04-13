package ba.etfrma.projekat.kviz.data

import ba.etfrma.projekat.kviz.model.Predmet

object PredmetStaticData {
    fun getAll(): List<Predmet> {
        return listOf(
            Predmet(
                naziv = "RPR",
                godina = 2
            ),
            Predmet(
                naziv = "RMA",
                godina = 2
            ),
            Predmet(
                naziv = "DM",
                godina = 3
            ),
            Predmet(
                naziv = "TP",
                godina = 1
            ),
        )
    }
    private var upisani = mutableListOf("RPR")

    fun getUpisani(): List<Predmet> {
        val naziviUpisanih = upisani
        return getAll().filter { naziviUpisanih.contains(it.naziv) }
    }
    fun upis(naziv: String) {
        if (upisani.none { it == naziv }) {
            upisani.add(naziv)
        }
    }
}
