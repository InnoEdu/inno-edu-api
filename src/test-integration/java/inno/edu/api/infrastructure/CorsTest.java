package inno.edu.api.infrastructure;

import inno.edu.api.ApiTest;
import org.junit.Test;

import static inno.edu.api.support.UserFactory.fei;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CorsTest extends ApiTest {
    @Test
    public void shouldBeAbleToGetUserFromDifferentOrigin() throws Exception {
        this.mockMvc.perform(options("/api/users/" + fei().getId().toString())
                .header("Access-Control-Request-Method", "GET")
                .header("Origin", "http://www.someurl.com")).andDo(print())
                .andExpect(status().isOk());
    }
}
