FROM openjdk:17-alpine
MAINTAINER Elleined
RUN mkdir "./social-media-api"
COPY . ./social-media-api
WORKDIR ./social-media-api
EXPOSE 8081
CMD ["java", "-jar", "./target/social-media-api-0.0.1-SNAPSHOT.jar"]