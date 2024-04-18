FROM amazoncorretto:21.0.2-alpine3.19
ADD target/*.jar /app.jar
ENTRYPOINT [ "java", "-jar", "app.jar" ]