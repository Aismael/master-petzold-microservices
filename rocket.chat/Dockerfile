#for supervisor
FROM ubuntu:16.04
EXPOSE 9090 3000

#FROM node:4-slim
#FROM java:8
RUN apt-get install software-properties-common
RUN apt-get update && apt-get install -y curl nodejs build-essential npm gnupg2  supervisor
# Install Java.
RUN \
  echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | debconf-set-selections && \
  add-apt-repository -y ppa:webupd8team/java && \
  apt-get update && \
  apt-get install -y oracle-java8-installer && \
  rm -rf /var/lib/apt/lists/* && \
  rm -rf /var/cache/oracle-jdk8-installer

# Define working directory.
WORKDIR /data
# Define commonly used JAVA_HOME variable
ENV JAVA_HOME /usr/lib/jvm/java-8-oracle

###
RUN ln -s /usr/bin/nodejs /usr/local/bin/node
#RUN apt-get update && apt-get install -y default-jre supervisor
RUN mkdir -p /var/log/supervisor
RUN chmod 777  /var/log/supervisor

COPY supervisord.conf /etc/supervisor/conf.d/supervisord.conf

#slimeureka enviroment
#FROM java:8
ENV GROOVY_VERSION 2.3.9
# load slimeureka
ADD SpringBootDocker.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_HOME /usr/lib/jvm/java-8-oracle
ENV JAVA_OPTS=""
#ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar -Dspring.profiles.active=docker /app.jar" ]


#FROM node:4-slim

# crafted and tuned by pierre@ozoux.net and sing.li@rocket.chat
MAINTAINER buildmaster@rocket.chat

RUN groupadd -r rocketchat \
&&  useradd -r -g rocketchat rocketchat

VOLUME /app/uploads

# gpg: key 4FD08014: public key "Rocket.Chat Buildmaster <buildmaster@rocket.chat>" imported
RUN gpg --keyserver ha.pool.sks-keyservers.net --recv-keys 0E163286C20D07B9787EBE9FD7F9D0414FD08104

ENV RC_VERSION 0.52.0

WORKDIR /app

RUN curl -fSL "https://rocket.chat/releases/${RC_VERSION}/download" -o rocket.chat.tgz \
&&  curl -fSL "https://rocket.chat/releases/${RC_VERSION}/asc" -o rocket.chat.tgz.asc \
&&  gpg --batch --verify rocket.chat.tgz.asc rocket.chat.tgz \
&&  tar zxvf rocket.chat.tgz \
&&  rm rocket.chat.tgz rocket.chat.tgz.asc \
&&  cd bundle/programs/server \
&&  npm install

USER rocketchat

WORKDIR /app/bundle

# needs a mongoinstance - defaults to container linking with alias 'db'
ENV MONGO_URL=mongodb://db:27017/meteor \
    HOME=/tmp \
    PORT=3000 \
    ROOT_URL=http://localhost:3000 \
    Accounts_AvatarStorePath=/app/uploads\
    ADMIN_USERNAME=admin.example.com\
    ADMIN_PASS=supersecret\
    ADMIN_EMAIL=admin@example.com


#CMD ["node", "main.js"]

CMD ["/usr/bin/supervisord"]

