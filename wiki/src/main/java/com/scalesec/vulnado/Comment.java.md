# Documentation for `Comment` Class

## Overview

The `Comment` class is part of the `com.scalesec.vulnado` package and is designed to manage comments within an application. It provides functionalities to create, fetch, and delete comments from a database. The class interacts with a PostgreSQL database to perform these operations.

## Class Structure

### Attributes

- **id**: A `String` representing the unique identifier for the comment.
- **username**: A `String` representing the username of the person who made the comment.
- **body**: A `String` containing the text of the comment.
- **created_on**: A `Timestamp` indicating when the comment was created.

### Constructor

- **Comment(String id, String username, String body, Timestamp created_on)**: Initializes a new instance of the `Comment` class with the provided id, username, body, and creation timestamp.

## Methods

### Static Methods

- **create(String username, String body)**: 
  - Creates a new `Comment` object with a unique ID and the current timestamp.
  - Attempts to save the comment to the database.
  - Returns the created `Comment` object if successful, otherwise throws a `BadRequest` or `ServerError` exception.

- **fetch_all()**: 
  - Retrieves all comments from the database.
  - Returns a `List<Comment>` containing all the comments.

- **delete(String id)**: 
  - Deletes a comment from the database based on the provided ID.
  - Returns `true` if the deletion was successful, otherwise returns `false`.

### Private Methods

- **commit()**: 
  - Inserts the current `Comment` object into the database.
  - Returns `true` if the insertion was successful.

## Insights

- **Database Interaction**: The class uses JDBC to interact with a PostgreSQL database. It assumes the existence of a `Postgres` class with a `connection()` method to obtain database connections.

- **Error Handling**: The class uses custom exceptions (`BadRequest` and `ServerError`) to handle errors during database operations. This provides a clear mechanism for error reporting.

- **UUID for ID**: The `create` method generates a unique identifier for each comment using `UUID.randomUUID()`, ensuring that each comment has a distinct ID.

- **Timestamp Management**: The class uses `Timestamp` to manage the creation time of comments, which is useful for tracking and sorting comments based on their creation time.

- **SQL Operations**: The class performs basic SQL operations such as `SELECT`, `INSERT`, and `DELETE` to manage comments in the database. It uses `PreparedStatement` to prevent SQL injection in the `delete` and `commit` methods.

- **Potential Improvements**: 
  - Consider implementing connection pooling to optimize database connections.
  - Enhance error handling to provide more specific feedback on database operation failures.
  - Implement logging instead of `printStackTrace` for better error tracking and debugging.
