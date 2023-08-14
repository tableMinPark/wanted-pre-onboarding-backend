FROM openjdk:11-jdk
ARG JAR_FILE=./wanted/build/libs/wanted-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} ./app.jar
ENTRYPOINT ["java","-jar","/app.jar"]