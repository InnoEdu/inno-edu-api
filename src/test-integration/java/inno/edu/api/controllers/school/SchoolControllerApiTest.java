package inno.edu.api.controllers.school;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static inno.edu.api.support.Payloads.postSchoolPayload;
import static inno.edu.api.support.Payloads.putSchoolPayload;
import static inno.edu.api.support.ProfileFactory.gustavoProfile;
import static inno.edu.api.support.SchoolFactory.berkeley;
import static inno.edu.api.support.SchoolFactory.createStanfordRequest;
import static inno.edu.api.support.SchoolFactory.schoolToDelete;
import static inno.edu.api.support.SchoolFactory.stanford;
import static inno.edu.api.support.SchoolFactory.updateStanfordRequest;
import static inno.edu.api.support.SchoolFactory.updatedStanford;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
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
                .andExpect(jsonPath("$._embedded.schoolResourceList[*].id", hasItems(stanford().getId().toString(), berkeley().getId().toString())))
                .andExpect(jsonPath("$._embedded.schoolResourceList[*].name", hasItems(stanford().getName(), berkeley().getName())))
                .andExpect(jsonPath("$._embedded.schoolResourceList[*].description", hasItems(stanford().getDescription(), berkeley().getDescription())));
    }

    @Test
    public void shouldListSchoolMentorsProfile() throws Exception {
        this.mockMvc.perform(get("/api/schools/" + stanford().getId() + "/mentors")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.profileResourceList[*].id", hasItems(gustavoProfile().getId().toString())))
                .andExpect(jsonPath("$._embedded.profileResourceList[*].userId", hasItems(gustavoProfile().getUserId().toString())))
                .andExpect(jsonPath("$._embedded.profileResourceList[*].schoolId", hasItems(gustavoProfile().getSchoolId().toString())))
                .andExpect(jsonPath("$._embedded.profileResourceList[*].description", hasItems(gustavoProfile().getDescription())))
                .andExpect(jsonPath("$._embedded.profileResourceList[*].rate", hasItems(gustavoProfile().getRate().doubleValue())));
    }

    @Test
    public void shouldGetSchoolById() throws Exception {
        this.mockMvc.perform(get("/api/schools/" + stanford().getId().toString())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(stanford().getId().toString())))
                .andExpect(jsonPath("$.name", is(stanford().getName())))
                .andExpect(jsonPath("$.description", is(stanford().getDescription())));
    }

    @Test
    public void shouldCreateNewSchool() throws Exception {
        this.mockMvc.perform(
                post("/api/schools")
                        .content(postSchoolPayload(createStanfordRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", not(stanford().getId().toString())))
                .andExpect(jsonPath("$.name", is(stanford().getName())))
                .andExpect(jsonPath("$.description", is(stanford().getDescription())));
    }

    @Test
    public void shouldUpdateSchool() throws Exception {
        this.mockMvc.perform(
                put("/api/schools/" + stanford().getId())
                        .content(putSchoolPayload(updateStanfordRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(stanford().getId().toString())))
                .andExpect(jsonPath("$.name", is(updatedStanford().getName())))
                .andExpect(jsonPath("$.description", is(updatedStanford().getDescription())));
    }

    @Test
    public void shouldDeleteSchool() throws Exception {
        this.mockMvc.perform(
                delete("/api/schools/" + schoolToDelete().getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
