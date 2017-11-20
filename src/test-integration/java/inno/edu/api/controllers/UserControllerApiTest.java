package inno.edu.api.controllers;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static inno.edu.api.support.Payloads.loginUserPayload;
import static inno.edu.api.support.Payloads.postUserPayload;
import static inno.edu.api.support.Payloads.putUserPayload;
import static inno.edu.api.support.UserFactory.alan;
import static inno.edu.api.support.ProfileFactory.alanProfile;
import static inno.edu.api.support.UserFactory.fei;
import static inno.edu.api.support.UserFactory.feiCredentials;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.UserFactory.gustavo;
import static inno.edu.api.support.UserFactory.updatedFei;
import static org.hamcrest.Matchers.hasItems;
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
                .andExpect(jsonPath("$._embedded.userResourceList[*].photoUrl", hasItems(fei().getPhotoUrl(), alan().getPhotoUrl())))
                .andExpect(jsonPath("$._embedded.userResourceList[*].isMentor", hasItems(fei().getIsMentor(), alan().getIsMentor())));
    }

    @Test
    public void shouldGetUserById() throws Exception {
        this.mockMvc.perform(get("/api/users/" + fei().getId().toString())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(fei().getId().toString())))
                .andExpect(jsonPath("$.firstName", is(fei().getFirstName())))
                .andExpect(jsonPath("$.lastName", is(fei().getLastName())))
                .andExpect(jsonPath("$.username", is(fei().getUsername())))
                .andExpect(jsonPath("$.photoUrl", is(fei().getPhotoUrl())))
                .andExpect(jsonPath("$.isMentor", is(fei().getIsMentor())));
    }

    @Test
    public void shouldGetMentorProfileByUserId() throws Exception {
        this.mockMvc.perform(get("/api/users/" + fei().getId().toString() + "/profile")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(feiProfile().getId().toString())))
                .andExpect(jsonPath("$.mentorId", is(feiProfile().getMentorId().toString())))
                .andExpect(jsonPath("$.schoolId", is(feiProfile().getSchoolId().toString())))
                .andExpect(jsonPath("$.email", is(feiProfile().getEmail())))
                .andExpect(jsonPath("$.status", is(feiProfile().getStatus().toString())));
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
    public void shouldLoginUser() throws Exception {
        this.mockMvc.perform(
                post("/api/users/login")
                        .content(loginUserPayload(feiCredentials()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldCreateNewUser() throws Exception {
        this.mockMvc.perform(
                post("/api/users")
                        .content(postUserPayload(gustavo()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldNotCreateNewUserIfUserNameAlreadyExists() throws Exception {
        this.mockMvc.perform(
                post("/api/users")
                        .content(postUserPayload(fei()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        this.mockMvc.perform(
                put("/api/users/" + fei().getId())
                        .content(putUserPayload(updatedFei()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(fei().getId().toString())))
                .andExpect(jsonPath("$.firstName", is(updatedFei().getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedFei().getLastName())))
                .andExpect(jsonPath("$.photoUrl", is(updatedFei().getPhotoUrl())))
                .andExpect(jsonPath("$.isMentor", is(updatedFei().getIsMentor())));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        this.mockMvc.perform(
                delete("/api/users/" + fei().getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldApproveUserProfile() throws Exception {
        this.mockMvc.perform(
                put("/api/users/" + gustavo().getId() + "/approve"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldRejectUserProfile() throws Exception {
        this.mockMvc.perform(
                put("/api/users/" + gustavo().getId() + "/reject"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
