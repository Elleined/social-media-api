FROM openjdk:17-alpine
MAINTAINER Elleined
RUN mkdir "./social-media-api"
RUN cd "social-media-api"

# Docker MySQL Credentials
ENV MYSQL_HOST=social-media-mysql
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=root
ENV MYSQL_PORT=3306
ENV MYSQL_DATABASE=social_media_api_db

COPY ./target/*.jar ./
EXPOSE 8081
CMD ["java", "-jar", "./target/social-media-api.jar"]
