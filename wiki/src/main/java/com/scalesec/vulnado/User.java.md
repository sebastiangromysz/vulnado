# User Class Documentation

## Overview

The `User` class is part of the `com.scalesec.vulnado` package and represents a user entity with attributes and methods for handling user authentication and data retrieval from a database. It includes functionality for generating JSON Web Tokens (JWT) and verifying them, as well as fetching user data from a PostgreSQL database.

## Class Definition

### Attributes

- `id`: A `String` representing the unique identifier of the user.
- `username`: A `String` representing the username of the user.
- `hashedPassword`: A `String` representing the hashed password of the user.

### Constructor

- `User(String id, String username, String hashedPassword)`: Initializes a new instance of the `User` class with the specified `id`, `username`, and `hashedPassword`.

## Methods

### `token(String secret)`

- **Description**: Generates a JSON Web Token (JWT) for the user using the provided secret key.
- **Parameters**: 
  - `secret`: A `String` used to sign the JWT.
- **Returns**: A `String` representing the generated JWT.

### `assertAuth(String secret, String token)`

- **Description**: Verifies the provided JWT using the specified secret key. Throws an `Unauthorized` exception if the token is invalid.
- **Parameters**: 
  - `secret`: A `String` used to verify the JWT.
  - `token`: A `String` representing the JWT to be verified.
- **Exceptions**: Throws an `Unauthorized` exception if the token verification fails.

### `fetch(String un)`

- **Description**: Retrieves a `User` object from the database based on the provided username.
- **Parameters**: 
  - `un`: A `String` representing the username to search for in the database.
- **Returns**: A `User` object if a user with the specified username is found; otherwise, returns `null`.
- **Database Interaction**: Executes a SQL query to fetch user data from the `users` table in a PostgreSQL database.

## Insights

- **Security Considerations**: 
  - The `token` method uses HMAC SHA key for signing JWTs, which is a secure method for token generation.
  - The `fetch` method constructs SQL queries using string concatenation, which may lead to SQL injection vulnerabilities. It is recommended to use prepared statements to mitigate this risk.
  
- **Error Handling**: 
  - The `assertAuth` method catches exceptions during token parsing and throws a custom `Unauthorized` exception, providing a mechanism for handling authentication errors.
  - The `fetch` method logs exceptions and returns `null` if an error occurs during database interaction, which may require additional handling in the calling code.

- **Dependencies**: 
  - The class relies on the `io.jsonwebtoken` library for JWT operations.
  - It assumes the existence of a `Postgres` class with a `connection()` method for database connectivity.
