# Now we're setting up the runtime container which will just run the built JAR file.
FROM  eclipse-temurin:latest
# COPY --from=build /home/app/target/spring_crud_app*.jar /usr/local/lib/spring_crud_app.jar
# EXPOSE 8080
# ENTRYPOINT ["java","-jar","/usr/local/lib/spring_crud_app.jar"]
WORKDIR /app
COPY target/spring_crud_app*.jar /app/spring_crud_app.jar
EXPOSE 8080
CMD ["java", "-jar", "spring_crud_app.jar"]