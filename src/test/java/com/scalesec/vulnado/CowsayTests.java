package com.scalesec.vulnado;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;
import org.mockito.Mockito;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VulnadoApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void Cowsay_Run_ShouldReturnExpectedOutput() {
        // Mocking the ProcessBuilder and Process
        ProcessBuilder mockProcessBuilder = Mockito.mock(ProcessBuilder.class);
        Process mockProcess = Mockito.mock(Process.class);

        try {
            // Mocking the InputStreamReader to simulate the output of the command
            String expectedOutput = "Mocked Cowsay Output\n";
            ByteArrayInputStream inputStream = new ByteArrayInputStream(expectedOutput.getBytes());
            BufferedReader mockReader = new BufferedReader(new InputStreamReader(inputStream));

            Mockito.when(mockProcessBuilder.start()).thenReturn(mockProcess);
            Mockito.when(mockProcess.getInputStream()).thenReturn(inputStream);

            // Using the mocked objects
            String actualOutput = Cowsay.run("Hello");

            // Asserting the output
            assertEquals("The output should match the mocked output", expectedOutput, actualOutput);
        } catch (Exception e) {
            fail("Exception should not be thrown during the test");
        }
    }

    @Test
    public void Cowsay_Run_ShouldHandleExceptionGracefully() {
        // Mocking the ProcessBuilder to throw an exception
        ProcessBuilder mockProcessBuilder = Mockito.mock(ProcessBuilder.class);

        try {
            Mockito.when(mockProcessBuilder.start()).thenThrow(new RuntimeException("Mocked Exception"));

            // Using the mocked objects
            String actualOutput = Cowsay.run("Hello");

            // Asserting the output
            assertEquals("The output should be empty when an exception occurs", "", actualOutput);
        } catch (Exception e) {
            fail("Exception should not be thrown during the test");
        }
    }
}
