FROM openjdk:17-oracle
LABEL maintainer="kngatineau@gmail.com"
ARG JAR_FILE=target/TwitterLike_UMS-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]