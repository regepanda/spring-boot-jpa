FROM 172.17.0.4:1180/baseimg/jdk:8
ENTRYPOINT ["java", "-Xms128m", "-Xmx1024m", "-jar", "/app.jar"]
ARG JAR_FILE
ADD ${JAR_FILE} /app.jar
EXPOSE 8082