package inno.edu.api.features;

import inno.edu.api.IntegrationTest;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserManagement extends IntegrationTest {
    @Test
    public void shouldListUsers() throws Exception {
        this.mockMvc.perform(get("/api/users")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.userResourceList[0].id", is(1)))
                .andExpect(jsonPath("$._embedded.userResourceList[0].firstName", is("Dave")))
                .andExpect(jsonPath("$._embedded.userResourceList[0].lastName", is("Syer")));
    }

    @Test
    public void shouldGetUserById() throws Exception {
        this.mockMvc.perform(get("/api/users/1")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Dave")))
                .andExpect(jsonPath("$.lastName", is("Syer")));
    }
}
