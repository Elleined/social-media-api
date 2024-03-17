FROM openjdk:17-alpine
MAINTAINER Elleined

# Docker MySQL Credentials
ENV MYSQL_HOST=sma_mysql_server
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=root
ENV MYSQL_PORT=3306
ENV MYSQL_DATABASE=forum_db

ADD ./target/*.jar forum-api.jar
EXPOSE 8081
CMD ["java", "-jar", "forum-api.jar"]