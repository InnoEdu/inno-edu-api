package inno.edu.api.controllers.profile;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static inno.edu.api.support.Payloads.postProfilePayload;
import static inno.edu.api.support.Payloads.putProfilePayload;
import static inno.edu.api.support.ProfileFactory.alanProfile;
import static inno.edu.api.support.ProfileFactory.createAlanProfileRequest;
import static inno.edu.api.support.ProfileFactory.createTuanyProfileRequest;
import static inno.edu.api.support.ProfileFactory.gustavoProfile;
import static inno.edu.api.support.ProfileFactory.tuanyProfile;
import static inno.edu.api.support.ProfileFactory.updateAlanProfileRequest;
import static inno.edu.api.support.ProfileFactory.updatedAlanProfile;
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

public class ProfileControllerApiTest extends ApiTest {
    @Test
    public void shouldListProfiles() throws Exception {
        this.mockMvc.perform(get("/api/profiles")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.profileResourceList[*].id", hasItems(alanProfile().getId().toString(), gustavoProfile().getId().toString())))
                .andExpect(jsonPath("$._embedded.profileResourceList[*].userId", hasItems(alanProfile().getUserId().toString(), gustavoProfile().getUserId().toString())))
                .andExpect(jsonPath("$._embedded.profileResourceList[*].schoolId", hasItems(alanProfile().getSchoolId(), gustavoProfile().getSchoolId().toString())))
                .andExpect(jsonPath("$._embedded.profileResourceList[*].description", hasItems(alanProfile().getDescription(), gustavoProfile().getDescription())))
                .andExpect(jsonPath("$._embedded.profileResourceList[*].rate", hasItems(alanProfile().getRate(), gustavoProfile().getRate().doubleValue())));
    }

    @Test
    public void shouldGetProfileById() throws Exception {
        this.mockMvc.perform(get("/api/profiles/" + gustavoProfile().getId().toString())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(gustavoProfile().getId().toString())))
                .andExpect(jsonPath("$.userId", is(gustavoProfile().getUserId().toString())))
                .andExpect(jsonPath("$.schoolId", is(gustavoProfile().getSchoolId().toString())))
                .andExpect(jsonPath("$.description", is(gustavoProfile().getDescription())))
                .andExpect(jsonPath("$.rate", is(gustavoProfile().getRate().doubleValue())));
    }

    @Test
    public void shouldCreateNewProfile() throws Exception {
        this.mockMvc.perform(
                post("/api/profiles")
                        .content(postProfilePayload(createTuanyProfileRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", not(tuanyProfile().getId().toString())))
                .andExpect(jsonPath("$.userId", is(tuanyProfile().getUserId().toString())))
                .andExpect(jsonPath("$.description", is(tuanyProfile().getDescription())));
    }

    @Test
    public void userShouldNotHaveMultipleProfiles() throws Exception {
        this.mockMvc.perform(
                post("/api/profiles")
                        .content(postProfilePayload(createAlanProfileRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldUpdateProfile() throws Exception {
        this.mockMvc.perform(
                put("/api/profiles/" + alanProfile().getId())
                        .content(putProfilePayload(updateAlanProfileRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(alanProfile().getId().toString())))
                .andExpect(jsonPath("$.userId", is(alanProfile().getUserId().toString())))
                .andExpect(jsonPath("$.description", is(updatedAlanProfile().getDescription())));
    }

    @Test
    public void shouldDeleteProfile() throws Exception {
        this.mockMvc.perform(
                delete("/api/profiles/" + alanProfile().getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
