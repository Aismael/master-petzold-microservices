FROM java:8

ADD SpringBootDocker.jar app.jar

RUN sh -c 'touch /app.jar'

ENV JAVA_HOME /usr/lib/jvm/java-8-oracle
ENV JAVA_OPTS=""

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar -Dspring.profiles.active=docker /app.jar" ]