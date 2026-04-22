FROM openjdk:17-jdk-slim
WORKDIR /app
COPY jars/a1-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
# Allow DB configs to be overridden via environment variables
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/postgres \
    SPRING_DATASOURCE_USERNAME=postgres \
    SPRING_DATASOURCE_PASSWORD=mysecretpassword

ENTRYPOINT ["java","-jar","app.jar"]
