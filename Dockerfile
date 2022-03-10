FROM openjdk:8-jdk-alpine
EXPOSE 8081
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} quotemanager-0.1.1.jar
ENTRYPOINT ["java","-jar","/quotemanager-0.1.1.jar"]