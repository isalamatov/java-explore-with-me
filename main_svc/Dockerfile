FROM amazoncorretto:11
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8083
COPY target/*.jar explore_main_svc.jar
ENTRYPOINT ["java","-jar","/explore_main_svc.jar"]