package inno.edu.api.controllers;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static inno.edu.api.support.Payloads.approveProfileAssociationPayload;
import static inno.edu.api.support.Payloads.associateProfilePayload;
import static inno.edu.api.support.Payloads.postProfilePayload;
import static inno.edu.api.support.Payloads.putProfilePayload;
import static inno.edu.api.support.Payloads.rejectProfileAssociationPayload;
import static inno.edu.api.support.ProfileFactory.alanProfile;
import static inno.edu.api.support.ProfileFactory.approveRequest;
import static inno.edu.api.support.ProfileFactory.createAlanProfileRequest;
import static inno.edu.api.support.ProfileFactory.createTuanyProfileRequest;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.ProfileFactory.feiProfileAssociation;
import static inno.edu.api.support.ProfileFactory.gustavoProfile;
import static inno.edu.api.support.ProfileFactory.gustavoToBerkeleyRequest;
import static inno.edu.api.support.ProfileFactory.rejectRequest;
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
                .andExpect(jsonPath("$._embedded.profileResourceList[*].status", hasItems(alanProfile().getStatus().toString(), gustavoProfile().getStatus().toString())))
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
                .andExpect(jsonPath("$.status", is(gustavoProfile().getStatus().toString())))
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
                .andExpect(jsonPath("$.description", is(tuanyProfile().getDescription())))
                .andExpect(jsonPath("$.status", is(tuanyProfile().getStatus().toString())));
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

    @Test
    public void shouldAssociateProfileToSchool() throws Exception {
        this.mockMvc.perform(
                post("/api/profiles/" + gustavoProfile().getId() + "/associate")
                        .content(associateProfilePayload(gustavoToBerkeleyRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldListAssociationForProfile() throws Exception {
        this.mockMvc.perform(get("/api/profiles/" + feiProfile().getId() + "/associations"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.profileAssociationResourceList[*].id", hasItems(feiProfileAssociation().getId().toString())))
                .andExpect(jsonPath("$._embedded.profileAssociationResourceList[*].profileId", hasItems(feiProfileAssociation().getProfileId().toString())))
                .andExpect(jsonPath("$._embedded.profileAssociationResourceList[*].schoolId", hasItems(feiProfileAssociation().getSchoolId().toString())))
                .andExpect(jsonPath("$._embedded.profileAssociationResourceList[*].status", hasItems(feiProfileAssociation().getStatus().toString())))
                .andExpect(jsonPath("$._embedded.profileAssociationResourceList[*].description", hasItems(feiProfileAssociation().getDescription())));
    }

    @Test
    public void shouldApproveProfileToSchool() throws Exception {
        this.mockMvc.perform(
                put("/api/profiles/associations/" + feiProfileAssociation().getId() + "/approve")
                        .content(approveProfileAssociationPayload(approveRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldRejectProfileToSchool() throws Exception {
        this.mockMvc.perform(
                put("/api/profiles/associations/" + feiProfileAssociation().getId() + "/reject")
                        .content(rejectProfileAssociationPayload(rejectRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}
