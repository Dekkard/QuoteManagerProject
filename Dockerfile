FROM openjdk:11-oraclelinux7
RUN groupadd -r spring && useradd -r spring -g spring
USER spring:spring
EXPOSE 8081
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} quotemanager-v2.3.jar
ENTRYPOINT ["java","-jar","/quotemanager-v2.3.jar"]