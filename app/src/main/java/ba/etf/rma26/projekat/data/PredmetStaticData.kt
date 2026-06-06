package ba.etf.rma26.projekat.data

import ba.etf.rma26.projekat.model.Grupa
import ba.etf.rma26.projekat.model.Predmet

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
    private var upisaniSaGrupom = mutableMapOf<String, Grupa>("RPR" to Grupa("G1", "RPR"))

    fun getUpisani(): List<Predmet> {
        val naziviUpisanih = upisaniSaGrupom
        return getAll().filter { upisaniSaGrupom.containsKey(it.naziv) }
    }
    fun upis(naziv: String, grupa : Grupa) {
        upisaniSaGrupom[naziv] = grupa
    }
}
