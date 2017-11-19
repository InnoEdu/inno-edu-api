package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.AppointmentResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.appointment.commands.CreateAppointmentCommand;
import inno.edu.api.domain.appointment.commands.DeleteAppointmentCommand;
import inno.edu.api.domain.appointment.commands.UpdateAppointmentCommand;
import inno.edu.api.domain.appointment.commands.UpdateAppointmentStatusCommand;
import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.queries.GetAppointmentByIdQuery;
import inno.edu.api.domain.appointment.queries.GetAppointmentsByMenteeIdQuery;
import inno.edu.api.domain.appointment.queries.GetAppointmentsByMentorIdQuery;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletRequestAttributes;

import static inno.edu.api.domain.appointment.models.AppointmentStatus.ACCEPTED;
import static inno.edu.api.domain.appointment.models.AppointmentStatus.CANCELED;
import static inno.edu.api.domain.appointment.models.AppointmentStatus.DECLINED;
import static inno.edu.api.domain.appointment.models.AppointmentStatus.PROPOSED;
import static inno.edu.api.support.AppointmentFactory.appointment;
import static inno.edu.api.support.AppointmentFactory.appointments;
import static inno.edu.api.support.AppointmentFactory.emptyReason;
import static inno.edu.api.support.AppointmentFactory.proposedAppointments;
import static inno.edu.api.support.AppointmentFactory.reason;
import static inno.edu.api.support.UserFactory.alan;
import static inno.edu.api.support.UserFactory.fei;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.web.context.request.RequestContextHolder.setRequestAttributes;

@RunWith(MockitoJUnitRunner.class)
public class AppointmentControllerTest {
    @Mock
    private ResourceBuilder resourceBuilder;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private CreateAppointmentCommand createAppointmentCommand;

    @Mock
    private UpdateAppointmentCommand updateAppointmentCommand;

    @Mock
    private DeleteAppointmentCommand deleteAppointmentCommand;

    @Mock
    private GetAppointmentByIdQuery getAppointmentByIdQuery;

    @Mock
    private GetAppointmentsByMentorIdQuery getAppointmentsByMentorIdQuery;

    @Mock
    private GetAppointmentsByMenteeIdQuery getAppointmentsByMenteeIdQuery;

    @Mock
    private UpdateAppointmentStatusCommand updateAppointmentStatusCommand;

    @InjectMocks
    private AppointmentController appointmentController;

    @Before
    public void setup() {
        setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    public void shouldGetAppointmentById() {
        when(getAppointmentByIdQuery.run(eq(appointment().getId()))).thenReturn(appointment());

        AppointmentResource appointmentResource = appointmentController.get(appointment().getId());

        assertThat(appointmentResource.getAppointment(), is(appointment()));
    }

    @Test
    public void shouldListAppointments() {
        when(appointmentRepository.findAll()).thenReturn(appointments());

        appointmentController.all();

        verify(resourceBuilder).from(eq(appointments()), any());
    }

    @Test
    public void shouldListAppointmentsByMentor() {
        when(getAppointmentsByMentorIdQuery.run(fei().getId(), null)).thenReturn(appointments());

        appointmentController.allByMentor(fei().getId(), null);

        verify(resourceBuilder).from(eq(appointments()), any());
    }

    @Test
    public void shouldListAppointmentsByMentorAndStatus() {
        when(getAppointmentsByMentorIdQuery.run(fei().getId(), PROPOSED)).thenReturn(proposedAppointments());

        appointmentController.allByMentor(fei().getId(), PROPOSED);

        verify(resourceBuilder).from(eq(proposedAppointments()), any());
    }

    @Test
    public void shouldListAppointmentsByMentee() {
        when(getAppointmentsByMenteeIdQuery.run(alan().getId(), null)).thenReturn(appointments());

        appointmentController.allByMentee(alan().getId(), null);

        verify(resourceBuilder).from(eq(appointments()), any());
    }

    @Test
    public void shouldListAppointmentsByMenteeAndStatus() {
        when(getAppointmentsByMenteeIdQuery.run(alan().getId(), PROPOSED)).thenReturn(proposedAppointments());

        appointmentController.allByMentee(alan().getId(), PROPOSED);

        verify(resourceBuilder).from(eq(proposedAppointments()), any());
    }

    @Test
    public void shouldCreateNewAppointment() {
        ArgumentCaptor<Appointment> argumentCaptor = forClass(Appointment.class);
        when(createAppointmentCommand.run(argumentCaptor.capture())).thenReturn(appointment());

        appointmentController.post(appointment());

        verify(createAppointmentCommand).run(argumentCaptor.capture());
    }

    @Test
    public void shouldUpdateAppointment() {
        when(updateAppointmentCommand.run(appointment().getId(), appointment())).thenReturn(appointment());

        appointmentController.put(appointment().getId(), appointment());

        verify(updateAppointmentCommand).run(appointment().getId(), appointment());
    }

    @Test
    public void shouldDeleteAppointment() {
        appointmentController.delete(appointment().getId());

        verify(deleteAppointmentCommand).run(appointment().getId());
    }

    @Test
    public void shouldCancelAppointment() {
        appointmentController.cancel(appointment().getId(), reason());

        verify(updateAppointmentStatusCommand).run(appointment().getId(), reason(), CANCELED);
    }

    @Test
    public void shouldDeclineAppointment() {
        appointmentController.decline(appointment().getId(), reason());

        verify(updateAppointmentStatusCommand).run(appointment().getId(), reason(), DECLINED);
    }

    @Test
    public void shouldAcceptAppointment() {
        appointmentController.accept(appointment().getId());

        verify(updateAppointmentStatusCommand).run(appointment().getId(), emptyReason(), ACCEPTED);
    }
}