package inno.edu.api.controllers;

import inno.edu.api.ApiTest;
import org.junit.Test;

import static inno.edu.api.factories.AvailabilityFactory.availability;
import static inno.edu.api.factories.AvailabilityFactory.otherAvailability;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AvailabilityControllerApiTest extends ApiTest {
    @Test
    public void shouldListAvailability() throws Exception {
        this.mockMvc.perform(get("/api/availability")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.availabilityResourceList[*].id", containsInAnyOrder(availability().getId().toString(), otherAvailability().getId().toString())));
    }

    @Test
    public void shouldGetAvailabilityById() throws Exception {
        this.mockMvc.perform(get("/api/availability/" + availability().getId().toString())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(availability().getId().toString())));
    }
}
