FROM 47.93.240.230:8203/baseimg/jdk:8
ENTRYPOINT ["java", "-Xms128m", "-Xmx1024m", "-jar", "/app.jar"]
ARG JAR_FILE
ADD ${JAR_FILE} /app.jar
EXPOSE 8082