package inno.edu.api.controllers.appointment;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static inno.edu.api.domain.appointment.models.AppointmentStatus.PROPOSED;
import static inno.edu.api.support.AppointmentFactory.appointment;
import static inno.edu.api.support.AppointmentFactory.appointmentToDelete;
import static inno.edu.api.support.AppointmentFactory.createAppointmentRequest;
import static inno.edu.api.support.AppointmentFactory.createFeedbackRequest;
import static inno.edu.api.support.AppointmentFactory.feedback;
import static inno.edu.api.support.AppointmentFactory.otherAppointment;
import static inno.edu.api.support.AppointmentFactory.reason;
import static inno.edu.api.support.AppointmentFactory.updateAppointmentRequest;
import static inno.edu.api.support.AppointmentFactory.updatedAppointment;
import static inno.edu.api.support.Payloads.postAppointmentPayload;
import static inno.edu.api.support.Payloads.postFeedbackPayload;
import static inno.edu.api.support.Payloads.putAppointmentPayload;
import static inno.edu.api.support.Payloads.putAppointmentReasonPayload;
import static inno.edu.api.support.ProfileFactory.alanProfile;
import static inno.edu.api.support.ProfileFactory.feiProfile;
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

public class AppointmentControllerApiTest extends ApiTest {
    @Test
    public void shouldListAppointments() throws Exception {
        this.mockMvc.perform(get("/api/appointments")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].id", hasItems(appointment().getId().toString(), otherAppointment().getId().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].mentorProfileId", hasItems(appointment().getMentorProfileId().toString(), otherAppointment().getMentorProfileId().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].menteeProfileId", hasItems(appointment().getMenteeProfileId().toString(), otherAppointment().getMenteeProfileId().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].fromDateTime", hasItems(appointment().getFromDateTime().toString(), otherAppointment().getFromDateTime().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].toDateTime", hasItems(appointment().getToDateTime().toString(), otherAppointment().getToDateTime().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].description", hasItems(appointment().getDescription(), otherAppointment().getDescription())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].fee", hasItems(appointment().getFee().doubleValue(), otherAppointment().getFee().doubleValue())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].status", hasItems(appointment().getStatus().toString(), otherAppointment().getStatus().toString())));
    }

    @Test
    public void shouldListAppointmentsByMentorProfileAndStatus() throws Exception {
        this.mockMvc.perform(get("/api/appointments/mentor/" + feiProfile().getId())
                .param("status", PROPOSED.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].id", hasItems(appointment().getId().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].mentorProfileId", hasItems(appointment().getMentorProfileId().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].menteeProfileId", hasItems(appointment().getMenteeProfileId().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].fromDateTime", hasItems(appointment().getFromDateTime().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].toDateTime", hasItems(appointment().getToDateTime().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].description", hasItems(appointment().getDescription())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].fee", hasItems(appointment().getFee().doubleValue())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].status", hasItems(appointment().getStatus().toString())));
    }

    @Test
    public void shouldListAppointmentsByMenteeProfileAndStatus() throws Exception {
        this.mockMvc.perform(get("/api/appointments/mentee/" + alanProfile().getId())
                .param("status", PROPOSED.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].id", hasItems(appointment().getId().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].mentorProfileId", hasItems(appointment().getMentorProfileId().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].menteeProfileId", hasItems(appointment().getMenteeProfileId().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].fromDateTime", hasItems(appointment().getFromDateTime().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].toDateTime", hasItems(appointment().getToDateTime().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].description", hasItems(appointment().getDescription())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].fee", hasItems(appointment().getFee().doubleValue())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].status", hasItems(appointment().getStatus().toString())));
    }

    @Test
    public void shouldGetAppointmentById() throws Exception {
        this.mockMvc.perform(get("/api/appointments/" + appointment().getId().toString())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(appointment().getId().toString())))
                .andExpect(jsonPath("$.mentorProfileId", is(appointment().getMentorProfileId().toString())))
                .andExpect(jsonPath("$.menteeProfileId", is(appointment().getMenteeProfileId().toString())))
                .andExpect(jsonPath("$.fromDateTime", is(appointment().getFromDateTime().toString())))
                .andExpect(jsonPath("$.toDateTime", is(appointment().getToDateTime().toString())))
                .andExpect(jsonPath("$.description", is(appointment().getDescription())))
                .andExpect(jsonPath("$.fee", is(appointment().getFee().doubleValue())))
                .andExpect(jsonPath("$.status", is(appointment().getStatus().toString())));
    }

    @Test
    public void shouldCreateNewAppointment() throws Exception {
        this.mockMvc.perform(
                post("/api/appointments")
                        .content(postAppointmentPayload(createAppointmentRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", not(appointment().getId().toString())))
                .andExpect(jsonPath("$.mentorProfileId", is(appointment().getMentorProfileId().toString())))
                .andExpect(jsonPath("$.menteeProfileId", is(appointment().getMenteeProfileId().toString())))
                .andExpect(jsonPath("$.fromDateTime", is(appointment().getFromDateTime().toString())))
                .andExpect(jsonPath("$.toDateTime", is(appointment().getToDateTime().toString())))
                .andExpect(jsonPath("$.description", is(appointment().getDescription())))
                .andExpect(jsonPath("$.fee", is(appointment().getFee().doubleValue())))
                .andExpect(jsonPath("$.status", is(PROPOSED.toString())));
    }

    @Test
    public void shouldUpdateAppointment() throws Exception {
        this.mockMvc.perform(
                put("/api/appointments/" + appointment().getId())
                        .content(putAppointmentPayload(updateAppointmentRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(appointment().getId().toString())))
                .andExpect(jsonPath("$.mentorProfileId", is(appointment().getMentorProfileId().toString())))
                .andExpect(jsonPath("$.menteeProfileId", is(appointment().getMenteeProfileId().toString())))
                .andExpect(jsonPath("$.fromDateTime", is(updatedAppointment().getFromDateTime().toString())))
                .andExpect(jsonPath("$.toDateTime", is(updatedAppointment().getToDateTime().toString())))
                .andExpect(jsonPath("$.description", is(updatedAppointment().getDescription())))
                .andExpect(jsonPath("$.fee", is(updatedAppointment().getFee().doubleValue())))
                .andExpect(jsonPath("$.status", is(appointment().getStatus().toString())));
    }

    @Test
    public void shouldDeleteAppointment() throws Exception {
        this.mockMvc.perform(
                delete("/api/appointments/" + appointmentToDelete().getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldCancelAppointment() throws Exception {
        this.mockMvc.perform(
                put("/api/appointments/" + appointment().getId() + "/cancel")
                        .content(putAppointmentReasonPayload(reason()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeclineAppointment() throws Exception {
        this.mockMvc.perform(
                put("/api/appointments/" + appointment().getId() + "/decline")
                        .content(putAppointmentReasonPayload(reason()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldAcceptAppointment() throws Exception {
        this.mockMvc.perform(
                put("/api/appointments/" + appointment().getId() + "/accept"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldEstimateAppointment() throws Exception {
        this.mockMvc.perform(get("/api/appointments/estimate")
                .param("mentorProfileId", feiProfile().getId().toString())
                .param("fromDateTime", appointment().getFromDateTime().toString())
                .param("toDateTime", appointment().getToDateTime().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fee", is(appointment().getFee().doubleValue())));
    }

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