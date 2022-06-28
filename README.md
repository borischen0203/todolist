# Todo list service

This is a todo list service. You can get, post, delete, update todo items.

## Features

- Get the todo list
- Add todo item to the list
- Update the todo item status
- Delete todo item from list

## How to use

- use make command to start the server

```bash
make run
```

## API demo

### Add todo item to the list

- `POST` /api/todos

```bash
curl -X POST -H "Content-Type: application/json" -d '{"task" : "cook", "status":1}' http://localhost:9100/api/todos
```

### Get the todo list

- `GET`  /api/todos

```bash
curl -X GET http://localhost:9100/api/todos
```

### Update the todo item status

- `PUT` /api/todos/{id}

```bash
curl -X PUT -H "Content-Type: application/json" -d '{"task" : "cook", "status":1}' http://localhost:9100/api/todos/1
```

### Delete todo item from list

- `DELETE` /api/todos/{id}

```bash
curl -X DELETE http://localhost:9100/api/todos/1
```

## Tech Stack

- Java
- Maven
- Spring Boot framework
- RESTful API
- H2 database
- JUnit
- Mockito
- MockMvc
- Makefile

## Todo:
- [ ] Swagger API document
- [ ] Logging lib issue fix
- [ ] Integration test
- [ ] Controller and Service optimization
- [ ] Use docker SQL DB
- [ ] Deployed heroku