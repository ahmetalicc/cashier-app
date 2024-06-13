FROM openjdk:17-alpine
WORKDIR /toyota
COPY target/sale-service-0.0.1-SNAPSHOT.jar sale-service.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "sale-service.jar"]

