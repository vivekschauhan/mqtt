#!/bin/sh

SCRIPTDIR=`dirname "$0"`

OPTS="-Dtransport.mqtt.port=${MQTT_PORT} -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"
java ${OPTS} -jar /app.jar