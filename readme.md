# MC Tank

The project is to automate and track some gasoline stations that have a shop, a wash, a fast-food, and a fuel. 

## Roles
The station includes the following roles:

- *Boss* - has overview and control of all facilities and supplies.
- *Manager* - has overview of *Reservations* and *Items* sections. Can make * reservations* and sell supplies.
- *Customer* - has access to the personal space where he/she can see *transaction history*, *coupons*, *reservations* and *personal data*.

All passwords for roles:

| Role name   | Login | Password |
|---------|:---------:|:-------:|
| Boss    | `boss`    |  `123`  |
| Manager | `manager` |  `123`  |
| User    |  `test`   |  `test` |

## Run

The project operates on **Java 8** with **H2** database, so there is no preparation step.

1. Clone the repository.
2. Run `./mvnw spring-boot:run`
3. Go to [localhost:8080](http://localhost:8080/)

If you want to package the application - just run:
```
./mvnw clean package
```
The packaged application (a JAR in `target/`) can be run with `java -jar $jarName`.

## Other

We are forced to make the *documentation* and *tests*. So both of them are bad and don't meet their purpose.
The documentation is located under `src/main/asciidoc`.

