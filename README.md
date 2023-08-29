# API User Microservice

## Overview

Esta api permite la consulta, creacion, actualizacion y eliminacion de **usuarios**, las peticiones se deben autenticar con JTW.

## Pre-requirements

- JAVA 11
- Maven

## Implemented Libraries

- Spring Security
- JWT
- Lombok
- Mapstruct

## Unit Tests

- Junit
- MockMvc

**Test:**

![test](./docs/img/test2.PNG)

**Coverage:**

![coverage](./docs/img/test1.PNG)

## Solution Diagram

![Diagrama](./docs/img/diagrama.jpg)

## How to run component locally

**Microservice settings:**

- Setup Spring application using [YML file](src/main/resources), i.e. server port.

**Run:**
- `com.novo.nisum.microservices.MicroserviceApplication` main method.

**Login:**

![Login](./docs/img/login.PNG)

**Config Token:**

![Login](./docs/img/token.PNG)

**Save User:**

![Save User](./docs/img/save.PNG)

**Find User:**

![Diagrama](./docs/img/findIdUser.PNG)

## Additional notes

Desarrollado por Edwin Gonzalez  edwinhalo2y3@hotmail.com
