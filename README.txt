ZADATAK 1:
1. IZMJENA POČETNOG EKRANA
Izmjena: Izdvajanje forme za upis na predmet sa početnog ekrana na zaseban ekran (UpisScreen) kojem se pristupa putem dugmeta.
Korist: Na početnom ekranu su se nalazile u suštini dvije funkcionalnosti upis na predmet i filtriranje.
Stoga, je upis na predmet izmješten na novi ekran.

2. IZMJENA IZGLEDA FILTERA
Izmjena: Zamjena nepreglednog ExposedDropdownMenu menija za odabir filtera kategorija kvizova s
a vertikalnom listom klikabilnih kartica koje također prikazuju trenutni broj dostupnih kvizova.
Korist: Korisnik sada jednim pogledom dobija informaciju o dostupnim filterima za kvizove kao i o broju dostupnih kvizova za svaki filter. 

3. IZMJENA IZGLEDA KARTICA KVIZOVA
Izmjena: Zamjena boja kao i formatiranje ispisa u svakoj kartici
Korist: Aplikacija izgleda jednolično, također preglednija je.

ZADATAK 2:
1. REORGANIZACIJA API INTERFEJSA
Izmjena: Retrofit API interfejsi (KvizApi, OdgovorApi, PitanjeKvizApi,PredmetIGrupaApi, TakeKvizApi) premješteni u  
repositories/api/ subpackage.
Korist: Razdvajanje između API interfejsa i implementacije (repozitoriji). Na ovaj način lakše je pronalaziti stvari u kodu.

2. RAZDVAJANJE KVIZ STATUS LOGIKE
Izmjena: Funkcija getStatus() i enum KvizStatusBoja izdvojeni iz KvizCard.kt u novi fajl data/models/KvizStatus.kt.
Konstrukcija Triple<Int, LocalDateTime, String> zamijenjena KvizStatus data klasom.
Korist: KvizCard.kt sada sadrži samo UI logiku. Poslovna status logika odvojena od UI logike, dosta je preglednije na ovaj način.

3. ZAMJENA RUČNE LOGIKE SA LazyVerticalGrid
Izmjena: U PrikaziKviz.kt je ručno implementirana logika za smještanje kvizova u parove kroz petlju i prikaz
kroz LazyColumn + Row. Zamijenjeno sa LazyVerticalGrid(GridCells.Fixed(2)).
Korist: Kod je znatno kraći i jasniji. 30 linija ručne logike zamijenjeno sa Compose ugrađenim rješenjem.

4. JEDAN FAJL RETROFIT KONFIGURACIJE (Globalni.kt)
Izmjena: Svaki repozitorij je imao vlastiti Retrofit.Builder
blok koji se pozivao pri svakom API pozivu (get() property). Kreiran Globalni.kt
objekat sa buildWithDateTime() i build() metodama. Svi repozitoriji sada koriste
Globalni umjesto lokalnih duplikata.
Korist: Retrofit instanca se kreira jednom, ne pri svakom API pozivu. Lakše za pratiti izmjene u kodu, 
da nismo ovo uradili pri kasnijim izmjenama, izmjene bi morali vrsiti ne u jednom nego u više fajlova.
 