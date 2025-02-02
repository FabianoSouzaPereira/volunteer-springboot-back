FROM openjdk:17-jdk-slim

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo JAR para o diretório de trabalho
COPY target/volunteer-0.0.1-SNAPSHOT.jar /app/app.jar

# Lista os arquivos no diretório de trabalho para verificação
RUN ls -al /app

# Define o comando de entrada para executar o aplicativo
ENTRYPOINT ["java", "-jar", "app.jar"]

# Expõe a porta 8080
EXPOSE 8080