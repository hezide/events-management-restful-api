
# Events Management Restful API

This repository contains a RESTful API for event management built using Spring Boot and PostgreSQL.
## Known issues and non-completed tasks:
- Notification 30 minutes before the event is not implemented yet
- Integration Testing
- Provide a DTO to replace the direct connection between the Event and Participants model and the API controller
- Logging
- Automatic documentation from the code
- Bonus Features
  
## Architecture
![Untitled Diagram drawio](https://github.com/hezide/events-management-restful-api/assets/22726977/f4fad7dd-2120-46ce-bf36-7bf699e9cc98)

The architecture of the project aligns with a layered structure:

### 1. Presentation Layer:
**Controllers:** Responsible for handling incoming HTTP requests, processing inputs, and returning responses. They delegate business logic to the service layer and manage request/response handling.

### 2. Service Layer:
**Services:** Contain business logic, implement use cases, and coordinate interactions between different parts of the application. They abstract and encapsulate complex business rules.

### 3. Data Access Layer:
**Repositories:** Manage data access, database interactions, and perform CRUD operations. They abstract away the database-specific logic and provide an interface to interact with the underlying database.

### 4. Model:
**Entities:** Represent data structures used within the application. Entities map to database tables, while DTOs serve as data transfer objects for communication between layers.

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

# API Reference
This API allows you to manage events by creating, retrieving, updating, and deleting them. Adjust the endpoints and request/response bodies as needed based on your application's requirements.

### Create Event

```http
  POST /events
```

Creates a new event.

#### Request Body

| Field         | Type     | Description               |
| :------------ | :------- | :------------------------ |
| `event`       | `object` | **Required**. Event data  |

### Example of event data
```
{
    "title": "Training Workshop",
    "startTime": "2024-01-05T10:00:00",
    "endTime": "2024-01-05T16:00:00",
    "location": "Tech Hub Auditorium",
    "venue": null,
    "organizer": {
        "firstName": "Michael",
        "lastName": "Brown",
        "email": "michael@example.com"
    },
    "participants": [
        {
            "firstName": "Sarah",
            "lastName": "Taylor",
            "email": "sarah@example.com"
        },
        {
            "firstName": "Oliver",
            "lastName": "Davis",
            "email": "oliver@example.com"
        }
    ]
}
```
#### Response

| Status Code | Description            |
| :---------- | :--------------------- |
| `201`       | Event created          |
| `500`       | Internal Server Error  |

### Get All Events

```http
  GET /events
```

Retrieves a list of all events.

#### Query Parameters

| Parameter    | Type     | Description                                     |
| :----------- | :------- | :---------------------------------------------- |
| `location`   | `string` | Filters events by location                      |
| `venue`      | `string` | Filters events by venue                         |
| `sort`       | `string` | Sorts events by a field (default: `createdAt`)  |
| `order`      | `string` | Orders events (`asc` or `desc`, default: `asc`) |
| `page`       | `integer`| Specifies the page number (default: `0`)        |
| `size`       | `integer`| Specifies the page size (default: `10`)         |

#### Response

| Status Code | Description            |
| :---------- | :--------------------- |
| `200`       | List of events         |

### Get Event by ID

```http
  GET /events/{id}
```

Retrieves an event by ID.

#### Path Parameters

| Parameter | Type     | Description               |
| :-------- | :------- | :------------------------ |
| `id`      | `string` | **Required**. Event ID    |

#### Response

| Status Code | Description            |
| :---------- | :--------------------- |
| `200`       | Event found            |
| `404`       | Event not found        |
| `500`       | Internal Server Error  |

### Update Event

```http
  PUT /events/{id}
```

Updates an event by ID, the entire event properties will get updated even if they weren't provided.

#### Path Parameters

| Parameter | Type     | Description               |
| :-------- | :------- | :------------------------ |
| `id`      | `string` | **Required**. Event ID    |

#### Request Body

| Field              | Type     | Description                           |
| :----------------- | :------- | :------------------------------------ |
| `updatedEventDetails` | `object` | **Required**. Updated event data     |

#### Response

| Status Code | Description            |
| :---------- | :--------------------- |
| `200`       | Event updated          |
| `404`       | Event not found        |
| `500`       | Internal Server Error  |

### Partially Update Event

```http
  PATCH /events/{id}
```

Partially updates an event by ID, will update only the properties that were provided.

#### Path Parameters

| Parameter | Type     | Description               |
| :-------- | :------- | :------------------------ |
| `id`      | `string` | **Required**. Event ID    |

#### Request Body

| Field              | Type     | Description                           |
| :----------------- | :------- | :------------------------------------ |
| `updatedEventDetails` | `object` | **Required**. Updated event data     |

#### Response

| Status Code | Description            |
| :---------- | :--------------------- |
| `200`       | Event updated          |
| `404`       | Event not found        |
| `500`       | Internal Server Error  |

### Delete Event

```http
  DELETE /events/{id}
```

Deletes an event by ID.

#### Path Parameters

| Parameter | Type     | Description               |
| :-------- | :------- | :------------------------ |
| `id`      | `string` | **Required**. Event ID    |

#### Response

| Status Code | Description            |
| :---------- | :--------------------- |
| `200`       | Event deleted          |
| `404`       | Event not found        |
| `500`       | Internal Server Error  |
