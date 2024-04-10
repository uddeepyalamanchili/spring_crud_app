# Use the official Maven image to create a build artifact.
# This is multi-stage build. In the first stage we're just building the JAR file.
FROM maven:3.6.0-jdk-11-slim AS build
WORKDIR /home/app
COPY src src
COPY pom.xml .
RUN mvn -f pom.xml clean package

# Now we're setting up the runtime container which will just run the built JAR file.
FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/*.jar /usr/local/lib/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]
