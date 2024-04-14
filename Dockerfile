# Use an official OpenJDK runtime as a parent image
FROM openjdk:23-ea-slim

# Set the working directory in the container
WORKDIR /opt/app

# Copy the JAR file from your host to your current location in the image
COPY ./target/spring_crud_app*.jar /opt/app/spring_crud_app.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/opt/app/spring_crud_app.jar"]