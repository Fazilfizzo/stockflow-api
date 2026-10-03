# =========================
# 1. Build stage
# =========================
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy Maven configuration first
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline

# Copy application source
COPY src ./src

# Build Spring Boot application
RUN mvn clean package -DskipTests


# =========================
# 2. Runtime stage
# =========================
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# StockFlow runs on port 5670
EXPOSE 5670

# Start Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]