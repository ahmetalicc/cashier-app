FROM openjdk:17-alpine
WORKDIR /toyota
COPY target/authentication-service-0.0.1-SNAPSHOT.jar authentication-service.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "authentication-service.jar"]

