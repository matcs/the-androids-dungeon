FROM openjdk:11-jdk
EXPOSE 8080
ARG JAR_FILE=target/androidsdungeon-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} androidsdungeon-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/androidsdungeon-0.0.1-SNAPSHOT.jar"]