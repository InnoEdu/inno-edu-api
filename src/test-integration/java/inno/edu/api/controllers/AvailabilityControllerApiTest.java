package inno.edu.api.controllers;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static inno.edu.api.factories.AvailabilityFactory.availability;
import static inno.edu.api.factories.AvailabilityFactory.otherAvailability;
import static inno.edu.api.factories.AvailabilityFactory.updatedAvailability;
import static inno.edu.api.factories.UniversityFactory.berkeley;
import static inno.edu.api.factories.UserFactory.fei;
import static inno.edu.api.support.Payloads.postAvailabilityPayload;
import static inno.edu.api.support.Payloads.putAvailabilityPayload;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AvailabilityControllerApiTest extends ApiTest {
    @Test
    public void shouldListAvailability() throws Exception {
        this.mockMvc.perform(get("/api/availability")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.availabilityResourceList[*].id", containsInAnyOrder(availability().getId().toString(), otherAvailability().getId().toString())))
                .andExpect(jsonPath("$._embedded.availabilityResourceList[*].mentorId", containsInAnyOrder(availability().getMentorId().toString(), otherAvailability().getMentorId().toString())))
                .andExpect(jsonPath("$._embedded.availabilityResourceList[*].universityId", containsInAnyOrder(availability().getUniversityId().toString(), otherAvailability().getUniversityId().toString())))
                .andExpect(jsonPath("$._embedded.availabilityResourceList[*].fromDateTime", containsInAnyOrder(availability().getFromDateTime().toString(), otherAvailability().getFromDateTime().toString())))
                .andExpect(jsonPath("$._embedded.availabilityResourceList[*].toDateTime", containsInAnyOrder(availability().getToDateTime().toString(), otherAvailability().getToDateTime().toString())));
    }

    @Test
    public void shouldListAvailabilityByUser() throws Exception {
        this.mockMvc.perform(get("/api/availability/mentor/" + fei().getId().toString())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.availabilityResourceList[*].id", contains(availability().getId().toString())))
                .andExpect(jsonPath("$._embedded.availabilityResourceList[*].mentorId", contains(availability().getMentorId().toString())))
                .andExpect(jsonPath("$._embedded.availabilityResourceList[*].universityId", contains(availability().getUniversityId().toString())))
                .andExpect(jsonPath("$._embedded.availabilityResourceList[*].fromDateTime", contains(availability().getFromDateTime().toString())))
                .andExpect(jsonPath("$._embedded.availabilityResourceList[*].toDateTime", contains(availability().getToDateTime().toString())));
    }

    @Test
    public void shouldListAvailabilityByUniversity() throws Exception {
        this.mockMvc.perform(get("/api/availability/university/" + berkeley().getId().toString())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.availabilityResourceList[*].id", contains(otherAvailability().getId().toString())))
                .andExpect(jsonPath("$._embedded.availabilityResourceList[*].mentorId", contains(otherAvailability().getMentorId().toString())))
                .andExpect(jsonPath("$._embedded.availabilityResourceList[*].universityId", contains(otherAvailability().getUniversityId().toString())))
                .andExpect(jsonPath("$._embedded.availabilityResourceList[*].fromDateTime", contains(otherAvailability().getFromDateTime().toString())))
                .andExpect(jsonPath("$._embedded.availabilityResourceList[*].toDateTime", contains(otherAvailability().getToDateTime().toString())));
    }

    @Test
    public void shouldGetAvailabilityById() throws Exception {
        this.mockMvc.perform(get("/api/availability/" + availability().getId().toString())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(availability().getId().toString())))
                .andExpect(jsonPath("$.mentorId", is(availability().getMentorId().toString())))
                .andExpect(jsonPath("$.universityId", is(availability().getUniversityId().toString())))
                .andExpect(jsonPath("$.fromDateTime", is(availability().getFromDateTime().toString())))
                .andExpect(jsonPath("$.toDateTime", is(availability().getToDateTime().toString())));
    }

    @Test
    public void shouldCreateNewAvailability() throws Exception {
        this.mockMvc.perform(
                post("/api/availability")
                        .content(postAvailabilityPayload(availability()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldUpdateAvailability() throws Exception {
        this.mockMvc.perform(
                put("/api/availability/" + availability().getId())
                        .content(putAvailabilityPayload(updatedAvailability()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(availability().getId().toString())))
                .andExpect(jsonPath("$.toDateTime", is(updatedAvailability().getToDateTime().toString())))
                .andExpect(jsonPath("$.fromDateTime", is(updatedAvailability().getFromDateTime().toString())));
    }

    @Test
    public void shouldDeleteAvailability() throws Exception {
        this.mockMvc.perform(
                delete("/api/availability/" + availability().getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
