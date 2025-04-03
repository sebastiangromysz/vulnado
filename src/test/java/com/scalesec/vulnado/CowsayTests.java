package com.scalesec.vulnado;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VulnadoApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void Cowsay_Run_ShouldReturnExpectedOutput() throws Exception {
        // Mocking the ProcessBuilder and Process
        ProcessBuilder processBuilderMock = Mockito.mock(ProcessBuilder.class);
        Process processMock = Mockito.mock(Process.class);

        // Mocking the InputStreamReader and BufferedReader
        BufferedReader bufferedReaderMock = new BufferedReader(new StringReader("Mocked Cowsay Output\n"));

        Mockito.when(processBuilderMock.start()).thenReturn(processMock);
        Mockito.when(processMock.getInputStream()).thenReturn(new InputStreamReader(bufferedReaderMock).getInputStream());

        // Mocking the command execution
        String input = "Hello, World!";
        String expectedCommand = "/usr/games/cowsay '" + input + "'";
        Mockito.doNothing().when(processBuilderMock).command("bash", "-c", expectedCommand);

        // Running the method
        String result = Cowsay.run(input);

        // Asserting the result
        assertEquals("Mocked Cowsay Output\n", result, "The output should match the mocked cowsay output.");
    }

    @Test
    public void Cowsay_Run_ShouldHandleExceptionGracefully() {
        // Mocking the ProcessBuilder to throw an exception
        ProcessBuilder processBuilderMock = Mockito.mock(ProcessBuilder.class);
        Mockito.when(processBuilderMock.start()).thenThrow(new RuntimeException("Mocked Exception"));

        // Running the method
        String input = "Hello, World!";
        String result = Cowsay.run(input);

        // Asserting the result
        assertTrue(result.isEmpty(), "The output should be empty when an exception occurs.");
    }
}
