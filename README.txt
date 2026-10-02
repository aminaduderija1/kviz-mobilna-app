# Mobilna aplikacija za fakultetske kvizove

Android aplikacija razvijena u okviru predmeta Razvoj mobilnih aplikacija. Aplikacija omogućava kreiranje, upravljanje i rješavanje kvizova po predmetima.

---

## Tehnologije i biblioteke

* **Jezik:** Kotlin
* **UI Framework:** Jetpack Compose
* **Arhitektura:** MVVM (Model-View-ViewModel)
* **Navigacija:** Jetpack Navigation (State Hoisting koncept)
* **Mrežni sloj:** Retrofit (REST API)
* **Asinhrono programiranje:** Kotlin Coroutines (`suspend` funkcije)

---

## Glavne funkcionalnosti

* **Upravljanje predmetima i kvizovima:** Pregled predmeta, filtriranje i pristup kvizovima po oblastima.
* **Interaktivno rješavanje kvizova:** Dinamičko učitavanje pitanja, odabir odgovora i prikaz rezultata u realnom vremenu.
* **REST API sinhronizacija:** Asinhrono preuzimanje i slanje podataka na backend servis pomoću Retrofit-a i Kotlin Coroutines.
