# Use an official Java runtime as a base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /AnonymousMe

# Copy the packaged JAR file into the container
COPY target/*.war AnonymousMe.war

# Run the application
ENTRYPOINT ["java", "-jar", "AnonymousMe.war"]
