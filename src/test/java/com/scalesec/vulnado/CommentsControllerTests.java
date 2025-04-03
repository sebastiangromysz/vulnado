package com.scalesec.vulnado;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentsControllerTests {

    @Autowired
    private CommentsController commentsController;

    @MockBean
    private User userMock;

    @MockBean
    private Comment commentMock;

    @Value("${app.secret}")
    private String secret;

    // Test for GET /comments endpoint
    @Test
    public void comments_ShouldReturnListOfComments() {
        String token = "valid-token";
        List<Comment> mockComments = Arrays.asList(new Comment(), new Comment());

        // Mocking User authentication
        doNothing().when(userMock).assertAuth(secret, token);

        // Mocking Comment.fetch_all()
        when(commentMock.fetch_all()).thenReturn(mockComments);

        List<Comment> result = commentsController.comments(token);

        assertNotNull("Result should not be null", result);
        assertEquals("Result size should match mock data size", mockComments.size(), result.size());
    }

    // Test for POST /comments endpoint
    @Test
    public void createComment_ShouldCreateAndReturnComment() {
        String token = "valid-token";
        CommentRequest input = new CommentRequest();
        input.username = "testUser";
        input.body = "testBody";

        Comment mockComment = new Comment();

        // Mocking Comment.create()
        when(commentMock.create(input.username, input.body)).thenReturn(mockComment);

        Comment result = commentsController.createComment(token, input);

        assertNotNull("Result should not be null", result);
        assertEquals("Result should match mock comment", mockComment, result);
    }

    // Test for DELETE /comments/{id} endpoint
    @Test
    public void deleteComment_ShouldDeleteAndReturnTrue() {
        String token = "valid-token";
        String id = "123";

        // Mocking Comment.delete()
        when(commentMock.delete(id)).thenReturn(true);

        Boolean result = commentsController.deleteComment(token, id);

        assertNotNull("Result should not be null", result);
        assertTrue("Result should be true", result);
    }

    // Test for DELETE /comments/{id} endpoint with invalid ID
    @Test(expected = ResponseStatusException.class)
    public void deleteComment_ShouldThrowExceptionForInvalidId() {
        String token = "valid-token";
        String id = "invalid-id";

        // Mocking Comment.delete() to throw exception
        when(commentMock.delete(id)).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID"));

        commentsController.deleteComment(token, id);
    }

    // Test for GET /comments endpoint with invalid token
    @Test(expected = ResponseStatusException.class)
    public void comments_ShouldThrowExceptionForInvalidToken() {
        String token = "invalid-token";

        // Mocking User.assertAuth() to throw exception
        doThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token")).when(userMock).assertAuth(secret, token);

        commentsController.comments(token);
    }

    // Test for POST /comments endpoint with invalid input
    @Test(expected = ResponseStatusException.class)
    public void createComment_ShouldThrowExceptionForInvalidInput() {
        String token = "valid-token";
        CommentRequest input = new CommentRequest();
        input.username = null; // Invalid username
        input.body = "testBody";

        // Mocking Comment.create() to throw exception
        when(commentMock.create(input.username, input.body)).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input"));

        commentsController.createComment(token, input);
    }
}
