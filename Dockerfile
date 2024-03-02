FROM openjdk:17-alpine
MAINTAINER Elleined

# Docker MySQL Credentials
ENV MYSQL_HOST=sma_mysql_server
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=root
ENV MYSQL_PORT=3306
ENV MYSQL_DATABASE=social_media_api_db

ADD ./target/*.jar social-media-api.jar
EXPOSE 8081
CMD ["java", "-jar", "social-media-api.jar"]
