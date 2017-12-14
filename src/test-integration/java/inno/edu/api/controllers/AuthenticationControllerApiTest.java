package inno.edu.api.controllers;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static inno.edu.api.support.Payloads.loginUserPayload;
import static inno.edu.api.support.UserFactory.feiCredentials;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthenticationControllerApiTest extends ApiTest {
    @Test
    public void shouldLoginUser() throws Exception {
        this.mockMvc.perform(
                post("/api/auth/login")
                        .content(loginUserPayload(feiCredentials()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
