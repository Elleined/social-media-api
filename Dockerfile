FROM alpine/java:21-jdk
MAINTAINER Elleined

# Docker MySQL Credentials
ENV MYSQL_HOST=mysql_server
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=root
ENV MYSQL_PORT=3306
ENV MYSQL_DATABASE=sma_db
ENV PORT=8081

ADD ./target/*.jar social-media-api.jar
EXPOSE 8081
CMD ["java", "-jar", "social-media-api.jar"]
