# LinksController Documentation

## Overview

The `LinksController` is a REST controller in a Spring Boot application that provides endpoints for retrieving links from a given URL. It is part of the `com.scalesec.vulnado` package and utilizes the `LinkLister` class to perform the link extraction.

## Annotations

- `@RestController`: Indicates that this class is a REST controller, allowing it to handle HTTP requests.
- `@EnableAutoConfiguration`: Enables Spring Boot's auto-configuration feature, simplifying the setup of the application.

## Endpoints

### `/links`

- **Method**: `GET`
- **Produces**: `application/json`
- **Parameters**: 
  - `url` (String): The URL from which links are to be extracted.
- **Returns**: A list of links (`List<String>`) extracted from the specified URL.
- **Exceptions**: 
  - `IOException`: Thrown if an I/O error occurs during link extraction.

### `/links-v2`

- **Method**: `GET`
- **Produces**: `application/json`
- **Parameters**: 
  - `url` (String): The URL from which links are to be extracted.
- **Returns**: A list of links (`List<String>`) extracted from the specified URL.
- **Exceptions**: 
  - `BadRequest`: Thrown if the request is malformed or invalid.

## Dependencies

- **Spring Boot**: Used for building the application and handling HTTP requests.
- **Spring Web**: Provides the REST controller functionality.

## Insights

- The `LinksController` provides two versions of link extraction endpoints, potentially offering different implementations or handling mechanisms for extracting links.
- The use of custom exceptions like `BadRequest` suggests that the application has specific error handling logic for malformed requests.
- The controller relies on the `LinkLister` class for the actual logic of link extraction, indicating a separation of concerns between request handling and business logic.
