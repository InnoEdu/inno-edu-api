package inno.edu.api.controllers.profile;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static inno.edu.api.support.Payloads.postInterestPayload;
import static inno.edu.api.support.Payloads.putInterestPayload;
import static inno.edu.api.support.ProfileFactory.createFeiInterestRequest;
import static inno.edu.api.support.ProfileFactory.feiInterest;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.ProfileFactory.updateFeiInterestRequest;
import static inno.edu.api.support.ProfileFactory.updatedFeiInterest;
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

public class InterestControllerApiTest extends ApiTest {
    @Test
    public void shouldListProfileInterests() throws Exception {
        this.mockMvc.perform(get("/api/profiles/" + feiProfile().getId() + "/interests")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.interestResourceList[*].id", hasItems(feiInterest().getId().toString())))
                .andExpect(jsonPath("$._embedded.interestResourceList[*].profileId", hasItems(feiInterest().getProfileId().toString())))
                .andExpect(jsonPath("$._embedded.interestResourceList[*].title", hasItems(feiInterest().getTitle())))
                .andExpect(jsonPath("$._embedded.interestResourceList[*].description", hasItems(feiInterest().getDescription())));
    }

    @Test
    public void shouldGetInterestById() throws Exception {
        this.mockMvc.perform(get("/api/profiles/interests/" + feiInterest().getId().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(feiInterest().getId().toString())))
                .andExpect(jsonPath("$.profileId", is(feiInterest().getProfileId().toString())))
                .andExpect(jsonPath("$.title", is(feiInterest().getTitle())))
                .andExpect(jsonPath("$.description", is(feiInterest().getDescription())));
    }

    @Test
    public void shouldCreateNewInterest() throws Exception {
        this.mockMvc.perform(
                post("/api/profiles/" + feiProfile().getId() + "/interests")
                        .content(postInterestPayload(createFeiInterestRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", not(feiInterest().getId().toString())))
                .andExpect(jsonPath("$.profileId", is(feiInterest().getProfileId().toString())))
                .andExpect(jsonPath("$.title", is(feiInterest().getTitle())))
                .andExpect(jsonPath("$.description", is(feiInterest().getDescription())));
    }

    @Test
    public void shouldUpdateInterest() throws Exception {
        this.mockMvc.perform(
                put("/api/profiles/interests/" + feiInterest().getId())
                        .content(putInterestPayload(updateFeiInterestRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(feiInterest().getId().toString())))
                .andExpect(jsonPath("$.profileId", is(updatedFeiInterest().getProfileId().toString())))
                .andExpect(jsonPath("$.title", is(updatedFeiInterest().getTitle())))
                .andExpect(jsonPath("$.description", is(updatedFeiInterest().getDescription())));
    }

    @Test
    public void shouldDeleteInterest() throws Exception {
        this.mockMvc.perform(
                delete("/api/profiles/interests/" + feiInterest().getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
