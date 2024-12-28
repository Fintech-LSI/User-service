# Dockerfile
FROM openjdk:17-jdk-slim

WORKDIR /app

# Create directory for images
RUN mkdir -p src/main/public/images

# Copy the JAR file
COPY target/*.jar app.jar

# Copy the images directory
COPY src/main/public/images src/main/public/images

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]