package com.scalesec.vulnado;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentsControllerTests {

    @Value("${app.secret}")
    private String secret;

    @Test
    public void comments_ShouldReturnListOfComments() {
        // Arrange
        CommentsController controller = new CommentsController();
        controller.secret = "test-secret";
        String token = "valid-token";

        // Mock User authentication
        mockStatic(User.class);
        doNothing().when(User.class);
        User.assertAuth("test-secret", token);

        // Mock Comment.fetch_all()
        mockStatic(Comment.class);
        List<Comment> mockComments = new ArrayList<>();
        mockComments.add(new Comment("user1", "comment1"));
        mockComments.add(new Comment("user2", "comment2"));
        when(Comment.fetch_all()).thenReturn(mockComments);

        // Act
        List<Comment> result = controller.comments(token);

        // Assert
        assertNotNull("Result should not be null", result);
        assertEquals("Result size should match", 2, result.size());
        assertEquals("First comment username should match", "user1", result.get(0).getUsername());
        assertEquals("Second comment body should match", "comment2", result.get(1).getBody());
    }

    @Test
    public void createComment_ShouldCreateAndReturnComment() {
        // Arrange
        CommentsController controller = new CommentsController();
        String token = "valid-token";
        CommentRequest input = new CommentRequest();
        input.username = "user1";
        input.body = "comment1";

        // Mock Comment.create()
        mockStatic(Comment.class);
        Comment mockComment = new Comment("user1", "comment1");
        when(Comment.create(input.username, input.body)).thenReturn(mockComment);

        // Act
        Comment result = controller.createComment(token, input);

        // Assert
        assertNotNull("Result should not be null", result);
        assertEquals("Username should match", "user1", result.getUsername());
        assertEquals("Body should match", "comment1", result.getBody());
    }

    @Test
    public void deleteComment_ShouldDeleteAndReturnTrue() {
        // Arrange
        CommentsController controller = new CommentsController();
        String token = "valid-token";
        String id = "comment-id";

        // Mock Comment.delete()
        mockStatic(Comment.class);
        when(Comment.delete(id)).thenReturn(true);

        // Act
        Boolean result = controller.deleteComment(token, id);

        // Assert
        assertTrue("Result should be true", result);
    }

    @Test(expected = ResponseStatusException.class)
    public void comments_ShouldThrowExceptionForInvalidToken() {
        // Arrange
        CommentsController controller = new CommentsController();
        controller.secret = "test-secret";
        String token = "invalid-token";

        // Mock User authentication
        mockStatic(User.class);
        doThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED)).when(User.class);
        User.assertAuth("test-secret", token);

        // Act
        controller.comments(token);
    }

    @Test(expected = ResponseStatusException.class)
    public void createComment_ShouldThrowExceptionForInvalidInput() {
        // Arrange
        CommentsController controller = new CommentsController();
        String token = "valid-token";
        CommentRequest input = new CommentRequest();
        input.username = null; // Invalid input
        input.body = "comment1";

        // Mock Comment.create()
        mockStatic(Comment.class);
        when(Comment.create(input.username, input.body)).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

        // Act
        controller.createComment(token, input);
    }

    @Test(expected = ResponseStatusException.class)
    public void deleteComment_ShouldThrowExceptionForInvalidId() {
        // Arrange
        CommentsController controller = new CommentsController();
        String token = "valid-token";
        String id = "invalid-id";

        // Mock Comment.delete()
        mockStatic(Comment.class);
        when(Comment.delete(id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        // Act
        controller.deleteComment(token, id);
    }
}
