package inno.edu.api.controllers.profile;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static inno.edu.api.support.Payloads.approveProfileAssociationPayload;
import static inno.edu.api.support.Payloads.associateProfilePayload;
import static inno.edu.api.support.Payloads.rejectProfileAssociationPayload;
import static inno.edu.api.support.ProfileFactory.approveRequest;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.ProfileFactory.feiProfileAssociation;
import static inno.edu.api.support.ProfileFactory.gustavoProfile;
import static inno.edu.api.support.ProfileFactory.gustavoToBerkeleyRequest;
import static inno.edu.api.support.ProfileFactory.rejectRequest;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProfileAssociationControllerApiTest extends ApiTest {
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
