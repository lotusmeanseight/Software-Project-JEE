#!/bin/bash

# building sep and sep-ui
cd ..
cd sep
mvn clean verify
cd ..
cd sep-ui
mvn clean package
cd ..

# copy the wars in the folders of the dockerfiles.
# cd ..
mv sep/target/sep-gruppe-5.war sprint-7/sep
mv sep-ui/target/sep-gruppe-5-ui.war sprint-7/sep-ui
#mv sep-ui/target/sep-ui-gruppe-5.war sprint-7/sep

# building and running the docker containers
docker network create --driver bridge gruppe5-network
cd sprint-7/databases
docker build -t gruppe5-postgres .
docker run --name postgres-container --network gruppe5-network -d gruppe5-postgres
cd ..
cd sep
docker build -t gruppe5-sep .
docker run --name sep-container --network gruppe5-network -d gruppe5-sep 

cd ..
cd sep-ui
docker build -t gruppe5-sep-ui .
docker run --name sep-ui-container --network gruppe5-network -d -p 8080:8080 gruppe5-sep-ui 


# defining the docker network
