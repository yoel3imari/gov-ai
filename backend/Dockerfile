# Use OpenJDK 21 for Java 21
FROM oeclipse-temurin:25-jdk-alpine

# Set working directory
WORKDIR /app

# Copy Gradle wrapper and build files
COPY gradlew gradlew.bat build.gradle settings.gradle ./
COPY gradle gradle/

# Copy source code
COPY src src/

# Build the application
RUN ./gradlew build -x test

# Expose port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "build/libs/morgovai-0.0.1-SNAPSHOT.war"]