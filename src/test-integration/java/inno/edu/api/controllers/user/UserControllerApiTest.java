package inno.edu.api.controllers.user;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static inno.edu.api.support.Payloads.postUserPayload;
import static inno.edu.api.support.Payloads.putUserPayload;
import static inno.edu.api.support.ProfileFactory.alanProfile;
import static inno.edu.api.support.UserFactory.alan;
import static inno.edu.api.support.UserFactory.createFeiRequest;
import static inno.edu.api.support.UserFactory.createGustavoRequest;
import static inno.edu.api.support.UserFactory.fei;
import static inno.edu.api.support.UserFactory.gustavo;
import static inno.edu.api.support.UserFactory.updateFeiRequest;
import static inno.edu.api.support.UserFactory.updatedFei;
import static inno.edu.api.support.UserFactory.userToDelete;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
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
                .andExpect(jsonPath("$._embedded.userResourceList[*].id", hasItems(fei().getId().toString(), alan().getId().toString())))
                .andExpect(jsonPath("$._embedded.userResourceList[*].firstName", hasItems(fei().getFirstName(), alan().getFirstName())))
                .andExpect(jsonPath("$._embedded.userResourceList[*].lastName", hasItems(fei().getLastName(), alan().getLastName())))
                .andExpect(jsonPath("$._embedded.userResourceList[*].username", hasItems(fei().getUsername(), alan().getUsername())))
                .andExpect(jsonPath("$._embedded.userResourceList[*].email", hasItems(fei().getEmail(), alan().getEmail())));
    }

    @Test
    public void shouldGetUserById() throws Exception {
        this.mockMvc.perform(get("/api/users/" + fei().getId().toString())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(fei().getId().toString())))
                .andExpect(jsonPath("$.firstName", is(fei().getFirstName())))
                .andExpect(jsonPath("$.lastName", is(fei().getLastName())))
                .andExpect(jsonPath("$.username", is(fei().getUsername())))
                .andExpect(jsonPath("$.deviceId", is(fei().getDeviceId())))
                .andExpect(jsonPath("$.email", is(fei().getEmail())));
    }

    @Test
    public void shouldGetProfileByUserId() throws Exception {
        this.mockMvc.perform(get("/api/users/" + alan().getId().toString() + "/profile")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(alanProfile().getId().toString())))
                .andExpect(jsonPath("$.userId", is(alanProfile().getUserId().toString())));
    }

    @Test
    public void shouldCreateNewUser() throws Exception {
        this.mockMvc.perform(
                post("/api/users")
                        .content(postUserPayload(createGustavoRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", not(nullValue())))
                .andExpect(jsonPath("$.user.id", not(fei().getId().toString())))
                .andExpect(jsonPath("$.user.firstName", is(gustavo().getFirstName())))
                .andExpect(jsonPath("$.user.lastName", is(gustavo().getLastName())))
                .andExpect(jsonPath("$.user.username", is(gustavo().getUsername())))
                .andExpect(jsonPath("$.user.deviceId", is(gustavo().getDeviceId())))
                .andExpect(jsonPath("$.user.email", is(gustavo().getEmail())));
    }

    @Test
    public void shouldNotCreateNewUserIfUsernameAlreadyExists() throws Exception {
        this.mockMvc.perform(
                post("/api/users")
                        .content(postUserPayload(createFeiRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        this.mockMvc.perform(
                put("/api/users/" + fei().getId())
                        .content(putUserPayload(updateFeiRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(updatedFei().getId().toString())))
                .andExpect(jsonPath("$.firstName", is(updatedFei().getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedFei().getLastName())))
                .andExpect(jsonPath("$.username", is(updatedFei().getUsername())))
                .andExpect(jsonPath("$.deviceId", is(updatedFei().getDeviceId())))
                .andExpect(jsonPath("$.email", is(updatedFei().getEmail())));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        this.mockMvc.perform(
                delete("/api/users/" + userToDelete().getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
