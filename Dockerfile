FROM openjdk:11-jdk-slim

ARG APP_NAME=goTwitter-spring

RUN mkdir /usr/${APP_NAME}

COPY target/*.jar /usr/${APP_NAME}/app.jar
WORKDIR /usr/${APP_NAME}

EXPOSE 3000

CMD ["java", "-jar" ,"app.jar"]