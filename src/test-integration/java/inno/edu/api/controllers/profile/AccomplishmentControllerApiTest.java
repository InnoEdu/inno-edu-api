package inno.edu.api.controllers.profile;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static inno.edu.api.support.Payloads.postAccomplishmentPayload;
import static inno.edu.api.support.Payloads.putAccomplishmentPayload;
import static inno.edu.api.support.ProfileFactory.createFeiAccomplishmentRequest;
import static inno.edu.api.support.ProfileFactory.feiAccomplishment;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.ProfileFactory.updateFeiAccomplishmentRequest;
import static inno.edu.api.support.ProfileFactory.updatedFeiAccomplishment;
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

public class AccomplishmentControllerApiTest extends ApiTest {
    @Test
    public void shouldListProfileAccomplishments() throws Exception {
        this.mockMvc.perform(get("/api/profiles/" + feiProfile().getId() + "/accomplishments")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.accomplishmentResourceList[*].id", hasItems(feiAccomplishment().getId().toString())))
                .andExpect(jsonPath("$._embedded.accomplishmentResourceList[*].profileId", hasItems(feiAccomplishment().getProfileId().toString())))
                .andExpect(jsonPath("$._embedded.accomplishmentResourceList[*].title", hasItems(feiAccomplishment().getTitle())))
                .andExpect(jsonPath("$._embedded.accomplishmentResourceList[*].description", hasItems(feiAccomplishment().getDescription())))
                .andExpect(jsonPath("$._embedded.accomplishmentResourceList[*].type", hasItems(feiAccomplishment().getType().toString())));
    }

    @Test
    public void shouldGetAccomplishmentById() throws Exception {
        this.mockMvc.perform(get("/api/profiles/accomplishments/" + feiAccomplishment().getId().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(feiAccomplishment().getId().toString())))
                .andExpect(jsonPath("$.profileId", is(feiAccomplishment().getProfileId().toString())))
                .andExpect(jsonPath("$.title", is(feiAccomplishment().getTitle())))
                .andExpect(jsonPath("$.description", is(feiAccomplishment().getDescription())))
                .andExpect(jsonPath("$.type", is(feiAccomplishment().getType().toString())));

    }

    @Test
    public void shouldCreateNewAccomplishment() throws Exception {
        this.mockMvc.perform(
                post("/api/profiles/" + feiProfile().getId() + "/accomplishments")
                        .content(postAccomplishmentPayload(createFeiAccomplishmentRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", not(feiAccomplishment().getId().toString())))
                .andExpect(jsonPath("$.profileId", is(feiAccomplishment().getProfileId().toString())))
                .andExpect(jsonPath("$.title", is(feiAccomplishment().getTitle())))
                .andExpect(jsonPath("$.description", is(feiAccomplishment().getDescription())))
                .andExpect(jsonPath("$.type", is(feiAccomplishment().getType().toString())));
    }

    @Test
    public void shouldUpdateAccomplishment() throws Exception {
        this.mockMvc.perform(
                put("/api/profiles/accomplishments/" + feiAccomplishment().getId())
                        .content(putAccomplishmentPayload(updateFeiAccomplishmentRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(feiAccomplishment().getId().toString())))
                .andExpect(jsonPath("$.profileId", is(updatedFeiAccomplishment().getProfileId().toString())))
                .andExpect(jsonPath("$.title", is(updatedFeiAccomplishment().getTitle())))
                .andExpect(jsonPath("$.description", is(updatedFeiAccomplishment().getDescription())))
                .andExpect(jsonPath("$.type", is(feiAccomplishment().getType().toString())));
    }

    @Test
    public void shouldDeleteAccomplishment() throws Exception {
        this.mockMvc.perform(
                delete("/api/profiles/accomplishments/" + feiAccomplishment().getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
