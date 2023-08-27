# Tuum Account API

Assignment project for Tuum interview

#### Swagger end point : http://localhost:8080/swagger-ui/index.html#/

## Running application

1. Build gradle project

Command will create the jar that will be used in docker container

`gradle clean build`

2. Boot up docker containers

Once the PostgreSQL and RabbitMQ containers have successfully started, the main application will be run.

docker-compose up
