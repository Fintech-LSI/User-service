# Build stage
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-jdk-slim

WORKDIR /app

RUN mkdir -p src/main/public/images

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

COPY src/main/public/images src/main/public/images

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]