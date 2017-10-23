package uk.ac.qub.csc3045.api.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import uk.ac.qub.csc3045.api.service.AuthenticationService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {
/*

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AuthenticationService authenticationServiceMock;

    @Test
    public void loginSuccessful() throws Exception {
        String username = "russell.kane";
        String password = "intense";

        String expectedResponse = "Login successful!";

        given(authenticationServiceMock.register(username, password))
                .willReturn(expectedResponse);

        mockMvc.perform(post("/login").param("username", username).param("password", password))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }

    @Test
    public void loginFailed() throws Exception {
        String username = "ji.ming";
        String password = "ok";

        String expectedResponse = "Login failed!";

        given(authenticationServiceMock.register(username, password))
                .willReturn(expectedResponse);

        mockMvc.perform(post("/login").param("username", username).param("password", password))
                .andExpect(content().string(expectedResponse));
    }
*/

}