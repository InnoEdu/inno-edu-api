package inno.edu.api.controllers.profile;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static inno.edu.api.support.Matchers.safeHasItems;
import static inno.edu.api.support.Payloads.postProfilePayload;
import static inno.edu.api.support.Payloads.putProfilePayload;
import static inno.edu.api.support.ProfileFactory.alanProfile;
import static inno.edu.api.support.ProfileFactory.createAlanProfileRequest;
import static inno.edu.api.support.ProfileFactory.createTuanyProfileRequest;
import static inno.edu.api.support.ProfileFactory.gustavoProfile;
import static inno.edu.api.support.ProfileFactory.profileToDelete;
import static inno.edu.api.support.ProfileFactory.tuanyProfile;
import static inno.edu.api.support.ProfileFactory.updateAlanProfileRequest;
import static inno.edu.api.support.ProfileFactory.updatedAlanProfile;
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
                .andExpect(jsonPath("$._embedded.profileResourceList[*].id", safeHasItems(alanProfile().getId().toString(), gustavoProfile().getId().toString())))
                .andExpect(jsonPath("$._embedded.profileResourceList[*].userId", safeHasItems(alanProfile().getUserId().toString(), gustavoProfile().getUserId().toString())))
                .andExpect(jsonPath("$._embedded.profileResourceList[*].schoolId", safeHasItems(alanProfile().getSchoolId(), gustavoProfile().getSchoolId().toString())))
                .andExpect(jsonPath("$._embedded.profileResourceList[*].description", safeHasItems(alanProfile().getDescription(), gustavoProfile().getDescription())))
                .andExpect(jsonPath("$._embedded.profileResourceList[*].location", safeHasItems(alanProfile().getLocation(), gustavoProfile().getLocation())))
                .andExpect(jsonPath("$._embedded.profileResourceList[*].company", safeHasItems(alanProfile().getCompany(), gustavoProfile().getCompany())))
                .andExpect(jsonPath("$._embedded.profileResourceList[*].rate", safeHasItems(alanProfile().getRate(), gustavoProfile().getRate().doubleValue())))
                .andExpect(jsonPath("$._embedded.profileResourceList[*].profileReferenceId", safeHasItems(alanProfile().getProfileReferenceId(), gustavoProfile().getProfileReferenceId().toString())));
    }

    @Test
    public void shouldGetProfileById() throws Exception {
        this.mockMvc.perform(get("/api/profiles/" + gustavoProfile().getId().toString())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(gustavoProfile().getId().toString())))
                .andExpect(jsonPath("$.description", is(gustavoProfile().getDescription())))
                .andExpect(jsonPath("$.location", is(gustavoProfile().getLocation())))
                .andExpect(jsonPath("$.company", is(gustavoProfile().getCompany())))
                .andExpect(jsonPath("$.rate", is(gustavoProfile().getRate().doubleValue())))
                .andExpect(jsonPath("$.mentor", is(true)));
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
                .andExpect(jsonPath("$.description", is(tuanyProfile().getDescription())))
                .andExpect(jsonPath("$.location", is(tuanyProfile().getLocation())))
                .andExpect(jsonPath("$.company", is(tuanyProfile().getCompany())))
                .andExpect(jsonPath("$.profileReferenceId", is(tuanyProfile().getProfileReferenceId().toString())));
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
                .andExpect(jsonPath("$.location", is(updatedAlanProfile().getLocation())))
                .andExpect(jsonPath("$.description", is(updatedAlanProfile().getDescription())))
                .andExpect(jsonPath("$.profileReferenceId", is(updatedAlanProfile().getProfileReferenceId().toString())));
    }

    @Test
    public void shouldDeleteProfile() throws Exception {
        this.mockMvc.perform(
                delete("/api/profiles/" + profileToDelete().getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
