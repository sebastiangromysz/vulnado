package com.scalesec.vulnado;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VulnadoApplicationTests {

    @Test
    public void contextLoads() {
        // Test to ensure the application context loads successfully
    }

    @Test
    public void main_ShouldInitializeApplicationContext() {
        // Mocking Postgres setup method
        Postgres mockPostgres = Mockito.mock(Postgres.class);
        Mockito.doNothing().when(mockPostgres).setup();

        // Mocking SpringApplication.run
        SpringApplication mockSpringApplication = Mockito.mock(SpringApplication.class);
        Mockito.doNothing().when(mockSpringApplication).run(Mockito.any(Class.class), Mockito.any(String[].class));

        // Running the main method
        VulnadoApplication.main(new String[]{});

        // Verifying that Postgres.setup() was called
        Mockito.verify(mockPostgres).setup();

        // Verifying that SpringApplication.run() was called
        Mockito.verify(mockSpringApplication).run(VulnadoApplication.class, new String[]{});
    }
}
