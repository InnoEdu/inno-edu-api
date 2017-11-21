package inno.edu.api.controllers;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static inno.edu.api.support.Payloads.postMenteeProfilePayload;
import static inno.edu.api.support.Payloads.putMenteeProfilePayload;
import static inno.edu.api.support.ProfileFactory.alanProfile;
import static inno.edu.api.support.ProfileFactory.createAlanProfileRequest;
import static inno.edu.api.support.ProfileFactory.createTuanyProfileRequest;
import static inno.edu.api.support.ProfileFactory.tuanyProfile;
import static inno.edu.api.support.ProfileFactory.updateAlanProfileRequest;
import static inno.edu.api.support.ProfileFactory.updatedAlanProfile;
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

public class MenteeProfileControllerApiTest extends ApiTest {
    @Test
    public void shouldListProfiles() throws Exception {
        this.mockMvc.perform(get("/api/mentee-profiles")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.menteeProfileResourceList[*].id", containsInAnyOrder(alanProfile().getId().toString())))
                .andExpect(jsonPath("$._embedded.menteeProfileResourceList[*].menteeId", containsInAnyOrder(alanProfile().getMenteeId().toString())))
                .andExpect(jsonPath("$._embedded.menteeProfileResourceList[*].description", containsInAnyOrder(alanProfile().getDescription())));
    }

    @Test
    public void shouldGetProfileById() throws Exception {
        this.mockMvc.perform(get("/api/mentee-profiles/" + alanProfile().getId().toString())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(alanProfile().getId().toString())))
                .andExpect(jsonPath("$.menteeId", is(alanProfile().getMenteeId().toString())))
                .andExpect(jsonPath("$.description", is(alanProfile().getDescription())));
    }

    @Test
    public void shouldCreateNewProfile() throws Exception {
        this.mockMvc.perform(
                post("/api/mentee-profiles")
                        .content(postMenteeProfilePayload(createTuanyProfileRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", not(tuanyProfile().getId().toString())))
                .andExpect(jsonPath("$.menteeId", is(tuanyProfile().getMenteeId().toString())))
                .andExpect(jsonPath("$.description", is(tuanyProfile().getDescription())));
    }

    @Test
    public void shouldMenteeShouldNotHaveMultipleProfiles() throws Exception {
        this.mockMvc.perform(
                post("/api/mentee-profiles")
                        .content(postMenteeProfilePayload(createAlanProfileRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldUpdateProfile() throws Exception {
        this.mockMvc.perform(
                put("/api/mentee-profiles/" + alanProfile().getId())
                        .content(putMenteeProfilePayload(updateAlanProfileRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(updatedAlanProfile().getId().toString())))
                .andExpect(jsonPath("$.menteeId", is(updatedAlanProfile().getMenteeId().toString())))
                .andExpect(jsonPath("$.description", is(updatedAlanProfile().getDescription())));
    }

    @Test
    public void shouldDeleteProfile() throws Exception {
        this.mockMvc.perform(
                delete("/api/mentee-profiles/" + alanProfile().getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
