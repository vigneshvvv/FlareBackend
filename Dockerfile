FROM openjdk:17
RUN gradle clean build
ARG JAR_FILE=build/libs/FlareBackend.jar
COPY ${JAR_File} flareBackend-d.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/flareBackend-d.jar"]
