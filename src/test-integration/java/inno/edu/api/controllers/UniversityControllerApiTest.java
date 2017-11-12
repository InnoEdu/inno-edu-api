package inno.edu.api.controllers;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static inno.edu.api.support.UniversityFactory.berkeley;
import static inno.edu.api.support.UniversityFactory.stanford;
import static inno.edu.api.support.UniversityFactory.updatedStanford;
import static inno.edu.api.support.UserFactory.feiProfile;
import static inno.edu.api.support.Payloads.postUniversityPayload;
import static inno.edu.api.support.Payloads.putUniversityPayload;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UniversityControllerApiTest extends ApiTest {
    @Test
    public void shouldListUniversities() throws Exception {
        this.mockMvc.perform(get("/api/universities")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.universityResourceList[*].id", containsInAnyOrder(stanford().getId().toString(), berkeley().getId().toString())))
                .andExpect(jsonPath("$._embedded.universityResourceList[*].name", containsInAnyOrder(stanford().getName(), berkeley().getName())));
    }

    @Test
    public void shouldListUniversityMentorsProfile() throws Exception {
        this.mockMvc.perform(get("/api/universities/" + stanford().getId() + "/mentors")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.mentorProfileResourceList[*].id", containsInAnyOrder(feiProfile().getId().toString())))
                .andExpect(jsonPath("$._embedded.mentorProfileResourceList[*].mentorId", containsInAnyOrder(feiProfile().getMentorId().toString())))
                .andExpect(jsonPath("$._embedded.mentorProfileResourceList[*].universityId", containsInAnyOrder(feiProfile().getUniversityId().toString())))
                .andExpect(jsonPath("$._embedded.mentorProfileResourceList[*].email", containsInAnyOrder(feiProfile().getEmail())))
                .andExpect(jsonPath("$._embedded.mentorProfileResourceList[*].status", containsInAnyOrder(feiProfile().getStatus().toString())));
    }

    @Test
    public void shouldGetUniversityById() throws Exception {
        this.mockMvc.perform(get("/api/universities/" + stanford().getId().toString())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(stanford().getId().toString())))
                .andExpect(jsonPath("$.name", is(stanford().getName())));
    }

    @Test
    public void shouldCreateNewUniversity() throws Exception {
        this.mockMvc.perform(
                post("/api/universities")
                        .content(postUniversityPayload(stanford()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldUpdateUniversity() throws Exception {
        this.mockMvc.perform(
                put("/api/universities/" + stanford().getId())
                        .content(putUniversityPayload(updatedStanford()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(stanford().getId().toString())))
                .andExpect(jsonPath("$.name", is(updatedStanford().getName())));
    }

    @Test
    public void shouldDeleteUniversity() throws Exception {
        this.mockMvc.perform(
                delete("/api/universities/" + stanford().getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
