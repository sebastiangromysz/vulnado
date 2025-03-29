# Documentation: `LoginController.java`

## Overview
The `LoginController` class is part of a Spring Boot application that handles user login functionality. It provides an endpoint for authenticating users based on their credentials and returns a token upon successful authentication. The controller also includes error handling for unauthorized access attempts.

---

## File Metadata
- **File Name**: `LoginController.java`

---

## Components

### 1. **LoginController**
#### Description
The main controller class that handles login requests. It uses Spring Boot annotations to define the endpoint and manage the application's configuration.

#### Key Features
- **Endpoint**: `/login`
  - **HTTP Method**: POST
  - **Consumes**: JSON
  - **Produces**: JSON
  - **Cross-Origin**: Allows requests from any origin (`@CrossOrigin(origins = "*")`).
- **Authentication Logic**:
  - Fetches user details using `User.fetch(input.username)`.
  - Compares the hashed password from the request with the stored hashed password.
  - Generates a token using the application's secret if authentication is successful.
  - Throws an `Unauthorized` exception if authentication fails.

#### Dependencies
- **Spring Boot**:
  - `@RestController`
  - `@EnableAutoConfiguration`
  - `@RequestMapping`
  - `@CrossOrigin`
  - `@Value`
- **HTTP Status**:
  - `HttpStatus.UNAUTHORIZED`

#### Fields
| Field Name | Type   | Description                          |
|------------|--------|--------------------------------------|
| `secret`   | String | Application secret used for token generation. |

---

### 2. **LoginRequest**
#### Description
A data structure representing the login request payload. It contains the user's credentials.

#### Fields
| Field Name | Type   | Description                |
|------------|--------|----------------------------|
| `username` | String | Username provided by the user. |
| `password` | String | Password provided by the user. |

#### Implements
- `Serializable`: Ensures the object can be serialized for network communication.

---

### 3. **LoginResponse**
#### Description
A data structure representing the response payload for a successful login. It contains the generated token.

#### Fields
| Field Name | Type   | Description                |
|------------|--------|----------------------------|
| `token`    | String | Authentication token generated for the user. |

#### Constructor
| Constructor Name | Parameters | Description                          |
|------------------|------------|--------------------------------------|
| `LoginResponse`  | `String msg` | Initializes the `token` field with the provided message. |

#### Implements
- `Serializable`: Ensures the object can be serialized for network communication.

---

### 4. **Unauthorized**
#### Description
A custom exception class used to handle unauthorized access attempts. It maps to the HTTP status `401 Unauthorized`.

#### Constructor
| Constructor Name | Parameters       | Description                          |
|------------------|------------------|--------------------------------------|
| `Unauthorized`   | `String exception` | Initializes the exception with a custom message. |

#### Annotations
- `@ResponseStatus(HttpStatus.UNAUTHORIZED)`: Maps the exception to the `401 Unauthorized` HTTP status code.

---

## Insights

### Security Considerations
- **Hardcoded Secret**: The `secret` field is injected using the `@Value` annotation. Ensure that the secret is securely stored and not exposed in the application's configuration files.
- **Password Hashing**: The application uses `Postgres.md5()` for password hashing. Verify that this hashing mechanism is secure and up-to-date with modern cryptographic standards.
- **Cross-Origin Requests**: The `@CrossOrigin(origins = "*")` annotation allows requests from any origin, which may expose the application to Cross-Origin Resource Sharing (CORS) vulnerabilities. Consider restricting origins to trusted domains.

### Error Handling
- The `Unauthorized` exception provides a clear mechanism for handling failed authentication attempts. Ensure that the exception message does not leak sensitive information.

### Scalability
- The `User.fetch()` method and token generation logic should be optimized for high traffic scenarios to prevent performance bottlenecks.

### Serialization
- Both `LoginRequest` and `LoginResponse` implement `Serializable`, which is useful for network communication. Ensure compatibility with the client-side deserialization process.

### Dependency Management
- The application relies on Spring Boot for its core functionality. Ensure that all dependencies are up-to-date to avoid vulnerabilities in third-party libraries.
