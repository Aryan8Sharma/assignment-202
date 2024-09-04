FROM openjdk:17-jdk-slim

WORKDIR /app

<<<<<<< HEAD
COPY target/assignment1-microservice-0.0.1-SNAPSHOT.jar app.jar
=======
COPY target/assignment1-microservice-part2-world-0.0.1-SNAPSHOT.jar app.jar
>>>>>>> 3ec280184d99f32383813097eb0e6217354e582b

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]