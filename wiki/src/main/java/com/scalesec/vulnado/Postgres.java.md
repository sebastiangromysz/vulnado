# Documentation for `Postgres.java`

## Overview

The `Postgres` class provides functionality to interact with a PostgreSQL database. It includes methods for establishing a database connection, setting up the database schema, inserting seed data, and hashing passwords using the MD5 algorithm. This class is designed to initialize and manage a database for a user and comment system.

---

## Features

### 1. **Database Connection**
   - Establishes a connection to a PostgreSQL database using credentials and connection details retrieved from environment variables.
   - Uses the `org.postgresql.Driver` for database connectivity.

### 2. **Database Setup**
   - Creates two tables (`users` and `comments`) if they do not already exist.
   - Cleans up any existing data in the tables.
   - Inserts seed data into the `users` and `comments` tables.

### 3. **Password Hashing**
   - Provides a method to hash passwords using the MD5 algorithm for storage in the database.

### 4. **Data Insertion**
   - Includes methods to insert user data (`insertUser`) and comment data (`insertComment`) into the database.

---

## Methods

### `connection()`
- **Description**: Establishes a connection to the PostgreSQL database.
- **Returns**: `Connection` object.
- **Implementation Details**:
  - Retrieves database connection details (`PGHOST`, `PGDATABASE`, `PGUSER`, `PGPASSWORD`) from environment variables.
  - Uses `DriverManager.getConnection()` to establish the connection.
  - Handles exceptions and exits the program if the connection fails.

---

### `setup()`
- **Description**: Sets up the database schema and inserts seed data.
- **Implementation Details**:
  - Creates the `users` table with fields:
    - `user_id`: Primary key (UUID).
    - `username`: Unique, non-null.
    - `password`: Non-null.
    - `created_on`: Timestamp.
    - `last_login`: Timestamp.
  - Creates the `comments` table with fields:
    - `id`: Primary key (UUID).
    - `username`: Foreign key reference to `users`.
    - `body`: Comment text.
    - `created_on`: Timestamp.
  - Deletes existing data in both tables.
  - Inserts predefined seed data for users and comments.

---

### `md5(String input)`
- **Description**: Generates an MD5 hash for the given input string.
- **Returns**: MD5 hash as a hexadecimal string.
- **Implementation Details**:
  - Uses `MessageDigest` to compute the MD5 hash.
  - Converts the hash byte array into a hexadecimal string.
  - Pads the hash to ensure it is 32 characters long.

---

### `insertUser(String username, String password)`
- **Description**: Inserts a new user into the `users` table.
- **Parameters**:
  - `username`: The username of the user.
  - `password`: The plaintext password of the user (hashed before insertion).
- **Implementation Details**:
  - Generates a UUID for the `user_id`.
  - Hashes the password using the `md5` method.
  - Executes an `INSERT` SQL statement using a `PreparedStatement`.

---

### `insertComment(String username, String body)`
- **Description**: Inserts a new comment into the `comments` table.
- **Parameters**:
  - `username`: The username of the user posting the comment.
  - `body`: The content of the comment.
- **Implementation Details**:
  - Generates a UUID for the `id`.
  - Executes an `INSERT` SQL statement using a `PreparedStatement`.

---

## Insights

### Security Concerns
1. **MD5 Hashing**:
   - MD5 is considered cryptographically weak and is not recommended for password storage. Consider using stronger hashing algorithms like `bcrypt` or `PBKDF2`.
   
2. **Environment Variables**:
   - The database credentials are retrieved from environment variables, which is a good practice for securing sensitive information.

3. **SQL Injection**:
   - The use of `PreparedStatement` mitigates SQL injection risks for user and comment insertion.

---

### Scalability
- The current implementation is suitable for small-scale applications. For larger systems, consider implementing connection pooling and optimizing database queries.

---

### Error Handling
- The error handling mechanism prints stack traces and exits the program on failure. This approach is not ideal for production systems. Consider implementing a more robust error-handling strategy.

---

### Seed Data
- The `setup()` method inserts predefined seed data, which is useful for testing but should be removed or modified for production environments.

---

## Dependencies

### External Libraries
- **PostgreSQL JDBC Driver** (`org.postgresql.Driver`): Required for database connectivity.

---

## Environment Variables

| Variable       | Description                          |
|----------------|--------------------------------------|
| `PGHOST`       | Hostname of the PostgreSQL server.  |
| `PGDATABASE`   | Name of the PostgreSQL database.    |
| `PGUSER`       | Username for database authentication. |
| `PGPASSWORD`   | Password for database authentication. |

---

## Database Schema

### `users` Table
| Column Name   | Data Type      | Constraints                |
|---------------|----------------|----------------------------|
| `user_id`     | `VARCHAR(36)`  | Primary Key                |
| `username`    | `VARCHAR(50)`  | Unique, Not Null           |
| `password`    | `VARCHAR(50)`  | Not Null                   |
| `created_on`  | `TIMESTAMP`    | Not Null                   |
| `last_login`  | `TIMESTAMP`    | -                          |

### `comments` Table
| Column Name   | Data Type      | Constraints                |
|---------------|----------------|----------------------------|
| `id`          | `VARCHAR(36)`  | Primary Key                |
| `username`    | `VARCHAR(36)`  | Foreign Key (to `users`)   |
| `body`        | `VARCHAR(500)` | -                          |
| `created_on`  | `TIMESTAMP`    | Not Null                   |
