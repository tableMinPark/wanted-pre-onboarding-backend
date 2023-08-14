FROM openjdk:11-jdk
ARG JAR_FILE=./wanted/build/lib/wanted-0.0.1-SNAPSHOT.jar
CMD ["sh", "./build.sh"]
COPY ${JAR_FILE} ./app.jar
ENTRYPOINT ["java","-jar","/app.jar"]