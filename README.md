# Toy - Alter Bridge
Yaboong's [Spring Boot](http://projects.spring.io/spring-boot/) sample app.

## Requirements
For building and running the application you need:

- [JDK 1.11](https://tecadmin.net/install-java-macos/)
- [Springboot 2.1.7](https://spring.io/projects/spring-boot)
- [Gradle 4.9](https://docs.gradle.org/current/userguide/getting_started.html)
- [H2](http://www.h2database.com/html/main.html)

## Run
```bash
> git clone https://github.com/yaboong/alter-bridge.git
> cd alter-bridge
> ./gradlew build
> java -jar -Dspring.profiles.active=local build/libs/alter-bridge-0.0.1-SNAPSHOT.jar
```

## Guide Documents
- After run -> [http://localhost:18080/docs/index.html](http://localhost:18080/docs/index.html)

