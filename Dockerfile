# Use an official Maven image to build the application
FROM maven:3.8.6-eclipse-temurin-17 AS build

# Set the working directory
WORKDIR /app

# Copy project files
COPY . .

# Run SonarQube scan before building the application
RUN mvn clean verify sonar:sonar \
    -Dsonar.projectKey=anonymous-app \
    -Dsonar.host.url=http://localhost:9000 \
    -Dsonar.login=sqp_658804ad2bb6d88c9da11cf9c8ba643936771e0c || true

# Build the application (skipping tests for faster build)
RUN mvn clean package -DskipTests

# Use a lightweight JDK runtime image for running the app
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /AnonymousMeChat

# Copy the built WAR file from the previous build stage
COPY --from=build /app/target/*.war AnonymousMeChat.war

# Expose the application's port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "AnonymousMeChat.war"]
