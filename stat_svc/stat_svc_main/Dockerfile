FROM amazoncorretto:11
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8082
COPY target/*.jar stat_svc_main.jar
ENTRYPOINT ["java","-jar","/stat_svc_main.jar"]