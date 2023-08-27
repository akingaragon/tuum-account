# Tuum Account API

Assignment project for Tuum interview
#### Swagger end point : http://localhost:8080/swagger-ui/index.html#/

## Local development setup

1. Build gradle project

`gradle clean build`

2. Set Spring active profiles to `local`

3. Boot up docker containers

## PostgreSQL

I map Postgresql port to 5499 not to cause any conflict with one of running services port

`docker-compose -f docker/postgresql.yml up -d`

## RabbitMQ

I map RabbitMQ ports to 5699 and 15999 not to cause any conflict with one of running services port

`docker-compose -f docker/rabbitmq.yml up -d`

4. Start up tuum-account-api

`gradle bootRun --args='--spring.profiles.active=local'`