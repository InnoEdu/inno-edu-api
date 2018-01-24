package inno.edu.api.controllers.appointment;

import inno.edu.api.ApiTest;
import inno.edu.api.domain.appointment.root.models.Appointment;
import inno.edu.api.domain.appointment.root.models.projections.AppointmentProjection;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static inno.edu.api.domain.appointment.root.models.AppointmentStatus.PROPOSED;
import static inno.edu.api.domain.appointment.root.models.AppointmentStatus.UNAVAILABLE;
import static inno.edu.api.support.AppointmentFactory.appointment;
import static inno.edu.api.support.AppointmentFactory.appointmentProjection;
import static inno.edu.api.support.AppointmentFactory.appointmentProjections;
import static inno.edu.api.support.AppointmentFactory.appointmentToDelete;
import static inno.edu.api.support.AppointmentFactory.appointments;
import static inno.edu.api.support.AppointmentFactory.conflictAppointment;
import static inno.edu.api.support.AppointmentFactory.createAppointmentRequest;
import static inno.edu.api.support.AppointmentFactory.updateAppointmentRequest;
import static inno.edu.api.support.AppointmentFactory.updateAppointmentStatusRequest;
import static inno.edu.api.support.AppointmentFactory.updatedAppointment;
import static inno.edu.api.support.Payloads.postAppointmentPayload;
import static inno.edu.api.support.Payloads.putAppointmentPayload;
import static inno.edu.api.support.Payloads.putAppointmentStatusPayload;
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
        ResultActions resultActions = this.mockMvc.perform(get("/api/appointments"))
                .andDo(print())
                .andExpect(status().isOk());

        assertAppointmentList(resultActions, appointments());
    }

    @Test
    public void shouldListAppointmentsByMentorProfileAndStatus() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(get("/api/appointments/mentor/" + feiProfile().getId())
                .param("status", PROPOSED.toString()))
                .andDo(print())
                .andExpect(status().isOk());

        assertAppointmentProjectionList(resultActions, appointmentProjections());
    }

    @Test
    public void shouldListAppointmentsByMenteeProfileAndStatus() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(get("/api/appointments/mentee/" + alanProfile().getId())
                .param("status", PROPOSED.toString()))
                .andDo(print())
                .andExpect(status().isOk());

        assertAppointmentProjectionList(resultActions, appointmentProjections());
    }

    @Test
    public void shouldGetAppointmentById() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(get("/api/appointments/" + appointment().getId().toString()))
                .andDo(print())
                .andExpect(status().isOk());

        assertAppointmentProjection(resultActions, appointmentProjection());
    }

    @Test
    public void shouldCreateNewAppointment() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(
                post("/api/appointments")
                        .content(postAppointmentPayload(createAppointmentRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        assertAppointment(resultActions, appointment(), true);
    }

    @Test
    public void shouldUpdateAppointment() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(
                put("/api/appointments/" + appointment().getId())
                        .content(putAppointmentPayload(updateAppointmentRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        assertAppointment(resultActions, updatedAppointment());
    }

    @Test
    public void shouldDeleteAppointment() throws Exception {
        this.mockMvc.perform(
                delete("/api/appointments/" + appointmentToDelete().getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldUpdateAppointmentStatus() throws Exception {
        this.mockMvc.perform(
                put("/api/appointments/" + appointment().getId() + "/status")
                        .content(putAppointmentStatusPayload(updateAppointmentStatusRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        this.mockMvc.perform(get("/api/appointments/" + conflictAppointment().getId().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(conflictAppointment().getId().toString())))
                .andExpect(jsonPath("$.status", is(UNAVAILABLE.toString())));
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
    public void shouldSearchAllAppointments() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(get("/api/appointments/search"))
                .andDo(print())
                .andExpect(status().isOk());

        assertAppointmentList(resultActions, appointments());
    }

    @Test
    public void shouldSearchAppointmentsByStatusList() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(get("/api/appointments/search?status=PROPOSED,ACCEPTED"))
                .andDo(print())
                .andExpect(status().isOk());

        assertAppointmentList(resultActions, appointments());
    }

    private void assertAppointmentList(ResultActions resultActions, List<Appointment> appointments) throws Exception {
        appointments.forEach(appointment -> {
            try {
                resultActions
                        .andExpect(jsonPath("$._embedded.appointmentResourceList[*].id", hasItems(appointment.getId().toString())))
                        .andExpect(jsonPath("$._embedded.appointmentResourceList[*].mentorProfileId", hasItems(appointment.getMentorProfileId().toString())))
                        .andExpect(jsonPath("$._embedded.appointmentResourceList[*].menteeProfileId", hasItems(appointment.getMenteeProfileId().toString())))
                        .andExpect(jsonPath("$._embedded.appointmentResourceList[*].fromDateTime", hasItems(appointment.getFromDateTime().toString())))
                        .andExpect(jsonPath("$._embedded.appointmentResourceList[*].toDateTime", hasItems(appointment.getToDateTime().toString())))
                        .andExpect(jsonPath("$._embedded.appointmentResourceList[*].description", hasItems(appointment.getDescription())))
                        .andExpect(jsonPath("$._embedded.appointmentResourceList[*].fee", hasItems(appointment.getFee().doubleValue())))
                        .andExpect(jsonPath("$._embedded.appointmentResourceList[*].status", hasItems(appointment.getStatus().toString())));
            } catch (Exception ignored) {
            }
        });
    }

    private void assertAppointment(ResultActions resultActions, Appointment appointment) throws Exception {
        assertAppointment(resultActions, appointment, false);
    }

    private void assertAppointment(ResultActions resultActions, Appointment appointment, boolean isCreating) throws Exception {
        resultActions
                .andExpect(jsonPath("$.id", isCreating ? not(appointment.getId().toString()) : is(appointment.getId().toString())))
                .andExpect(jsonPath("$.mentorProfileId", is(appointment.getMentorProfileId().toString())))
                .andExpect(jsonPath("$.menteeProfileId", is(appointment.getMenteeProfileId().toString())))
                .andExpect(jsonPath("$.fromDateTime", is(appointment.getFromDateTime().toString())))
                .andExpect(jsonPath("$.toDateTime", is(appointment.getToDateTime().toString())))
                .andExpect(jsonPath("$.description", is(appointment.getDescription())))
                .andExpect(jsonPath("$.fee", is(appointment.getFee().doubleValue())))
                .andExpect(jsonPath("$.status", is(appointment.getStatus().toString())));
    }

    private void assertAppointmentProjection(ResultActions resultActions, AppointmentProjection appointmentProjection) throws Exception {
        resultActions
                .andExpect(jsonPath("$.id", is(appointmentProjection.getId().toString())))
                .andExpect(jsonPath("$.mentorProfileId", is(appointmentProjection.getMentorProfileId().toString())))
                .andExpect(jsonPath("$.menteeProfileId", is(appointmentProjection.getMenteeProfileId().toString())))
                .andExpect(jsonPath("$.fromDateTime", is(appointmentProjection.getFromDateTime().toString())))
                .andExpect(jsonPath("$.toDateTime", is(appointmentProjection.getToDateTime().toString())))
                .andExpect(jsonPath("$.description", is(appointmentProjection.getDescription())))
                .andExpect(jsonPath("$.fee", is(appointmentProjection.getFee().doubleValue())))
                .andExpect(jsonPath("$.status", is(appointmentProjection.getStatus().toString())))
                .andExpect(jsonPath("$.mentorFirstName", is(appointmentProjection.getMentorFirstName())))
                .andExpect(jsonPath("$.mentorLastName", is(appointmentProjection.getMentorLastName())))
                .andExpect(jsonPath("$.menteeFirstName", is(appointmentProjection.getMenteeFirstName())))
                .andExpect(jsonPath("$.menteeLastName", is(appointmentProjection.getMenteeLastName())))
                .andExpect(jsonPath("$.mentorPhotoUrl", is(appointmentProjection.getMentorPhotoUrl())))
                .andExpect(jsonPath("$.menteePhotoUrl", is(appointmentProjection.getMenteePhotoUrl())));
    }

    private void assertAppointmentProjectionList(ResultActions resultActions, List<AppointmentProjection> appointmentProjections) throws Exception {
        appointmentProjections.forEach(appointmentProjection -> {
            try {
                resultActions
                        .andExpect(jsonPath("$._embedded.appointmentProjectionResourceList[*].id", hasItems(appointmentProjection.getId().toString())))
                        .andExpect(jsonPath("$._embedded.appointmentProjectionResourceList[*].mentorProfileId", hasItems(appointmentProjection.getMentorProfileId().toString())))
                        .andExpect(jsonPath("$._embedded.appointmentProjectionResourceList[*].menteeProfileId", hasItems(appointmentProjection.getMenteeProfileId().toString())))
                        .andExpect(jsonPath("$._embedded.appointmentProjectionResourceList[*].fromDateTime", hasItems(appointmentProjection.getFromDateTime().toString())))
                        .andExpect(jsonPath("$._embedded.appointmentProjectionResourceList[*].toDateTime", hasItems(appointmentProjection.getToDateTime().toString())))
                        .andExpect(jsonPath("$._embedded.appointmentProjectionResourceList[*].description", hasItems(appointmentProjection.getDescription())))
                        .andExpect(jsonPath("$._embedded.appointmentProjectionResourceList[*].fee", hasItems(appointmentProjection.getFee().doubleValue())))
                        .andExpect(jsonPath("$._embedded.appointmentProjectionResourceList[*].status", hasItems(appointmentProjection.getStatus().toString())))
                        .andExpect(jsonPath("$._embedded.appointmentProjectionResourceList[*].mentorFirstName", hasItems(appointmentProjection.getMentorFirstName())))
                        .andExpect(jsonPath("$._embedded.appointmentProjectionResourceList[*].mentorLastName", hasItems(appointmentProjection.getMentorLastName())))
                        .andExpect(jsonPath("$._embedded.appointmentProjectionResourceList[*].menteeFirstName", hasItems(appointmentProjection.getMenteeFirstName())))
                        .andExpect(jsonPath("$._embedded.appointmentProjectionResourceList[*].menteeLastName", hasItems(appointmentProjection.getMenteeLastName())))
                        .andExpect(jsonPath("$._embedded.appointmentProjectionResourceList[*].mentorPhotoUrl", hasItems(appointmentProjection.getMentorPhotoUrl())))
                        .andExpect(jsonPath("$._embedded.appointmentProjectionResourceList[*].menteePhotoUrl", hasItems(appointmentProjection.getMenteePhotoUrl())));
            } catch (Exception ignored) {
            }
        });
    }

}
