package inno.edu.api.controllers;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static inno.edu.api.domain.profile.models.ProfileStatus.CREATED;
import static inno.edu.api.support.Payloads.postMentorProfilePayload;
import static inno.edu.api.support.Payloads.putMentorProfilePayload;
import static inno.edu.api.support.ProfileFactory.createFeiProfileRequest;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.ProfileFactory.gustavoProfile;
import static inno.edu.api.support.ProfileFactory.updateFeiProfileRequest;
import static inno.edu.api.support.ProfileFactory.updatedFeiProfile;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;
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
                .andExpect(jsonPath("$._embedded.mentorProfileResourceList[*].id", hasItems(feiProfile().getId().toString())))
                .andExpect(jsonPath("$._embedded.mentorProfileResourceList[*].mentorId", hasItems(feiProfile().getMentorId().toString())))
                .andExpect(jsonPath("$._embedded.mentorProfileResourceList[*].schoolId", hasItems(feiProfile().getSchoolId().toString())))
                .andExpect(jsonPath("$._embedded.mentorProfileResourceList[*].email", hasItems(feiProfile().getEmail())))
                .andExpect(jsonPath("$._embedded.mentorProfileResourceList[*].description", hasItems(feiProfile().getDescription())))
                .andExpect(jsonPath("$._embedded.mentorProfileResourceList[*].status", hasItems(feiProfile().getStatus().toString())));
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
                        .content(postMentorProfilePayload(createFeiProfileRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", not(feiProfile().getId().toString())))
                .andExpect(jsonPath("$.mentorId", is(feiProfile().getMentorId().toString())))
                .andExpect(jsonPath("$.schoolId", is(feiProfile().getSchoolId().toString())))
                .andExpect(jsonPath("$.email", is(feiProfile().getEmail())))
                .andExpect(jsonPath("$.description", is(feiProfile().getDescription())))
                .andExpect(jsonPath("$.status", is(CREATED.toString())));
    }

    @Test
    public void shouldUpdateProfile() throws Exception {
        this.mockMvc.perform(
                put("/api/mentor-profiles/" + feiProfile().getId())
                        .content(putMentorProfilePayload(updateFeiProfileRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(feiProfile().getId().toString())))
                .andExpect(jsonPath("$.mentorId", is(feiProfile().getMentorId().toString())))
                .andExpect(jsonPath("$.schoolId", is(feiProfile().getSchoolId().toString())))
                .andExpect(jsonPath("$.email", is(feiProfile().getEmail())))
                .andExpect(jsonPath("$.description", is(updatedFeiProfile().getDescription())))
                .andExpect(jsonPath("$.status", is(feiProfile().getStatus().toString())));
    }

    @Test
    public void shouldDeleteProfile() throws Exception {
        this.mockMvc.perform(
                delete("/api/mentor-profiles/" + feiProfile().getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldApproveProfile() throws Exception {
        this.mockMvc.perform(
                put("/api/mentor-profiles/" + gustavoProfile().getId() + "/approve"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldRejectProfile() throws Exception {
        this.mockMvc.perform(
                put("/api/mentor-profiles/" + gustavoProfile().getId() + "/reject"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
