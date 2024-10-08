FROM openjdk:17

WORKDIR /app

ENV PROJECT_NAME=url_shortener

COPY ./build/libs/*.jar /app/${PROJECT_NAME}.jar
EXPOSE 55123

ENTRYPOINT java -jar -Duser.timezone=Asia/Seoul /app/$PROJECT_NAME.jar
