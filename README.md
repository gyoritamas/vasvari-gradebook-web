# Gradebook Web

## Az alkalmazás célja
Az alkalmazás a Gradebook API-val együtt biztosítja, hogy a felhasználó egy webes felületen keresztül 
hozzáférjen az elektronikus napló tartalmához.

## Az alkalmazás indítása
A gradebook-web projekt gyökérkönyvtárából adjuk ki az 
```
mvn exec:java -D exec.mainClass=org.vasvari.gradebookweb.GradebookWebApplication
```
vagy futtassuk a *gradebook-web.bat* állományt.

A futtatáshoz maven szükséges, a telepítés lépései [itt](https://maven.apache.org/install.html) találhatók.
## Az alkalmazás használata

Miután a Gradebook API alkalmazást is elindítottuk, használjuk a böngészőt a következő oldalak elérésére.

### Bejelentkezés
Alapértelmezés szerint a http://localhost:8081/ vagy http://localhost:8081/login címen jelentkezhetünk be.

| felhasználónév    | jelszó       | szerepkör     |
|-------------------|--------------|---------------|
| admin             | admin        | rendszergazda |
| fazekasmarianna76 | 9MqBNJacj6N6 | tanár         |
| baloghtamas80     | ULhhJyGXQM7m | tanuló        |

### Tanulók
A *Tanulók* oldalt a [http://localhost:8081/students](http://localhost:8081/students) címen érhetik el rendszergazda
és tanár szerepkörrel rendelkező felhasználók. 

A *Tanulók* oldal megjeleníti az adatbázisban szereplő tanulók adatait. A tanulók között kereshetünk név, évfolyam és 
tantárgy alapján (vagyis aszerint, tanulja-e a tanuló a kiválasztott tantárgyat).

Tanár felhasználók számára csak azok a tanulók jelennek meg, akiket tanítanak.

Rendszergazda szintű felhasználók módosíthatják a tanulók adatait vagy törölhetik azokat, emellett új tanuló 
létrehozására is van lehetőségük az 'Új tanuló' hivatkozás alatt. A műveletek közül a 'felhasználói fiók' lehetőséget 
választva felhasználói fiókot hozhatnak létre az adott tanulónak, vagy megtekinthetik a már meglévő fiókhoz tartozó 
felhasználónevet.

### Tanárok
A rendszergazda szintű felhasználók a [http://localhost:8081/teachers](http://localhost:8081/teachers) címen tudják
a *Tanárok* oldalt elérni. Itt a *Tanulók* oldallal azonos funkciók állnak rendelkezésükre.

### Tantrágyak
Az *Tantárgyak* oldal a [http://localhost:8081/subjects](http://localhost:8081/subjects) címen érhető el.

Tanuló felhasználók megtekinthetik a tantárgyak adatait: a tantárgy és a tanár nevét, ill. a tantárgyhoz tartozó
feladatok számát. A tanulók és tanárok számára csak saját tantárgyaik jelennek meg.

Tanár és rendszergazda szintű felhasználók a műveletek közül a 'részletek' hivatkozás alatt módosíthatják a tantárgyat
tanuló diákok listáját.

Rendszergazdák számára adott a szerkesztés, törlés lehetősége, ahogy az új tantárgy felvételéé is.

### Feladatok
A *Feladatok* oldalt a [http://localhost:8081/assignments](http://localhost:8081/assignments) címen keresztül
érhetjük el. Itt a rendszergazda és tanár felhasználónak lehetősége van szerkeszteni, törölni, valamint új 
feladatokat felvenni, utóbbi csak saját feladataival teheti ezt meg.

### Naplóbejegyzések
A naplóbejegyzések a [http://localhost:8081/gradebook-entries](http://localhost:8081/gradebook-entries) címen
érhetők el.
Tanár és rendszergazda szintű felhasználók létrehozhatnak, módosíthatnak és törölhetnek naplóbejegyzéseket, a tanárok
csak a sajátjaikat.
Naplóbejegyzés létrehozásakor választanunk kell egy tanulót, tantárgyat és feladatot, valamint
egy 1-5 közötti érdemjegyet.

### Felhasználók
Rendszergazdák a [http://localhost:8081/users](http://localhost:8081/users) címen kezelhetik a felhasználókat.
Itt lehetőségük van a felhasználók közötti keresésre név, szerepkör és a fiók aktív állapota szerint, a felhasználói 
fiók törlésére, aktív állapotának módosítására, valamint új, rendszergazda szintű felhasználó létrehozására. (Tanár és tanuló felhasználók a *Tanárok* ill. 
*Tanulók* oldalakon hozhatók létre).

### Profil
A [http://localhost:8081/profile](http://localhost:8081/profile) címen érhető el, a felhasználók itt tudják módosítani
jelszavukat.

## Alkalmazott technológia
- Spring Boot
- Thymeleaf
- Bootstrap