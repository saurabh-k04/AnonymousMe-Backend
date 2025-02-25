
# Use an official Maven image to build the application
FROM maven:3.8.6-eclipse-temurin-17 AS build

# Set the working directory
WORKDIR /app

# Copy project files
COPY . .

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
