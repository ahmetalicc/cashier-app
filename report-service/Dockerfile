FROM openjdk:17-alpine
WORKDIR /toyota
COPY target/report-service-0.0.1-SNAPSHOT.jar report-service.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "report-service.jar"]
