# Build Stage
FROM maven:3.8.6-eclipse-temurin-17 AS build

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

# Runtime Stage
FROM eclipse-temurin:17-jre

WORKDIR /AnonymousMeChat

COPY --from=build /app/target/*.war AnonymousMeChat.war

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "AnonymousMeChat.war"]
