# Estágio de construção
FROM ubuntu:latest AS builder

RUN apt-get update
RUN apt-get install gradle:8.7.0-jdk21-alpine -y

# Copiar todos os arquivos do projeto
COPY . .

# Executar o comando Gradle para construir a aplicação
RUN gradle build

# Estágio final
FROM gradle:8.7.0-jdk21-alpine

# Expor a porta necessária para a aplicação
EXPOSE 8080

# Copiar o arquivo JAR gerado no estágio de construção
COPY --from=builder /build/libs/*.jar app.jar

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]