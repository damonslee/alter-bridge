# Toy - Alter Bridge [![Build Status](https://travis-ci.org/yaboong/alter-bridge.svg?branch=master)](https://travis-ci.org/yaboong/alter-bridge)
Yaboong's [Spring Boot](http://projects.spring.io/spring-boot/) sample app.

## 목적
- 로이필딩이 말하는 진짜 REST API 의 제약사항을 지켜서 개발해보기   
- 참고자료
  - [[Naver D2] Day1, 2-2. 그런 REST API로 괜찮은가](https://youtu.be/RP_f5dMoHFc)
  - [스프링 기반 REST API 개발](https://www.inflearn.com/course/spring_rest-api)
  
## 적용해보고 싶었던 것들
- [Spring Testing](https://docs.spring.io/spring/docs/current/spring-framework-reference/testing.html)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/2.1.10.RELEASE/reference/html/)
- [Spring HATEOAS](https://docs.spring.io/spring-hateoas/docs/1.0.0.RC2/reference/html/)
- [Spring REST Docs](https://docs.spring.io/spring-restdocs/docs/2.0.3.RELEASE/reference/html5/)


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
- After run -> [http://localhost:8080/docs/index.html](http://localhost:8080/docs/index.html)

