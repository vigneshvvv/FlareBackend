FROM openjdk:17
WORKDIR /app
COPY vulpix-0.0.1-SNAPSHOT.jar /home/flareBackend-d.jar
ENTRYPOINT java -jar /home/flareBackend-d.jar
EXPOSE 8080
