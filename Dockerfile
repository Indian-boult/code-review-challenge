# Start with a Maven image that has OpenJDK 8
FROM maven:3.6.3-jdk-8 as builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven configuration files (pom.xml) and source code
COPY pom.xml /app/
COPY src /app/src/

# Run Maven to compile the code and execute tests
RUN mvn clean install

# Use an official OpenJDK runtime image as a base image for the final container
FROM openjdk:8-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled jar file from the builder stage to the runtime image
COPY --from=builder /app/target/ad-ranking-challenge-1.0.jar /app/ad-ranking-challenge.jar

# Expose the port on which the Spring Boot app runs
EXPOSE 8080

# Set the command to run the jar file when the container starts
ENTRYPOINT ["java", "-jar", "ad-ranking-challenge.jar"]
