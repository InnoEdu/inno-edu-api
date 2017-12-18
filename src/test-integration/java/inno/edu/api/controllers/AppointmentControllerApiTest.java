package inno.edu.api.controllers;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static inno.edu.api.domain.appointment.models.AppointmentStatus.PROPOSED;
import static inno.edu.api.support.AppointmentFactory.appointment;
import static inno.edu.api.support.AppointmentFactory.createAppointmentRequest;
import static inno.edu.api.support.AppointmentFactory.otherAppointment;
import static inno.edu.api.support.AppointmentFactory.reason;
import static inno.edu.api.support.AppointmentFactory.updateAppointmentRequest;
import static inno.edu.api.support.AppointmentFactory.updatedAppointment;
import static inno.edu.api.support.Payloads.postAppointmentPayload;
import static inno.edu.api.support.Payloads.putAppointmentPayload;
import static inno.edu.api.support.Payloads.putAppointmentReasonPayload;
import static inno.edu.api.support.UserFactory.alan;
import static inno.edu.api.support.UserFactory.fei;
import static org.hamcrest.Matchers.contains;
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

public class AppointmentControllerApiTest extends ApiTest {
    @Test
    public void shouldListAppointments() throws Exception {
        this.mockMvc.perform(get("/api/appointments")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].id", containsInAnyOrder(appointment().getId().toString(), otherAppointment().getId().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].mentorProfileId", containsInAnyOrder(appointment().getMentorProfileId().toString(), otherAppointment().getMentorProfileId().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].menteeProfileId", containsInAnyOrder(appointment().getMenteeProfileId().toString(), otherAppointment().getMenteeProfileId().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].fromDateTime", containsInAnyOrder(appointment().getFromDateTime().toString(), otherAppointment().getFromDateTime().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].toDateTime", containsInAnyOrder(appointment().getToDateTime().toString(), otherAppointment().getToDateTime().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].description", containsInAnyOrder(appointment().getDescription(), otherAppointment().getDescription())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].fee", containsInAnyOrder(appointment().getFee().doubleValue(), otherAppointment().getFee().doubleValue())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].status", containsInAnyOrder(appointment().getStatus().toString(), otherAppointment().getStatus().toString())));
    }

    @Test
    public void shouldListAppointmentsByMentorAndStatus() throws Exception {
        this.mockMvc.perform(get("/api/appointments/mentor/" + fei().getId())
                .param("status", PROPOSED.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].id", contains(appointment().getId().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].mentorProfileId", contains(appointment().getMentorProfileId().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].menteeProfileId", contains(appointment().getMenteeProfileId().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].fromDateTime", contains(appointment().getFromDateTime().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].toDateTime", contains(appointment().getToDateTime().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].description", contains(appointment().getDescription())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].fee", contains(appointment().getFee().doubleValue())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].status", contains(appointment().getStatus().toString())));
    }

    @Test
    public void shouldListAppointmentsByMenteeAndStatus() throws Exception {
        this.mockMvc.perform(get("/api/appointments/mentee/" + alan().getId())
                .param("status", PROPOSED.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].id", contains(appointment().getId().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].mentorProfileId", contains(appointment().getMentorProfileId().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].menteeProfileId", contains(appointment().getMenteeProfileId().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].fromDateTime", contains(appointment().getFromDateTime().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].toDateTime", contains(appointment().getToDateTime().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].description", contains(appointment().getDescription())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].fee", contains(appointment().getFee().doubleValue())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].status", contains(appointment().getStatus().toString())));
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
                delete("/api/appointments/" + appointment().getId()))
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
}
