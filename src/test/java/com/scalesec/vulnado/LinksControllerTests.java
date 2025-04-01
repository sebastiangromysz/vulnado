package com.scalesec.vulnado;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LinksControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private LinkLister linkLister;

    // Test for /links endpoint with valid URL
    @Test
    public void links_ValidUrl_ShouldReturnLinks() throws IOException {
        String testUrl = "http://example.com";
        List<String> mockLinks = Arrays.asList("http://example.com/link1", "http://example.com/link2");

        // Mocking the behavior of LinkLister.getLinks
        Mockito.when(linkLister.getLinks(testUrl)).thenReturn(mockLinks);

        ResponseEntity<List> response = restTemplate.getForEntity("/links?url=" + testUrl, List.class);

        assertEquals("Expected HTTP status 200", HttpStatus.OK, response.getStatusCode());
        assertEquals("Expected links to match mocked links", mockLinks, response.getBody());
    }

    // Test for /links endpoint with invalid URL
    @Test
    public void links_InvalidUrl_ShouldReturnEmptyList() throws IOException {
        String testUrl = "invalid-url";

        // Mocking the behavior of LinkLister.getLinks to throw IOException
        Mockito.when(linkLister.getLinks(testUrl)).thenThrow(new IOException("Invalid URL"));

        ResponseEntity<String> response = restTemplate.getForEntity("/links?url=" + testUrl, String.class);

        assertEquals("Expected HTTP status 500", HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue("Expected error message to contain 'Invalid URL'", response.getBody().contains("Invalid URL"));
    }

    // Test for /links-v2 endpoint with valid URL
    @Test
    public void linksV2_ValidUrl_ShouldReturnLinks() throws BadRequest {
        String testUrl = "http://example.com";
        List<String> mockLinks = Arrays.asList("http://example.com/link1", "http://example.com/link2");

        // Mocking the behavior of LinkLister.getLinksV2
        Mockito.when(linkLister.getLinksV2(testUrl)).thenReturn(mockLinks);

        ResponseEntity<List> response = restTemplate.getForEntity("/links-v2?url=" + testUrl, List.class);

        assertEquals("Expected HTTP status 200", HttpStatus.OK, response.getStatusCode());
        assertEquals("Expected links to match mocked links", mockLinks, response.getBody());
    }

    // Test for /links-v2 endpoint with invalid URL
    @Test
    public void linksV2_InvalidUrl_ShouldReturnBadRequest() throws BadRequest {
        String testUrl = "invalid-url";

        // Mocking the behavior of LinkLister.getLinksV2 to throw BadRequest
        Mockito.when(linkLister.getLinksV2(testUrl)).thenThrow(new BadRequest("Invalid URL"));

        ResponseEntity<String> response = restTemplate.getForEntity("/links-v2?url=" + testUrl, String.class);

        assertEquals("Expected HTTP status 400", HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue("Expected error message to contain 'Invalid URL'", response.getBody().contains("Invalid URL"));
    }

    // Test for /links endpoint with no URL parameter
    @Test
    public void links_NoUrlParameter_ShouldReturnBadRequest() {
        ResponseEntity<String> response = restTemplate.getForEntity("/links", String.class);

        assertEquals("Expected HTTP status 400", HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // Test for /links-v2 endpoint with no URL parameter
    @Test
    public void linksV2_NoUrlParameter_ShouldReturnBadRequest() {
        ResponseEntity<String> response = restTemplate.getForEntity("/links-v2", String.class);

        assertEquals("Expected HTTP status 400", HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
