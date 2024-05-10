# Estágio 1: Construir o aplicativo com Gradle
FROM gradle:8.7.0-jdk21-alpine AS builder

WORKDIR /app

# Copiar os arquivos Gradle
COPY build.gradle settings.gradle gradle.properties ./

# Copiar o código-fonte
COPY src ./src

# Construir o aplicativo
RUN gradle build --no-daemon

# Estágio 2: Criar a imagem final
FROM adoptopenjdk/openjdk21:alpine-slim

WORKDIR /app

# Copiar o arquivo JAR construído do estágio anterior
COPY --from=builder /app/build/libs/*.jar ./app.jar

# Expor a porta em que o aplicativo está em execução (se aplicável)
EXPOSE 8080

# Comando de entrada para executar o aplicativo quando o contêiner for iniciado
ENTRYPOINT ["java", "-jar", "app.jar"]
