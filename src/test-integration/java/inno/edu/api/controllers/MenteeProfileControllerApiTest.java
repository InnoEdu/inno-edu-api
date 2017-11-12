package inno.edu.api.controllers;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static inno.edu.api.factories.UserFactory.alanProfile;
import static inno.edu.api.factories.UserFactory.gustavoProfile;
import static inno.edu.api.factories.UserFactory.menteeProfilePayload;
import static inno.edu.api.factories.UserFactory.updatedAlanProfile;
import static org.hamcrest.Matchers.containsInAnyOrder;
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
                .andExpect(jsonPath("$._embedded.menteeProfileResourceList[*].email", containsInAnyOrder(alanProfile().getEmail())));
    }

    @Test
    public void shouldGetProfileById() throws Exception {
        this.mockMvc.perform(get("/api/mentee-profiles/" + alanProfile().getId().toString())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(alanProfile().getId().toString())))
                .andExpect(jsonPath("$.menteeId", is(alanProfile().getMenteeId().toString())))
                .andExpect(jsonPath("$.email", is(alanProfile().getEmail())));
    }

    @Test
    public void shouldGetProfileByMenteeId() throws Exception {
        this.mockMvc.perform(get("/api/mentee-profiles/mentee/" + alanProfile().getMenteeId().toString())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(alanProfile().getId().toString())))
                .andExpect(jsonPath("$.menteeId", is(alanProfile().getMenteeId().toString())))
                .andExpect(jsonPath("$.email", is(alanProfile().getEmail())));
    }

    @Test
    public void shouldCreateNewProfile() throws Exception {
        this.mockMvc.perform(
                post("/api/mentee-profiles")
                        .content(menteeProfilePayload(gustavoProfile()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldMenteeShouldNotHaveMultipleProfiles() throws Exception {
        this.mockMvc.perform(
                post("/api/mentee-profiles")
                        .content(menteeProfilePayload(alanProfile()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldUpdateProfile() throws Exception {
        this.mockMvc.perform(
                put("/api/mentee-profiles/" + alanProfile().getId())
                        .content(menteeProfilePayload(updatedAlanProfile()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(alanProfile().getId().toString())))
                .andExpect(jsonPath("$.menteeId", is(updatedAlanProfile().getMenteeId().toString())))
                .andExpect(jsonPath("$.email", is(updatedAlanProfile().getEmail())));
    }

    @Test
    public void shouldDeleteProfile() throws Exception {
        this.mockMvc.perform(
                delete("/api/mentee-profiles/" + alanProfile().getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
