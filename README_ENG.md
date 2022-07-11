# Wine webshop

## Description
This Spring Boot application implements a webshop. Customers who already in the database can filter the products by category and then place the selected wine in the shopping cart. 
Finally, you can pay for the products you want to buy.

### Build with
- Java 17
- Spring Boot 2.7.0.
- Maven 3.8.5
- H2 embedded database 2.1.212
- Flyway schema migration 8.5.11
- Docker 20.10.16

## How to run

Type the following command to the command line to run the shell script in the project source folder(.../wine-webshop):

```sh
$ sh run.sh
```

## Run tests

Type the following command to the command line to run the unit and integration tests in the project source folder:

```sh
$ mvn test
```

### API Docs

Swagger API Docs:
```sh
http://localhost:8080/swagger-ui.html
```
