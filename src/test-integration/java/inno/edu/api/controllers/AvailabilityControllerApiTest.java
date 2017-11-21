package inno.edu.api.controllers;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static inno.edu.api.support.AvailabilityFactory.availability;
import static inno.edu.api.support.AvailabilityFactory.createAvailabilityRequest;
import static inno.edu.api.support.AvailabilityFactory.otherAvailability;
import static inno.edu.api.support.AvailabilityFactory.updateAvailabilityRequest;
import static inno.edu.api.support.AvailabilityFactory.updatedAvailability;
import static inno.edu.api.support.Payloads.postAvailabilityPayload;
import static inno.edu.api.support.Payloads.putAvailabilityPayload;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.not;
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
                .andExpect(jsonPath("$._embedded.availabilityResourceList[*].mentorProfileId", containsInAnyOrder(availability().getMentorProfileId().toString(), otherAvailability().getMentorProfileId().toString())))
                .andExpect(jsonPath("$._embedded.availabilityResourceList[*].fromDateTime", containsInAnyOrder(availability().getFromDateTime().toString(), otherAvailability().getFromDateTime().toString())))
                .andExpect(jsonPath("$._embedded.availabilityResourceList[*].toDateTime", containsInAnyOrder(availability().getToDateTime().toString(), otherAvailability().getToDateTime().toString())));
    }

    @Test
    public void shouldGetAvailabilityById() throws Exception {
        this.mockMvc.perform(get("/api/availability/" + availability().getId().toString())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(availability().getId().toString())))
                .andExpect(jsonPath("$.mentorProfileId", is(availability().getMentorProfileId().toString())))
                .andExpect(jsonPath("$.fromDateTime", is(availability().getFromDateTime().toString())))
                .andExpect(jsonPath("$.toDateTime", is(availability().getToDateTime().toString())));
    }

    @Test
    public void shouldCreateNewAvailability() throws Exception {
        this.mockMvc.perform(
                post("/api/availability")
                        .content(postAvailabilityPayload(createAvailabilityRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", not(availability().getId().toString())))
                .andExpect(jsonPath("$.mentorProfileId", is(availability().getMentorProfileId().toString())))
                .andExpect(jsonPath("$.toDateTime", is(availability().getToDateTime().toString())))
                .andExpect(jsonPath("$.fromDateTime", is(availability().getFromDateTime().toString())));
    }

    @Test
    public void shouldUpdateAvailability() throws Exception {
        this.mockMvc.perform(
                put("/api/availability/" + availability().getId())
                        .content(putAvailabilityPayload(updateAvailabilityRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(availability().getId().toString())))
                .andExpect(jsonPath("$.mentorProfileId", is(availability().getMentorProfileId().toString())))
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
