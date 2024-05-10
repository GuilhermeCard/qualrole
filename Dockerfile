FROM gradle:8.7.0-jdk21-alpine AS builder

WORKDIR /app

COPY build.gradle settings.gradle gradle.properties .

RUN gradle build

FROM alpine:latest

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]