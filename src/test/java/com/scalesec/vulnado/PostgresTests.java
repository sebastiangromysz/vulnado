package com.scalesec.vulnado;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostgresTests {

    // Test for connection establishment
    @Test
    public void connection_ShouldEstablishSuccessfully() {
        Connection connection = Postgres.connection();
        assertNotNull("Connection should not be null", connection);
        try {
            connection.close();
        } catch (Exception e) {
            fail("Failed to close connection: " + e.getMessage());
        }
    }

    // Test for MD5 hash generation
    @Test
    public void md5_ShouldGenerateCorrectHash() {
        String input = "testPassword";
        String expectedHash = "e16b2ab8d12314bf4efbd6203906ea6c"; // Precomputed MD5 hash for "testPassword"
        String actualHash = Postgres.md5(input);
        assertEquals("MD5 hash should match expected value", expectedHash, actualHash);
    }

    // Test for database setup
    @Test
    public void setup_ShouldCreateTablesAndInsertSeedData() {
        Postgres.setup();
        try (Connection connection = Postgres.connection();
             Statement stmt = connection.createStatement()) {

            // Verify users table exists and contains seed data
            ResultSet rsUsers = stmt.executeQuery("SELECT COUNT(*) AS count FROM users");
            assertTrue("Users table should exist", rsUsers.next());
            assertTrue("Users table should contain seed data", rsUsers.getInt("count") > 0);

            // Verify comments table exists and contains seed data
            ResultSet rsComments = stmt.executeQuery("SELECT COUNT(*) AS count FROM comments");
            assertTrue("Comments table should exist", rsComments.next());
            assertTrue("Comments table should contain seed data", rsComments.getInt("count") > 0);

        } catch (Exception e) {
            fail("Failed to verify database setup: " + e.getMessage());
        }
    }

    // Test for inserting a user
    @Test
    public void insertUser_ShouldAddUserToDatabase() {
        String username = "testUser";
        String password = "testPassword";
        Postgres.setup(); // Ensure clean database state
        Postgres.insertUser(username, password);

        try (Connection connection = Postgres.connection();
             Statement stmt = connection.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT username FROM users WHERE username = '" + username + "'");
            assertTrue("User should be inserted into the database", rs.next());
            assertEquals("Inserted username should match", username, rs.getString("username"));

        } catch (Exception e) {
            fail("Failed to verify user insertion: " + e.getMessage());
        }
    }

    // Test for inserting a comment
    @Test
    public void insertComment_ShouldAddCommentToDatabase() {
        String username = "testUser";
        String body = "This is a test comment";
        Postgres.setup(); // Ensure clean database state
        Postgres.insertUser(username, "testPassword"); // Insert user first
        Postgres.insertComment(username, body);

        try (Connection connection = Postgres.connection();
             Statement stmt = connection.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT body FROM comments WHERE username = '" + username + "'");
            assertTrue("Comment should be inserted into the database", rs.next());
            assertEquals("Inserted comment body should match", body, rs.getString("body"));

        } catch (Exception e) {
            fail("Failed to verify comment insertion: " + e.getMessage());
        }
    }
}
