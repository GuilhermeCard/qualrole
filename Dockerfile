# Estágio de construção
FROM gradle:8.7.0-jdk21-alpine AS builder

# Definir o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copiar todos os arquivos do projeto
COPY . .

# Executar o comando Gradle para construir a aplicação
RUN gradle build

# Estágio final
FROM gradle:8.7.0-jdk21-alpine

# Definir o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copiar o arquivo JAR gerado no estágio de construção
COPY --from=builder /app/build/libs/*.jar app.jar

# Expor a porta necessária para a aplicação
EXPOSE 8080

# Comando para iniciar a aplicação
CMD ["java", "-jar", "app.jar"]
