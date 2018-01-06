package inno.edu.api.controllers.profile;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static inno.edu.api.support.Payloads.postServicePayload;
import static inno.edu.api.support.Payloads.putServicePayload;
import static inno.edu.api.support.ProfileFactory.createFeiServiceRequest;
import static inno.edu.api.support.ProfileFactory.feiService;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.ProfileFactory.updateFeiServiceRequest;
import static inno.edu.api.support.ProfileFactory.updatedFeiService;
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

public class ServiceControllerApiTest extends ApiTest {
    @Test
    public void shouldListProfileServices() throws Exception {
        this.mockMvc.perform(get("/api/profiles/" + feiProfile().getId() + "/services")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.serviceResourceList[*].id", hasItems(feiService().getId().toString())))
                .andExpect(jsonPath("$._embedded.serviceResourceList[*].profileId", hasItems(feiService().getProfileId().toString())))
                .andExpect(jsonPath("$._embedded.serviceResourceList[*].title", hasItems(feiService().getTitle())))
                .andExpect(jsonPath("$._embedded.serviceResourceList[*].description", hasItems(feiService().getDescription())));
    }

    @Test
    public void shouldGetServiceById() throws Exception {
        this.mockMvc.perform(get("/api/profiles/services/" + feiService().getId().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(feiService().getId().toString())))
                .andExpect(jsonPath("$.profileId", is(feiService().getProfileId().toString())))
                .andExpect(jsonPath("$.title", is(feiService().getTitle())))
                .andExpect(jsonPath("$.description", is(feiService().getDescription())));
    }

    @Test
    public void shouldCreateNewService() throws Exception {
        this.mockMvc.perform(
                post("/api/profiles/" + feiProfile().getId() + "/services")
                        .content(postServicePayload(createFeiServiceRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", not(feiService().getId().toString())))
                .andExpect(jsonPath("$.profileId", is(feiService().getProfileId().toString())))
                .andExpect(jsonPath("$.title", is(feiService().getTitle())))
                .andExpect(jsonPath("$.description", is(feiService().getDescription())));
    }

    @Test
    public void shouldUpdateService() throws Exception {
        this.mockMvc.perform(
                put("/api/profiles/services/" + feiService().getId())
                        .content(putServicePayload(updateFeiServiceRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(feiService().getId().toString())))
                .andExpect(jsonPath("$.profileId", is(updatedFeiService().getProfileId().toString())))
                .andExpect(jsonPath("$.title", is(updatedFeiService().getTitle())))
                .andExpect(jsonPath("$.description", is(updatedFeiService().getDescription())));
    }

    @Test
    public void shouldDeleteService() throws Exception {
        this.mockMvc.perform(
                delete("/api/profiles/services/" + feiService().getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
