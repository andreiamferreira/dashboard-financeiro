FROM maven:3.6.3-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -Dmaven.test.skip=true && \
    mv target/*.jar /app.jar

FROM openjdk:17
WORKDIR /app
COPY --from=build /app.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
