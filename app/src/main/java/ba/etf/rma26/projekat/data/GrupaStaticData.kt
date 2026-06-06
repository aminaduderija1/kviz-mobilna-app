package ba.etf.rma26.projekat.data

import ba.etf.rma26.projekat.model.Grupa

object GrupaStaticData {
    fun getSveGrupe () : List<Grupa>{
        return listOf(
            Grupa("G1", "RPR"),
            Grupa("G1", "RMA"),
            Grupa("G2", "RMA"),
            Grupa("G1", "TP"),
            Grupa("G3", "TP"),
            Grupa("G1", "DM"),
            Grupa("G2", "DM")
        )
    }
    fun getGrupaFromPredmet(nazivPredmeta: String): List<Grupa> {
       return getSveGrupe().filter { it.nazivPredmeta == nazivPredmeta }
    }

}