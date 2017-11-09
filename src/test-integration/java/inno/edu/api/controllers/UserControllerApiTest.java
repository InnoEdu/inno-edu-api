package inno.edu.api.controllers;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static inno.edu.api.common.Builders.otherUser;
import static inno.edu.api.common.Builders.user;
import static inno.edu.api.common.Builders.userPayload;
import static java.lang.String.format;
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
                .andExpect(jsonPath("$._embedded.userResourceList[0].id", is(user().getId().toString())))
                .andExpect(jsonPath("$._embedded.userResourceList[0].firstName", is(user().getFirstName())))
                .andExpect(jsonPath("$._embedded.userResourceList[0].lastName", is(user().getLastName())));
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
                        .content(format(userPayload(), otherUser().getFirstName(), otherUser().getLastName()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(is(user().getId().toString()))))
                .andExpect(jsonPath("$.firstName", is(otherUser().getFirstName())))
                .andExpect(jsonPath("$.lastName", is(otherUser().getLastName())));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        this.mockMvc.perform(
                delete("/api/users/" + user().getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
