# Wine webshop

## Leírás
Ez a Spring Boot alkamazás egy webshopot valósít meg. A már adatbáziban lévő vásárlók kategória szerint leszűrhetik a termékeket, majd a kiválasztott bort a bevásárlókocsiban helyezhetik. 
Végül a megvásárolni kívánt termékeket kifizethetik.

### Használt technológiák
- Java 17
- Spring Boot 2.7.0.
- Maven 3.8.5
- H2 embedded database 2.1.212
- Flyway schema migration 8.5.11
- Docker 20.10.16

## Indítás

Az alkalmazás docker containere terminálból indítható a /wine-webshop könyvtárban az alábbi parancs kiadásával:

```sh
$ sh run.sh
```

## Tesztek futtatása

Az alkalmazás unit és integrációs tesztjei a /wine-webshop könyvtárban kiadott alábbi paranccsal indíthatóak:

```sh
$ mvn test
```

## API Docs
Swagger API dokumentáció:
```sh
http://localhost:8080/swagger-ui.html
```
