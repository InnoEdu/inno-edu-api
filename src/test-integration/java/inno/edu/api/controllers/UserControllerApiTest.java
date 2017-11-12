package inno.edu.api.controllers;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static inno.edu.api.factories.UserFactory.alan;
import static inno.edu.api.factories.UserFactory.alanProfile;
import static inno.edu.api.factories.UserFactory.fei;
import static inno.edu.api.factories.UserFactory.feiProfile;
import static inno.edu.api.factories.UserFactory.gustavo;
import static inno.edu.api.factories.UserFactory.updatedFei;
import static inno.edu.api.factories.UserFactory.userPayload;
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
                .andExpect(jsonPath("$._embedded.userResourceList[*].id", containsInAnyOrder(fei().getId().toString(), alan().getId().toString())))
                .andExpect(jsonPath("$._embedded.userResourceList[*].firstName", containsInAnyOrder(fei().getFirstName(), alan().getFirstName())))
                .andExpect(jsonPath("$._embedded.userResourceList[*].lastName", containsInAnyOrder(fei().getLastName(), alan().getLastName())))
                .andExpect(jsonPath("$._embedded.userResourceList[*].userName", containsInAnyOrder(fei().getUserName(), alan().getUserName())))
                .andExpect(jsonPath("$._embedded.userResourceList[*].isMentor", containsInAnyOrder(fei().getIsMentor(), alan().getIsMentor())));
    }

    @Test
    public void shouldGetUserById() throws Exception {
        this.mockMvc.perform(get("/api/users/" + fei().getId().toString())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(fei().getId().toString())))
                .andExpect(jsonPath("$.firstName", is(fei().getFirstName())))
                .andExpect(jsonPath("$.lastName", is(fei().getLastName())))
                .andExpect(jsonPath("$.userName", is(fei().getUserName())))
                .andExpect(jsonPath("$.isMentor", is(fei().getIsMentor())));
    }

    @Test
    public void shouldGetMentorProfileByUserId() throws Exception {
        this.mockMvc.perform(get("/api/users/" + fei().getId().toString() + "/profile")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(feiProfile().getId().toString())))
                .andExpect(jsonPath("$.mentorId", is(feiProfile().getMentorId().toString())))
                .andExpect(jsonPath("$.universityId", is(feiProfile().getUniversityId().toString())))
                .andExpect(jsonPath("$.email", is(feiProfile().getEmail())))
                .andExpect(jsonPath("$.isActive", is(feiProfile().getIsActive())));
    }

    @Test
    public void shouldGetMenteeProfileByUserId() throws Exception {
        this.mockMvc.perform(get("/api/users/" + alan().getId().toString() + "/profile")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(alanProfile().getId().toString())))
                .andExpect(jsonPath("$.menteeId", is(alanProfile().getMenteeId().toString())))
                .andExpect(jsonPath("$.email", is(alanProfile().getEmail())));
    }

    @Test
    public void shouldCreateNewUser() throws Exception {
        this.mockMvc.perform(
                post("/api/users")
                        .content(userPayload(gustavo()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldNotCreateNewUserIfUserNameAlreadyExists() throws Exception {
        this.mockMvc.perform(
                post("/api/users")
                        .content(userPayload(fei()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        this.mockMvc.perform(
                put("/api/users/" + fei().getId())
                        .content(userPayload(updatedFei()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(fei().getId().toString())))
                .andExpect(jsonPath("$.firstName", is(updatedFei().getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedFei().getLastName())))
                .andExpect(jsonPath("$.userName", is(updatedFei().getUserName())))
                .andExpect(jsonPath("$.isMentor", is(updatedFei().getIsMentor())));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        this.mockMvc.perform(
                delete("/api/users/" + fei().getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
