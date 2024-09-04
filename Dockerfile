FROM openjdk:17

COPY target/passagem-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]
