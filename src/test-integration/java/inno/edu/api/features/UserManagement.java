package inno.edu.api.features;

import inno.edu.api.IntegrationTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static java.lang.String.format;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserManagement extends IntegrationTest {
    @Test
    public void shouldListUsers() throws Exception {
        this.mockMvc.perform(get("/api/users")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.userResourceList[0].id", is("841b43e1-08be-4401-968f-6ee45370a973")))
                .andExpect(jsonPath("$._embedded.userResourceList[0].firstName", is("Dave")))
                .andExpect(jsonPath("$._embedded.userResourceList[0].lastName", is("Syer")));
    }

    @Test
    public void shouldGetUserById() throws Exception {
        this.mockMvc.perform(get("/api/users/841b43e1-08be-4401-968f-6ee45370a973")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("841b43e1-08be-4401-968f-6ee45370a973")))
                .andExpect(jsonPath("$.firstName", is("Dave")))
                .andExpect(jsonPath("$.lastName", is("Syer")));
    }

    @Test
    public void shouldCreateNewUser() throws Exception {
        this.mockMvc.perform(
                post("/api/users")
                        .content(format("{\"firstName\": \"%s\", \"lastName\": \"%s\"}", "Gustavo", "Domenico"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}
