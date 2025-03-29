# Documentation: `Comment.java`

## Overview
The `Comment` class is a Java implementation for managing comments in a system. It provides functionality to create, fetch, and delete comments, as well as persist them in a PostgreSQL database. The class interacts with the database using JDBC and includes error handling mechanisms for database operations.

---

## Class: `Comment`

### Attributes
| Attribute Name | Type       | Description                                                                 |
|----------------|------------|-----------------------------------------------------------------------------|
| `id`           | `String`   | Unique identifier for the comment, generated using `UUID`.                 |
| `username`     | `String`   | Username of the user who created the comment.                              |
| `body`         | `String`   | Content of the comment.                                                    |
| `created_on`   | `Timestamp`| Timestamp indicating when the comment was created.                         |

---

### Constructor
#### `Comment(String id, String username, String body, Timestamp created_on)`
Initializes a new `Comment` object with the provided attributes.

**Parameters:**
- `id`: Unique identifier for the comment.
- `username`: Username of the comment's author.
- `body`: Content of the comment.
- `created_on`: Timestamp of when the comment was created.

---

### Methods

#### `static Comment create(String username, String body)`
Creates a new comment and persists it in the database.

**Parameters:**
- `username`: Username of the comment's author.
- `body`: Content of the comment.

**Returns:**
- A `Comment` object if the creation and persistence are successful.

**Throws:**
- `BadRequest`: If the comment cannot be saved.
- `ServerError`: If an unexpected error occurs during the operation.

---

#### `static List<Comment> fetch_all()`
Fetches all comments from the database.

**Returns:**
- A `List<Comment>` containing all comments retrieved from the database.

**Behavior:**
- Executes a SQL query to fetch all rows from the `comments` table.
- Maps each row to a `Comment` object and adds it to the list.

---

#### `static Boolean delete(String id)`
Deletes a comment from the database based on its unique identifier.

**Parameters:**
- `id`: The unique identifier of the comment to be deleted.

**Returns:**
- `true` if the deletion is successful.
- `false` if the deletion fails or an exception occurs.

---

#### `private Boolean commit()`
Persists the current `Comment` object in the database.

**Returns:**
- `true` if the insertion is successful.
- `false` if the insertion fails.

**Throws:**
- `SQLException`: If an error occurs during the database operation.

---

## Insights

### Database Interaction
- The class relies on a PostgreSQL database for storing and retrieving comments. The database connection is established using the `Postgres.connection()` method, which is assumed to be implemented elsewhere in the project.
- SQL queries are used for CRUD operations:
  - `INSERT INTO comments` for creating new comments.
  - `SELECT * FROM comments` for fetching all comments.
  - `DELETE FROM comments` for deleting comments.

### Error Handling
- The class uses custom exceptions (`BadRequest` and `ServerError`) to handle errors during comment creation.
- For other database operations, exceptions are caught and logged using `e.printStackTrace()`.

### UUID for Unique Identification
- Each comment is assigned a unique identifier using `UUID.randomUUID().toString()`. This ensures that every comment has a globally unique ID.

### Timestamp Management
- The `created_on` attribute is automatically set to the current timestamp during comment creation using `new Date().getTime()`.

### Potential Issues
- The `finally` block in the `delete` method always returns `false`, even if the deletion is successful. This is likely a bug and should be corrected.
- The `fetch_all` method does not close the `Statement` object, which could lead to resource leaks.

### Security Considerations
- The class uses prepared statements for SQL queries, which helps prevent SQL injection attacks.
- However, there is no input validation for the `username` and `body` fields, which could lead to issues such as storing malicious data.

### Dependencies
- The class depends on external classes and methods:
  - `Postgres.connection()`: For database connection.
  - `BadRequest` and `ServerError`: Custom exception classes.

