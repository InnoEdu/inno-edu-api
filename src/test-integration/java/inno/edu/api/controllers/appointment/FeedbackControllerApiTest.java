package inno.edu.api.controllers.appointment;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static inno.edu.api.support.AppointmentFactory.appointment;
import static inno.edu.api.support.AppointmentFactory.createFeedbackRequest;
import static inno.edu.api.support.AppointmentFactory.feedback;
import static inno.edu.api.support.Payloads.postFeedbackPayload;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FeedbackControllerApiTest extends ApiTest {
    @Test
    public void shouldCreateNewFeedback() throws Exception {
        this.mockMvc.perform(
                post("/api/appointments/" + appointment().getId() + "/feedbacks")
                        .content(postFeedbackPayload(createFeedbackRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", not(feedback().getId().toString())))
                .andExpect(jsonPath("$.appointmentId", is(feedback().getAppointmentId().toString())))
                .andExpect(jsonPath("$.source", is(feedback().getSource().toString())))
                .andExpect(jsonPath("$.rating", is(feedback().getRating())))
                .andExpect(jsonPath("$.description", is(feedback().getDescription())));
    }

    @Test
    public void shouldListFeedbacks() throws Exception {
        this.mockMvc.perform(get("/api/appointments/" + appointment().getId() + "/feedbacks"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.feedbackResourceList[*].id", hasItems(feedback().getId().toString())))
                .andExpect(jsonPath("$._embedded.feedbackResourceList[*].appointmentId", hasItems(feedback().getAppointmentId().toString())))
                .andExpect(jsonPath("$._embedded.feedbackResourceList[*].source", hasItems(feedback().getSource().toString())))
                .andExpect(jsonPath("$._embedded.feedbackResourceList[*].rating", hasItems(feedback().getRating())))
                .andExpect(jsonPath("$._embedded.feedbackResourceList[*].description", hasItems(feedback().getDescription())));
    }

    @Test
    public void shouldDeleteFeedback() throws Exception {
        this.mockMvc.perform(
                delete("/api/appointments/feedbacks/" + feedback().getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldGetFeedbackById() throws Exception {
        this.mockMvc.perform(get("/api/appointments/feedbacks/" + feedback().getId().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(feedback().getId().toString())))
                .andExpect(jsonPath("$.appointmentId", is(feedback().getAppointmentId().toString())))
                .andExpect(jsonPath("$.source", is(feedback().getSource().toString())))
                .andExpect(jsonPath("$.rating", is(feedback().getRating())))
                .andExpect(jsonPath("$.description", is(feedback().getDescription())));
    }
}
