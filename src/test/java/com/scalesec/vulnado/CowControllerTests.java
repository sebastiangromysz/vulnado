package com.scalesec.vulnado;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CowControllerTests {

    @Autowired
    private MockMvc mockMvc;

    // Test to verify the default response of the cowsay endpoint
    @Test
    public void cowsay_DefaultInput_ShouldReturnDefaultMessage() throws Exception {
        mockMvc.perform(get("/cowsay")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("I love Linux!"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }

    // Test to verify the response of the cowsay endpoint with custom input
    @Test
    public void cowsay_CustomInput_ShouldReturnCustomMessage() throws Exception {
        String customMessage = "Hello, World!";
        mockMvc.perform(get("/cowsay")
                .param("input", customMessage)
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string(customMessage))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }

    // Test to verify the response of the cowsay endpoint with empty input
    @Test
    public void cowsay_EmptyInput_ShouldReturnDefaultMessage() throws Exception {
        mockMvc.perform(get("/cowsay")
                .param("input", "")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("I love Linux!"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }
}

//BEGIN: Work/DemoTestCreator/2025-04-03__09-58-30.194__GenerateTests/Input/Existing_Tests/VulnadoApplicationTests.java
package com.scalesec.vulnado;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VulnadoApplicationTests {

	@Test
	public void contextLoads() {
	}

}


//END: Work/DemoTestCreator/2025-04-03__09-58-30.194__GenerateTests/Input/Existing_Tests/VulnadoApplicationTests.java
