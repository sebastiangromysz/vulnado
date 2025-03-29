# Documentation: `User.java`

## Overview
The `User` class is part of the `com.scalesec.vulnado` package and provides functionality for user management, including token generation, authentication, and database interaction. It encapsulates user-related data and operations, such as fetching user details from a database and handling JSON Web Tokens (JWT) for authentication.

---

## Class: `User`

### Fields
| Field Name      | Type   | Description                                      |
|------------------|--------|--------------------------------------------------|
| `id`            | String | Unique identifier for the user.                  |
| `username`      | String | Username of the user.                            |
| `hashedPassword`| String | Hashed password of the user for secure storage.  |

### Constructor
#### `User(String id, String username, String hashedPassword)`
Initializes a new `User` object with the provided `id`, `username`, and `hashedPassword`.

**Parameters:**
- `id`: Unique identifier for the user.
- `username`: Username of the user.
- `hashedPassword`: Hashed password of the user.

---

### Methods

#### `String token(String secret)`
Generates a JSON Web Token (JWT) for the user using the provided secret key.

**Parameters:**
- `secret`: A string used as the secret key for signing the JWT.

**Returns:**
- A signed JWT containing the user's `username` as the subject.

**Implementation Details:**
- Uses `io.jsonwebtoken` library for JWT creation.
- The secret key is derived using `Keys.hmacShaKeyFor(secret.getBytes())`.
- The token is signed using the HMAC SHA algorithm.

---

#### `static void assertAuth(String secret, String token)`
Validates the provided JWT token using the secret key. Throws an `Unauthorized` exception if the token is invalid.

**Parameters:**
- `secret`: A string used as the secret key for verifying the JWT.
- `token`: The JWT to be validated.

**Implementation Details:**
- Uses `io.jsonwebtoken` library for token parsing and validation.
- If the token is invalid, an exception is caught, logged, and an `Unauthorized` exception is thrown.

---

#### `static User fetch(String un)`
Fetches a user from the database based on the provided username.

**Parameters:**
- `un`: The username of the user to fetch.

**Returns:**
- A `User` object populated with data from the database, or `null` if no user is found.

**Implementation Details:**
- Establishes a connection to the database using `Postgres.connection()`.
- Executes a SQL query to retrieve user details (`id`, `username`, `password`) from the `users` table.
- Constructs a `User` object with the retrieved data.
- Closes the database connection after execution.

**Potential Issues:**
- The SQL query is vulnerable to SQL injection due to direct concatenation of the `username` parameter. This should be mitigated by using prepared statements.

---

## Insights

### Security Concerns
1. **SQL Injection Vulnerability**:
   - The `fetch` method directly concatenates the `username` parameter into the SQL query, making it susceptible to SQL injection attacks. Use prepared statements to mitigate this risk.

2. **Hardcoded Secret Key**:
   - The `token` and `assertAuth` methods rely on a secret key passed as a string. Ensure the secret is securely managed and not hardcoded or exposed.

3. **Exception Handling**:
   - The `assertAuth` method logs exceptions and throws an `Unauthorized` exception. Ensure sensitive information is not exposed in logs.

### Best Practices
- **Use Prepared Statements**:
  Replace the direct SQL query in the `fetch` method with a prepared statement to prevent SQL injection.

- **Secure Secret Management**:
  Use a secure vault or environment variables to manage the secret key used for JWT operations.

- **Password Hashing**:
  Ensure the `hashedPassword` field uses a strong hashing algorithm (e.g., bcrypt) with proper salting.

### Dependencies
- **io.jsonwebtoken**:
  - Used for JWT creation and validation.
- **javax.crypto**:
  - Used for generating HMAC SHA keys.
- **Postgres.connection()**:
  - Assumed to be a utility method for establishing database connections.

### Potential Enhancements
- Add input validation for the `username` parameter in the `fetch` method.
- Implement logging with a proper logging framework instead of `System.out.println`.
- Introduce unit tests for token generation, authentication, and database interaction.
