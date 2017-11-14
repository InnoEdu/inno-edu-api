package inno.edu.api.controllers;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static inno.edu.api.support.UserFactory.feiProfile;
import static inno.edu.api.support.UserFactory.updatedFeiProfile;
import static inno.edu.api.support.Payloads.postMentorProfilePayload;
import static inno.edu.api.support.Payloads.putMentorProfilePayload;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MentorProfileControllerApiTest extends ApiTest {
    @Test
    public void shouldListProfiles() throws Exception {
        this.mockMvc.perform(get("/api/mentor-profiles")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.mentorProfileResourceList[*].id", containsInAnyOrder(feiProfile().getId().toString())))
                .andExpect(jsonPath("$._embedded.mentorProfileResourceList[*].mentorId", containsInAnyOrder(feiProfile().getMentorId().toString())))
                .andExpect(jsonPath("$._embedded.mentorProfileResourceList[*].schoolId", containsInAnyOrder(feiProfile().getSchoolId().toString())))
                .andExpect(jsonPath("$._embedded.mentorProfileResourceList[*].email", containsInAnyOrder(feiProfile().getEmail())))
                .andExpect(jsonPath("$._embedded.mentorProfileResourceList[*].description", containsInAnyOrder(feiProfile().getDescription())))
                .andExpect(jsonPath("$._embedded.mentorProfileResourceList[*].status", containsInAnyOrder(feiProfile().getStatus().toString())));
    }

    @Test
    public void shouldGetProfileById() throws Exception {
        this.mockMvc.perform(get("/api/mentor-profiles/" + feiProfile().getId().toString())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(feiProfile().getId().toString())))
                .andExpect(jsonPath("$.mentorId", is(feiProfile().getMentorId().toString())))
                .andExpect(jsonPath("$.schoolId", is(feiProfile().getSchoolId().toString())))
                .andExpect(jsonPath("$.email", is(feiProfile().getEmail())))
                .andExpect(jsonPath("$.description", is(feiProfile().getDescription())))
                .andExpect(jsonPath("$.status", is(feiProfile().getStatus().toString())));
    }

    @Test
    public void shouldCreateNewProfile() throws Exception {
        this.mockMvc.perform(
                post("/api/mentor-profiles")
                        .content(postMentorProfilePayload(feiProfile()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldUpdateProfile() throws Exception {
        this.mockMvc.perform(
                put("/api/mentor-profiles/" + feiProfile().getId())
                        .content(putMentorProfilePayload(updatedFeiProfile()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(updatedFeiProfile().getId().toString())))
                .andExpect(jsonPath("$.mentorId", is(updatedFeiProfile().getMentorId().toString())))
                .andExpect(jsonPath("$.email", is(updatedFeiProfile().getEmail())))
                .andExpect(jsonPath("$.description", is(updatedFeiProfile().getDescription())));
    }

    @Test
    public void shouldDeleteProfile() throws Exception {
        this.mockMvc.perform(
                delete("/api/mentor-profiles/" + feiProfile().getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
