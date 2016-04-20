#!/bin/bash

mvn clean package && java -javaagent:$(pwd)/target/lib/perfcake-agent-7.0-SNAPSHOT.jar -Xms256m -Xmx256m -jar target/sut-1.0-SNAPSHOT.jar
#mvn clean package && java -jar target/app1-1.0-SNAPSHOT.jar
