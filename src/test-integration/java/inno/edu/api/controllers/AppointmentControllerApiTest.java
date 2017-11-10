package inno.edu.api.controllers;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static inno.edu.api.factories.AppointmentFactory.appointment;
import static inno.edu.api.factories.AppointmentFactory.appointmentPostPayload;
import static inno.edu.api.factories.AppointmentFactory.appointmentPutPayload;
import static inno.edu.api.factories.AppointmentFactory.otherAppointment;
import static inno.edu.api.factories.AppointmentFactory.updatedAppointment;
import static org.hamcrest.Matchers.containsInAnyOrder;
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
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].mentorId", containsInAnyOrder(appointment().getMentorId().toString(), otherAppointment().getMentorId().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].menteeId", containsInAnyOrder(appointment().getMenteeId().toString(), otherAppointment().getMenteeId().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].universityId", containsInAnyOrder(appointment().getUniversityId().toString(), otherAppointment().getUniversityId().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].fromDateTime", containsInAnyOrder(appointment().getFromDateTime().toString(), otherAppointment().getFromDateTime().toString())))
                .andExpect(jsonPath("$._embedded.appointmentResourceList[*].toDateTime", containsInAnyOrder(appointment().getToDateTime().toString(), otherAppointment().getToDateTime().toString())));
    }

    @Test
    public void shouldGetAppointmentById() throws Exception {
        this.mockMvc.perform(get("/api/appointments/" + appointment().getId().toString())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(appointment().getId().toString())))
                .andExpect(jsonPath("$.mentorId", is(appointment().getMentorId().toString())))
                .andExpect(jsonPath("$.menteeId", is(appointment().getMenteeId().toString())))
                .andExpect(jsonPath("$.universityId", is(appointment().getUniversityId().toString())))
                .andExpect(jsonPath("$.fromDateTime", is(appointment().getFromDateTime().toString())))
                .andExpect(jsonPath("$.toDateTime", is(appointment().getToDateTime().toString())));
    }

    @Test
    public void shouldCreateNewAppointment() throws Exception {
        this.mockMvc.perform(
                post("/api/appointments")
                        .content(appointmentPostPayload(appointment()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldUpdateAppointment() throws Exception {
        this.mockMvc.perform(
                put("/api/appointments/" + appointment().getId())
                        .content(appointmentPutPayload(updatedAppointment()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(is(appointment().getId().toString()))))
                .andExpect(jsonPath("$.toDateTime", is(updatedAppointment().getToDateTime().toString())))
                .andExpect(jsonPath("$.fromDateTime", is(updatedAppointment().getFromDateTime().toString())));
    }

    @Test
    public void shouldDeleteAppointment() throws Exception {
        this.mockMvc.perform(
                delete("/api/appointments/" + appointment().getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
