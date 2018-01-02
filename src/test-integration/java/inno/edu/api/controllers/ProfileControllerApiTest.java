package inno.edu.api.controllers;

import inno.edu.api.ApiTest;
import org.junit.Test;

import static inno.edu.api.support.ProfileFactory.newAlanProfile;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProfileControllerApiTest extends ApiTest {
    @Test
    public void shouldListProfiles() throws Exception {
        this.mockMvc.perform(get("/api/profiles")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.profileResourceList[*].id", containsInAnyOrder(newAlanProfile().getId().toString())))
                .andExpect(jsonPath("$._embedded.profileResourceList[*].userId", containsInAnyOrder(newAlanProfile().getUserId().toString())))
                .andExpect(jsonPath("$._embedded.profileResourceList[*].description", containsInAnyOrder(newAlanProfile().getDescription())));
    }
}
