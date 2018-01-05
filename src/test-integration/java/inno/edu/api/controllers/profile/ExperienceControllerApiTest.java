package inno.edu.api.controllers.profile;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static inno.edu.api.support.Payloads.postExperiencePayload;
import static inno.edu.api.support.Payloads.putExperiencePayload;
import static inno.edu.api.support.ProfileFactory.createFeiExperienceRequest;
import static inno.edu.api.support.ProfileFactory.feiExperience;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.ProfileFactory.updateFeiExperienceRequest;
import static inno.edu.api.support.ProfileFactory.updatedFeiExperience;
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

public class ExperienceControllerApiTest extends ApiTest {
    @Test
    public void shouldListProfileExperiences() throws Exception {
        this.mockMvc.perform(get("/api/profiles/" + feiProfile().getId() + "/experiences")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.experienceResourceList[*].id", hasItems(feiExperience().getId().toString())))
                .andExpect(jsonPath("$._embedded.experienceResourceList[*].profileId", hasItems(feiExperience().getProfileId().toString())))
                .andExpect(jsonPath("$._embedded.experienceResourceList[*].title", hasItems(feiExperience().getTitle())))
                .andExpect(jsonPath("$._embedded.experienceResourceList[*].institution", hasItems(feiExperience().getInstitution())))
                .andExpect(jsonPath("$._embedded.experienceResourceList[*].location", hasItems(feiExperience().getLocation())))
                .andExpect(jsonPath("$._embedded.experienceResourceList[*].area", hasItems(feiExperience().getArea())))
                .andExpect(jsonPath("$._embedded.experienceResourceList[*].fromDate", hasItems(feiExperience().getFromDate().toString())))
                .andExpect(jsonPath("$._embedded.experienceResourceList[*].toDate", hasItems(feiExperience().getToDate().toString())))
                .andExpect(jsonPath("$._embedded.experienceResourceList[*].description", hasItems(feiExperience().getDescription())))
                .andExpect(jsonPath("$._embedded.experienceResourceList[*].type", hasItems(feiExperience().getType().toString())));
    }

    @Test
    public void shouldGetExperienceById() throws Exception {
        this.mockMvc.perform(get("/api/profiles/experiences/" + feiExperience().getId().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(feiExperience().getId().toString())))
                .andExpect(jsonPath("$.profileId", is(feiExperience().getProfileId().toString())))
                .andExpect(jsonPath("$.title", is(feiExperience().getTitle())))
                .andExpect(jsonPath("$.institution", is(feiExperience().getInstitution())))
                .andExpect(jsonPath("$.location", is(feiExperience().getLocation())))
                .andExpect(jsonPath("$.area", is(feiExperience().getArea())))
                .andExpect(jsonPath("$.fromDate", is(feiExperience().getFromDate().toString())))
                .andExpect(jsonPath("$.toDate", is(feiExperience().getToDate().toString())))
                .andExpect(jsonPath("$.description", is(feiExperience().getDescription())))
                .andExpect(jsonPath("$.type", is(feiExperience().getType().toString())));
    }

    @Test
    public void shouldCreateNewExperience() throws Exception {
        this.mockMvc.perform(
                post("/api/profiles/" + feiProfile().getId() + "/experiences")
                        .content(postExperiencePayload(createFeiExperienceRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", not(feiExperience().getId().toString())))
                .andExpect(jsonPath("$.profileId", is(feiExperience().getProfileId().toString())))
                .andExpect(jsonPath("$.title", is(feiExperience().getTitle())))
                .andExpect(jsonPath("$.institution", is(feiExperience().getInstitution())))
                .andExpect(jsonPath("$.location", is(feiExperience().getLocation())))
                .andExpect(jsonPath("$.area", is(feiExperience().getArea())))
                .andExpect(jsonPath("$.fromDate", is(feiExperience().getFromDate().toString())))
                .andExpect(jsonPath("$.toDate", is(feiExperience().getToDate().toString())))
                .andExpect(jsonPath("$.description", is(feiExperience().getDescription())))
                .andExpect(jsonPath("$.type", is(feiExperience().getType().toString())));
    }

    @Test
    public void shouldUpdateExperience() throws Exception {
        this.mockMvc.perform(
                put("/api/profiles/experiences/" + feiExperience().getId())
                        .content(putExperiencePayload(updateFeiExperienceRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(feiExperience().getId().toString())))
                .andExpect(jsonPath("$.profileId", is(updatedFeiExperience().getProfileId().toString())))
                .andExpect(jsonPath("$.title", is(updatedFeiExperience().getTitle())))
                .andExpect(jsonPath("$.institution", is(updatedFeiExperience().getInstitution())))
                .andExpect(jsonPath("$.location", is(updatedFeiExperience().getLocation())))
                .andExpect(jsonPath("$.area", is(updatedFeiExperience().getArea())))
                .andExpect(jsonPath("$.fromDate", is(updatedFeiExperience().getFromDate().toString())))
                .andExpect(jsonPath("$.toDate", is(updatedFeiExperience().getToDate().toString())))
                .andExpect(jsonPath("$.description", is(updatedFeiExperience().getDescription())))
                .andExpect(jsonPath("$.type", is(feiExperience().getType().toString())));
    }

    @Test
    public void shouldDeleteExperience() throws Exception {
        this.mockMvc.perform(
                delete("/api/profiles/experiences/" + feiExperience().getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
