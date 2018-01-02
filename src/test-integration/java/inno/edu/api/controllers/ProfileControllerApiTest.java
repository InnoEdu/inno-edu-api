package inno.edu.api.controllers;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static inno.edu.api.support.Payloads.postProfilePayload;
import static inno.edu.api.support.Payloads.putProfilePayload;
import static inno.edu.api.support.ProfileFactory.alanProfile;
import static inno.edu.api.support.ProfileFactory.createNewAlanProfileRequest;
import static inno.edu.api.support.ProfileFactory.createNewGustavoProfileRequest;
import static inno.edu.api.support.ProfileFactory.newAlanProfile;
import static inno.edu.api.support.ProfileFactory.newGustavoProfile;
import static inno.edu.api.support.ProfileFactory.updateNewAlanProfileRequest;
import static inno.edu.api.support.ProfileFactory.updatedNewAlanProfile;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProfileControllerApiTest extends ApiTest {
    @Test
    public void shouldListProfiles() throws Exception {
        this.mockMvc.perform(get("/api/profiles")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.profileResourceList[*].id", containsInAnyOrder(newAlanProfile().getId().toString())))
                .andExpect(jsonPath("$._embedded.profileResourceList[*].userId", containsInAnyOrder(newAlanProfile().getUserId().toString())))
                .andExpect(jsonPath("$._embedded.profileResourceList[*].description", containsInAnyOrder(newAlanProfile().getDescription())));
    }

    @Test
    public void shouldGetProfileById() throws Exception {
        this.mockMvc.perform(get("/api/profiles/" + newAlanProfile().getId().toString())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(newAlanProfile().getId().toString())))
                .andExpect(jsonPath("$.userId", is(newAlanProfile().getUserId().toString())))
                .andExpect(jsonPath("$.description", is(newAlanProfile().getDescription())));
    }

    @Test
    public void shouldCreateNewProfile() throws Exception {
        this.mockMvc.perform(
                post("/api/profiles")
                        .content(postProfilePayload(createNewGustavoProfileRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", not(newGustavoProfile().getId().toString())))
                .andExpect(jsonPath("$.userId", is(newGustavoProfile().getUserId().toString())))
                .andExpect(jsonPath("$.description", is(newGustavoProfile().getDescription())));
    }

    @Test
    public void userShouldNotHaveMultipleProfiles() throws Exception {
        this.mockMvc.perform(
                post("/api/profiles")
                        .content(postProfilePayload(createNewAlanProfileRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldUpdateProfile() throws Exception {
        this.mockMvc.perform(
                put("/api/profiles/" + alanProfile().getId())
                        .content(putProfilePayload(updateNewAlanProfileRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(newAlanProfile().getId().toString())))
                .andExpect(jsonPath("$.userId", is(newAlanProfile().getUserId().toString())))
                .andExpect(jsonPath("$.description", is(updatedNewAlanProfile().getDescription())));
    }

    @Test
    public void shouldDeleteProfile() throws Exception {
        this.mockMvc.perform(
                delete("/api/profiles/" + newAlanProfile().getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
