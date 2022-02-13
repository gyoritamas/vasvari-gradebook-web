# Gradebook Web

## Az alkalmazás célja
Az alkalmazás a Gradebook API-val együtt biztosítja, hogy a felhasználó egy webes felületen keresztül 
hozzáférjen az elektronikus napló tartalmához.

## Az alkalmazás használata
Adjuk ki az 
```
mvn package -DskipTests
```
parancsot a Gradebook Web alkalmazás gyökérkönyvtárából. Ha a build folyamat véget ért, futtassuk 
a programot a 
```
java -jar target/gradebook-web-0.0.1-SNAPSHOT.jar
```
paranccsal.
Miután a Gradebook API alkalmazást is elindítottuk, használjuk a böngészőt a következő oldalak elérésére.

### Tanulók
Alapértelmezés szerint a [http://localhost:8081/students](http://localhost:8081/students) címen érhető el.

A *Tanulók* oldal megjeleníti az adatbázisban szereplő tanulók adatait; a szerkesztés és törlés opciók
mellett lehetőség van új tanulók létrehozására is az 'Új tanuló' hivatkozás alatt.
Itt a bevitt adatoknak meg kell felelniük bizonyos feltételeknek, pl. nem hagyhatunk üresen mezőket, 
vagy évfolyamnak nem adhatunk meg, csak 1-12 közötti értéket.

### Osztályok
Az *Osztályok* oldal a [http://localhost:8081/classes](http://localhost:8081/classes) címen érhető el. 
Itt található a különböző kurzusok, tantárgyak listája. Adott a szerkesztés, törlés lehetősége, ahogy
az új tantárgy felvételéé is.

### Feladatok
A *Feladatok* oldalt a [http://localhost:8081/assignments](http://localhost:8081/assignments) címen keresztül
érhetjük el. Itt is van lehetőségünk szerkeszteni, törölni, valamint új feladatokat felvenni. Új feladat
létrehozásakor választanunk kell négy előre meghatározott típus közül (test, homework, project, quiz). A 
leírás mező kitöltése opcionális.

### Naplóbejegyzések
A naplóbejegyzések a [http://localhost:8081/gradebook-entries](http://localhost:8081/gradebook-entries) címen
érhetők el.
Naplóbejegyzés létrehozásakor választanunk kell egy már korábban felvett tanulót, tantárgyat és feladatot, valamint
egy 1-5 közötti érdemjegyet.

## Tervezett funkciók
### Felhasználók kezelése
Szükség lenne bejelentkezés megvalósítására és egy oldalra, ahol a felhasználó megadhatja alapvető adatait ill. szerkesztheti azokat.
Legalább két felhasználói szint megvalósítására (tanár és diák), és minimum a szerkesztés és törlés opciók felhasználói szinttől függővé tételére.
### Navigáció
Szükséges lenne egy kezdőoldal elkészítése, és egy menürendszer kialakítása az oldalon való navigáció megkönnyítésére.

## Alkalmazott technológia
- Spring Boot
- Thymeleaf
- Bootstrap