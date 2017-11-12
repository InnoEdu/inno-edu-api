package inno.edu.api.controllers;

import inno.edu.api.ApiTest;
import org.junit.Test;

import static inno.edu.api.factories.UserFactory.alanProfile;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
                .andExpect(jsonPath("$.id", is(is(alanProfile().getId().toString()))))
                .andExpect(jsonPath("$.menteeId", is(is(alanProfile().getMenteeId().toString()))))
                .andExpect(jsonPath("$.email", is(alanProfile().getEmail())));
    }

    @Test
    public void shouldGetProfileByMenteeId() throws Exception {
        this.mockMvc.perform(get("/api/mentee-profiles/mentee/" + alanProfile().getMenteeId().toString())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(is(alanProfile().getId().toString()))))
                .andExpect(jsonPath("$.menteeId", is(is(alanProfile().getMenteeId().toString()))))
                .andExpect(jsonPath("$.email", is(alanProfile().getEmail())));
    }

}
