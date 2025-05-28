# CommentsController Documentation

## Overview

The `CommentsController` is a RESTful web service controller implemented using Spring Boot. It provides endpoints for managing comments, including fetching, creating, and deleting comments. The controller is configured to handle cross-origin requests and uses token-based authentication.

## Endpoints

### GET /comments

- **Description**: Fetches all comments.
- **Request Header**: 
  - `x-auth-token`: Authentication token required for accessing the endpoint.
- **Produces**: `application/json`
- **Response**: Returns a list of `Comment` objects.

### POST /comments

- **Description**: Creates a new comment.
- **Request Header**: 
  - `x-auth-token`: Authentication token required for accessing the endpoint.
- **Consumes**: `application/json`
- **Produces**: `application/json`
- **Request Body**: 
  - `CommentRequest` object containing:
    - `username`: The name of the user creating the comment.
    - `body`: The content of the comment.
- **Response**: Returns the created `Comment` object.

### DELETE /comments/{id}

- **Description**: Deletes a comment by its ID.
- **Request Header**: 
  - `x-auth-token`: Authentication token required for accessing the endpoint.
- **Path Variable**: 
  - `id`: The ID of the comment to be deleted.
- **Produces**: `application/json`
- **Response**: Returns a boolean indicating the success of the deletion.

## Classes

### CommentRequest

- **Description**: A data structure representing the request body for creating a comment.
- **Fields**:
  - `username`: The name of the user creating the comment.
  - `body`: The content of the comment.

### BadRequest

- **Description**: Exception class representing a bad request error.
- **HTTP Status**: `400 Bad Request`

### ServerError

- **Description**: Exception class representing an internal server error.
- **HTTP Status**: `500 Internal Server Error`

## Insights

- **Authentication**: The controller uses token-based authentication, requiring a valid `x-auth-token` for all operations.
- **Cross-Origin Resource Sharing (CORS)**: The controller is configured to allow requests from any origin, which is useful for web applications that need to interact with the API from different domains.
- **Error Handling**: Custom exceptions `BadRequest` and `ServerError` are defined to handle specific error scenarios, providing meaningful HTTP status codes.
- **Configuration**: The `secret` value is injected from application properties, indicating a secure approach to managing sensitive information.
