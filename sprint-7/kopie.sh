#!/bin/bash

# building sep and sep-ui
cd ..
cd sep
mvn clean verify
cd ..
#cd sep-ui
#mvn clean verify

# copy the wars in the folders of the dockerfiles.
# cd ..
mv sep/target/sep-gruppe-5.war sprint-7/sep
#mv sep-ui/target/sep-ui-gruppe-5.war sprint-7/sep

# building and running the docker containers
cd sprint-7/databases
docker build -t gruppe5-postgres .
POSTGRES_CONTAINER=`docker run --name container -d gruppe5-postgres`
# Wait for the postgres port to be available
until nc -z $(docker inspect --format='{{.NetworkSettings.IPAddress}}' $POSTGRES_CONTAINER) 5432
do
    echo "waiting until postgres container is fully running..."
    sleep 0.5
done
#docker run --name postgres-container -p 5433:5433 -d gruppe5-postgres
#docker run --name container -d gruppe5-postgres
cd ..
cd sep
docker build -t gruppe5-sep .
#docker run --name sep-container -p 8080:8080 -d gruppe5-sep 
#cd ..
#cd sep-ui
#docker build -t gruppe5-sep-ui .
#docker run --name sep-ui-container -d gruppe5-sep-ui

# defining the docker network
#docker network create gruppe5-network
#docker network connect gruppe5-network postgres-container
#docker network connect gruppe5-network sep-container
#docker network connect gruppe5-network gruppe5-sep-ui-container
docker run --name gruppe5 --link container:postgres -p 8080:8080 gruppe5-sep

read name
