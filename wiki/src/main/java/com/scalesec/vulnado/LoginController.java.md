# LoginController Documentation

## Overview

The `LoginController` is a RESTful web service component responsible for handling login requests. It is part of a Spring Boot application and provides an endpoint for user authentication. The controller processes incoming login requests, verifies user credentials, and returns a token if authentication is successful.

## Components

### Annotations

- `@RestController`: Indicates that this class is a REST controller, allowing it to handle HTTP requests.
- `@EnableAutoConfiguration`: Enables Spring Boot's auto-configuration feature.
- `@Value("${app.secret}")`: Injects the application's secret value from the configuration properties.
- `@CrossOrigin(origins = "*")`: Allows cross-origin requests from any domain.
- `@RequestMapping`: Maps HTTP requests to handler methods of the controller.

### Classes

#### LoginController

- **Attributes**:
  - `secret`: A string injected from application properties, used for token generation.

- **Methods**:
  - `login(LoginRequest input)`: Handles POST requests to the `/login` endpoint. It consumes and produces JSON data. The method fetches the user based on the provided username, checks the password, and returns a `LoginResponse` containing a token if authentication is successful. If authentication fails, it throws an `Unauthorized` exception.

#### LoginRequest

- **Attributes**:
  - `username`: A string representing the user's username.
  - `password`: A string representing the user's password.

- **Purpose**: Represents the data structure for incoming login requests.

#### LoginResponse

- **Attributes**:
  - `token`: A string representing the authentication token.

- **Constructor**:
  - `LoginResponse(String msg)`: Initializes the `token` attribute with the provided message.

- **Purpose**: Represents the data structure for outgoing login responses.

#### Unauthorized

- **Constructor**:
  - `Unauthorized(String exception)`: Initializes the exception message.

- **Purpose**: Custom exception class that represents an unauthorized access attempt, mapped to HTTP status 401 (Unauthorized).

## Insights

- **Security**: The controller uses a secret value for token generation, which is crucial for maintaining secure authentication processes.
- **Cross-Origin Resource Sharing (CORS)**: The controller allows requests from any origin, which might be a security concern if not properly managed.
- **Error Handling**: The use of a custom `Unauthorized` exception provides clear error messaging for failed authentication attempts.
- **Password Handling**: The password is hashed using MD5, which is considered insecure for cryptographic purposes. It is recommended to use a stronger hashing algorithm like bcrypt or Argon2 for password storage and verification.
