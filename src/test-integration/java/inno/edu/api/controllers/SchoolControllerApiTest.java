package inno.edu.api.controllers;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static inno.edu.api.support.Payloads.postSchoolPayload;
import static inno.edu.api.support.Payloads.putSchoolPayload;
import static inno.edu.api.support.SchoolFactory.berkeley;
import static inno.edu.api.support.SchoolFactory.stanford;
import static inno.edu.api.support.SchoolFactory.updatedStanford;
import static inno.edu.api.support.UserFactory.feiProfile;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SchoolControllerApiTest extends ApiTest {
    @Test
    public void shouldListSchools() throws Exception {
        this.mockMvc.perform(get("/api/schools")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.schoolResourceList[*].id", containsInAnyOrder(stanford().getId().toString(), berkeley().getId().toString())))
                .andExpect(jsonPath("$._embedded.schoolResourceList[*].name", containsInAnyOrder(stanford().getName(), berkeley().getName())))
                .andExpect(jsonPath("$._embedded.schoolResourceList[*].description", containsInAnyOrder(stanford().getDescription(), berkeley().getDescription())));
    }

    @Test
    public void shouldListSchoolMentorsProfile() throws Exception {
        this.mockMvc.perform(get("/api/schools/" + stanford().getId() + "/mentors")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.mentorProfileResourceList[*].id", containsInAnyOrder(feiProfile().getId().toString())))
                .andExpect(jsonPath("$._embedded.mentorProfileResourceList[*].mentorId", containsInAnyOrder(feiProfile().getMentorId().toString())))
                .andExpect(jsonPath("$._embedded.mentorProfileResourceList[*].schoolId", containsInAnyOrder(feiProfile().getSchoolId().toString())))
                .andExpect(jsonPath("$._embedded.mentorProfileResourceList[*].email", containsInAnyOrder(feiProfile().getEmail())))
                .andExpect(jsonPath("$._embedded.mentorProfileResourceList[*].status", containsInAnyOrder(feiProfile().getStatus().toString())));
    }

    @Test
    public void shouldGetSchoolById() throws Exception {
        this.mockMvc.perform(get("/api/schools/" + stanford().getId().toString())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(stanford().getId().toString())))
                .andExpect(jsonPath("$.description", is(stanford().getDescription())))
                .andExpect(jsonPath("$.name", is(stanford().getName())));
    }

    @Test
    public void shouldCreateNewSchool() throws Exception {
        this.mockMvc.perform(
                post("/api/schools")
                        .content(postSchoolPayload(stanford()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldUpdateSchool() throws Exception {
        this.mockMvc.perform(
                put("/api/schools/" + stanford().getId())
                        .content(putSchoolPayload(updatedStanford()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(stanford().getId().toString())))
                .andExpect(jsonPath("$.name", is(updatedStanford().getName())));
    }

    @Test
    public void shouldDeleteSchool() throws Exception {
        this.mockMvc.perform(
                delete("/api/schools/" + stanford().getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
