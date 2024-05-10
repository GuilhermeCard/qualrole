FROM gradle:8.7.0-jdk21-alpine AS builder

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/app

RUN gradle build --no-daemon

FROM gradle:8.7.0-jdk21-alpine

COPY --from=builder /home/gradle/src/build/libs/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]