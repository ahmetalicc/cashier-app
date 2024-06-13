FROM openjdk:17-alpine
WORKDIR /toyota
COPY target/usermanagement-service-0.0.1-SNAPSHOT.jar usermanagement-service.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "usermanagement-service.jar"]