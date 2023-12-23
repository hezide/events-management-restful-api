
# Events Management Restful API

This repository contains a RESTful API for event management built using Spring Boot and PostgreSQL. Follow the steps below to set up and run the application using Docker Compose.

## Prerequisites
Make sure you have Docker and Docker Compose installed on your system.

[Docker Installation Guide](https://docs.docker.com/engine/install/)

[Docker Compose Installation Guide](https://docs.docker.com/compose/install/)

## Getting Started
### Clone this repository:

``` git clone https://github.com/hezide/events-management-restful-api.git ```


### Navigate to the project directory:

``` cd events-management-restful-api ```

### Update application configuration (optional):

If you need to modify any environment variables or configurations, update the docker-compose.yml file accordingly.


**docker-compose.yml**
```
version: '3.8'

services:
  postgres:
    # ...
  
  my-spring-boot-app:
    # ...
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/event_management
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: Alfabet@123
    # ...
```

## Running the Application
### Run Docker Compose:

`docker-compose up`

This command will start two services: PostgreSQL and the Spring Boot application.

The API will be accessible at http://localhost:8080.

### Stopping the Application
To stop the application and remove the containers, use:

`docker-compose down`

