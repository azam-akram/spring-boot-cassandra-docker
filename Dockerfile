FROM java:8

EXPOSE 5557

VOLUME /tmp

ADD spring-boot-cassandra-docker.jar app.jar

RUN bash -c 'touch /app.jar'

ENTRYPOINT ["java","-jar","/app.jar"]