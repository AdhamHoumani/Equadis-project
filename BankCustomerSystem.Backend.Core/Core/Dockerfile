FROM openjdk:11-jre-slim
VOLUME /tmp
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 9091
ENTRYPOINT ["java", "-jar", "/app.jar"]
