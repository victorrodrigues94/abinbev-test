FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn test -Dtest=DrinkRepositoryTest
RUN mvn test -Dtest=DrinkControllerTest
RUN mvn test -Dtest=DrinkServiceTest

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/abinbev-0.0.1-SNAPSHOT.jar abinbev.jar
EXPOSE 8080 5005
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", "-jar", "abinbev.jar"]
