FROM maven:3.8.5-openjdk-17 AS build
COPY ../.. .
RUN mvn clean package -DskipTests

#FROM openjdk:17-jre-slim
#FROM openjdk:17-jdk-alpine
FROM openjdk:17
EXPOSE 8080
#ADD target/sales-inventory-management-app.jar sales-inventory-management-app.jar
COPY --from=build /target/sales-inventory-management-app.jar sales-inventory-management-app.jar
ENTRYPOINT ["java", "-jar", "/sales-inventory-management-app.jar"]