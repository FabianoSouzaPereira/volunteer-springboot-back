# Etapa 1: build da aplicação
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: imagem final com apenas o JAR
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/volunteer-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

# Expor a porta 8080 do Spring Boot
EXPOSE 8080
