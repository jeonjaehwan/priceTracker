FROM gradle:7.6-jdk17 AS build

COPY build/libs/price-tracker-service.jar /app/price-tracker-service.jar

ENTRYPOINT ["java", "-jar", "/app/price-tracker-service.jar"]