FROM registry.ecd.axway.int/cm/openjdk_alpine_8
MAINTAINER vchauhan@axway.com
COPY mqtt-server-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar
COPY startServer.sh startServer.sh
ENV MQTT_PORT=1883

RUN bash -c 'touch /app.jar'
RUN bash -c 'chmod +x startServer.sh'

ENTRYPOINT ["/bin/bash", "startServer.sh"]