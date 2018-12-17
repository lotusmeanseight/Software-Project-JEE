#!/bin/bash

# building sep and sep-ui
cd ..
cd sep
mvn clean verify
cd ..
cd sep-ui
mvn clean verify
# copy the wars in the folders of the dockerfiles.
cd ..
mv sep/target/sep-gruppe-5.war sprint-7/sep
mv sep-ui/target/sep-ui-gruppe-5.war sprint-7/sep
# building and running the docker containers
cd sprint-7/databases
docker build -t gruppe5-postgres .
docker run --name postgres-container -e POSTGRES_PASSWORD=12345 -d gruppe5-postgres
cd ..
cd sep
docker build -t gruppe5-sep .
docker run --name sep-container -d gruppe5-sep
cd ..
cd sep-ui
docker build -t gruppe5-sep-ui .
docker run --name sep-ui-container -d gruppe5-sep-ui
# defining the docker network
docker network create gruppe5-network
docker network connect gruppe5-network gruppe5-postgres
docker network connect gruppe5-network gruppe5-sep
docker network connect gruppe5-network gruppe5-sep-ui