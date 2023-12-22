# Use a base image with Maven and Java pre-installed
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory inside the container
WORKDIR /build

# Copy the project files into the container
COPY . .

# Build the Spring Boot application
RUN mvn clean package -Dmaven.test.skip=true

# Use a base image with Java pre-installed
FROM openjdk:17-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled Spring Boot application JAR file from the previous stage
COPY --from=build /build/target/events-management-restful-api-0.0.1-SNAPSHOT.jar /app/events-management-restful-api-0.0.1-SNAPSHOT.jar

# Expose the port that your Spring Boot application runs on
EXPOSE 8080

# Command to run your Spring Boot application when the container starts
CMD ["java", "-jar", "events-management-restful-api-0.0.1-SNAPSHOT.jar"]