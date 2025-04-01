package com.scalesec.vulnado;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTests {

    private static final String SECRET_KEY = "mysecretkey12345678901234567890"; // Mock secret key for testing

    // Test for User.token() method
    @Test
    public void token_ShouldGenerateValidToken() {
        // Arrange
        User user = new User("1", "testuser", "hashedpassword");

        // Act
        String token = user.token(SECRET_KEY);

        // Assert
        assertNotNull("Token should not be null", token);
        assertFalse("Token should not be empty", token.isEmpty());
    }

    // Test for User.assertAuth() method with valid token
    @Test
    public void assertAuth_WithValidToken_ShouldNotThrowException() {
        // Arrange
        User user = new User("1", "testuser", "hashedpassword");
        String token = user.token(SECRET_KEY);

        // Act & Assert
        try {
            User.assertAuth(SECRET_KEY, token);
        } catch (Exception e) {
            fail("Exception should not be thrown for valid token");
        }
    }

    // Test for User.assertAuth() method with invalid token
    @Test(expected = Unauthorized.class)
    public void assertAuth_WithInvalidToken_ShouldThrowUnauthorizedException() {
        // Arrange
        String invalidToken = "invalidToken";

        // Act
        User.assertAuth(SECRET_KEY, invalidToken);
    }

    // Test for User.fetch() method with valid username
    @Test
    public void fetch_WithValidUsername_ShouldReturnUser() throws Exception {
        // Arrange
        String username = "testuser";
        Connection mockConnection = mock(Connection.class);
        Statement mockStatement = mock(Statement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        when(Postgres.connection()).thenReturn(mockConnection);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("user_id")).thenReturn("1");
        when(mockResultSet.getString("username")).thenReturn(username);
        when(mockResultSet.getString("password")).thenReturn("hashedpassword");

        // Act
        User user = User.fetch(username);

        // Assert
        assertNotNull("User should not be null", user);
        assertEquals("User ID should match", "1", user.id);
        assertEquals("Username should match", username, user.username);
        assertEquals("Password should match", "hashedpassword", user.hashedPassword);

        // Verify interactions
        verify(mockConnection).createStatement();
        verify(mockStatement).executeQuery(anyString());
        verify(mockResultSet).next();
        verify(mockResultSet).getString("user_id");
        verify(mockResultSet).getString("username");
        verify(mockResultSet).getString("password");
    }

    // Test for User.fetch() method with invalid username
    @Test
    public void fetch_WithInvalidUsername_ShouldReturnNull() throws Exception {
        // Arrange
        String username = "nonexistentuser";
        Connection mockConnection = mock(Connection.class);
        Statement mockStatement = mock(Statement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        when(Postgres.connection()).thenReturn(mockConnection);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        // Act
        User user = User.fetch(username);

        // Assert
        assertNull("User should be null for nonexistent username", user);

        // Verify interactions
        verify(mockConnection).createStatement();
        verify(mockStatement).executeQuery(anyString());
        verify(mockResultSet).next();
    }
}
