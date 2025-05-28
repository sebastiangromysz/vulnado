# Postgres.java Documentation

## Overview

The `Postgres` class is responsible for managing database connections and operations for a PostgreSQL database. It includes methods for setting up the database schema, inserting user and comment data, and hashing passwords using MD5.

## Class: Postgres

### Methods

#### `connection()`

- **Purpose**: Establishes a connection to the PostgreSQL database using environment variables for configuration.
- **Returns**: `Connection` object if successful, otherwise exits the program.
- **Exceptions**: Catches all exceptions, prints stack trace, and exits the program.

#### `setup()`

- **Purpose**: Sets up the database schema and inserts seed data for users and comments.
- **Operations**:
  - Creates tables `users` and `comments` if they do not exist.
  - Deletes any existing data in these tables.
  - Inserts predefined user and comment data.
- **Exceptions**: Catches all exceptions, prints the error, and exits the program.

#### `md5(String input)`

- **Purpose**: Computes the MD5 hash of a given input string.
- **Returns**: Hexadecimal string representation of the MD5 hash.
- **Exceptions**: Throws `RuntimeException` if the MD5 algorithm is not found.

#### `insertUser(String username, String password)`

- **Purpose**: Inserts a new user into the `users` table with a hashed password.
- **Parameters**:
  - `username`: The username of the user.
  - `password`: The plaintext password to be hashed and stored.
- **Exceptions**: Catches all exceptions and prints stack trace.

#### `insertComment(String username, String body)`

- **Purpose**: Inserts a new comment into the `comments` table.
- **Parameters**:
  - `username`: The username associated with the comment.
  - `body`: The content of the comment.
- **Exceptions**: Catches all exceptions and prints stack trace.

## Database Schema

### Table: users

| Column Name | Data Type | Constraints |
|-------------|-----------|-------------|
| user_id     | VARCHAR(36) | PRIMARY KEY |
| username    | VARCHAR(50) | UNIQUE, NOT NULL |
| password    | VARCHAR(50) | NOT NULL |
| created_on  | TIMESTAMP   | NOT NULL |
| last_login  | TIMESTAMP   | - |

### Table: comments

| Column Name | Data Type | Constraints |
|-------------|-----------|-------------|
| id          | VARCHAR(36) | PRIMARY KEY |
| username    | VARCHAR(36) | - |
| body        | VARCHAR(500) | - |
| created_on  | TIMESTAMP   | NOT NULL |

## Insights

- **Security**: Passwords are stored using MD5 hashing, which is considered weak for cryptographic purposes. Consider using stronger hashing algorithms like SHA-256 or bcrypt for better security.
- **Environment Variables**: The database connection relies on environment variables (`PGHOST`, `PGDATABASE`, `PGUSER`, `PGPASSWORD`). Ensure these are set correctly in the deployment environment.
- **Error Handling**: The program exits on any exception during database connection or setup, which may not be ideal for production environments. Consider implementing more robust error handling and recovery mechanisms.
- **UUID Usage**: UUIDs are used for generating unique identifiers for users and comments, ensuring uniqueness across distributed systems.
