package inno.edu.api.controllers;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static inno.edu.api.common.ModelFactories.otherUser;
import static inno.edu.api.common.ModelFactories.updatedUser;
import static inno.edu.api.common.ModelFactories.user;
import static inno.edu.api.common.ModelFactories.userPayload;
import static java.lang.String.format;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerApiTest extends ApiTest {
    @Test
    public void shouldListUsers() throws Exception {
        this.mockMvc.perform(get("/api/users")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.userResourceList[*].id", containsInAnyOrder(user().getId().toString(), otherUser().getId().toString())))
                .andExpect(jsonPath("$._embedded.userResourceList[*].firstName", containsInAnyOrder(user().getFirstName(), otherUser().getFirstName())))
                .andExpect(jsonPath("$._embedded.userResourceList[*].lastName", containsInAnyOrder(user().getLastName(), otherUser().getLastName())));
    }

    @Test
    public void shouldGetUserById() throws Exception {
        this.mockMvc.perform(get("/api/users/" + user().getId().toString())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(is(user().getId().toString()))))
                .andExpect(jsonPath("$.firstName", is(user().getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user().getLastName())));
    }

    @Test
    public void shouldCreateNewUser() throws Exception {
        this.mockMvc.perform(
                post("/api/users")
                        .content(format(userPayload(), user().getFirstName(), user().getLastName()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        this.mockMvc.perform(
                put("/api/users/" + user().getId())
                        .content(format(userPayload(), updatedUser().getFirstName(), updatedUser().getLastName()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(is(user().getId().toString()))))
                .andExpect(jsonPath("$.firstName", is(updatedUser().getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedUser().getLastName())));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        this.mockMvc.perform(
                delete("/api/users/" + user().getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
