#JAVA_8_IMAGE
FROM openjdk:8-jdk-alpine

# Add Maintainer Info
LABEL authors="Roman"

#PORT_EXPOSE
EXPOSE 9000

# The application's jar file
ARG JAR_FILE=server/build/libs/server-0.0.1-SNAPSHOT.jar
#ARG JAR_FILE

# Add the application's jar to the container
ADD ${JAR_FILE} history-service.jar

#CODE_RUN
CMD ["java","-Djava.security.egd=file:/dev/./urandom", "-jar", "./history-service.jar"]
