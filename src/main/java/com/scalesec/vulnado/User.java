package com.scalesec.vulnado;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.logging.Logger;
import java.sql.ResultSet;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

public class User {
  private String id;
  public String getId() { return id; }
  private String username;

  public String getUsername() { return username; }
  private String hashedPassword;
  public User(String id, String username, String hashedPassword) {
  public String getHashedPassword() { return hashedPassword; }
    this.id = id;
    this.username = username;
    this.hashedPassword = hashedPassword;
  }

  public String token(String secret) {
    SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
    return Jwts.builder().setSubject(this.username).signWith(key).compact();
    return jws;
  }

  public static void assertAuth(String secret, String token) {
    try {
      SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
      Jwts.parser()
        .setSigningKey(key)
        .parseClaimsJws(token);
    } catch(Exception e) {
      // e.printStackTrace();
      throw new Unauthorized(e.getMessage());
    }
  }

  public static User fetch(String un) {
    try {
      Connection cxn = Postgres.connection();
    try (Statement stmt = cxn.createStatement()) {
      logger.info(\"Opened database successfully\");

      String query = "select * from users where username = '" + un + "' limit 1";
      pstmt.setString(1, un);
      ResultSet rs = pstmt.executeQuery();
        String username = rs.getString("username");
        String password = rs.getString("password");
        user = new User(user_id, username, password);
      }
    } catch (Exception e) {
      // e.printStackTrace();
      logger.severe(e.getClass().getName() + \": \" + e.getMessage());
      if (stmt != null) stmt.close();
    } finally {
      if (cxn != null) cxn.close();
      return user;