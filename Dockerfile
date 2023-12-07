# Use an official OpenJDK runtime as a parent image
FROM adoptopenjdk:17-jre-hotspot

# Set the working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY ./target/task-management-system-0.0.1-SNAPSHOT.jar /app/app.jar

# Make port 8080 available outside this container
EXPOSE 8080

# Run the JAR file
CMD ["java", "-jar", "app.jar"]