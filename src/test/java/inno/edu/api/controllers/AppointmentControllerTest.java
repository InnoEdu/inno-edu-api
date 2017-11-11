package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.AppointmentResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.appointment.commands.CreateAppointmentCommand;
import inno.edu.api.domain.appointment.commands.UpdateAppointmentCommand;
import inno.edu.api.domain.appointment.exceptions.AppointmentNotFoundException;
import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.queries.GetAppointmentsByUniversityIdQuery;
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

import static inno.edu.api.domain.appointment.models.AppointmentStatus.PROPOSED;
import static inno.edu.api.factories.AppointmentFactory.appointment;
import static inno.edu.api.factories.AppointmentFactory.appointments;
import static inno.edu.api.factories.AppointmentFactory.proposedAppointments;
import static inno.edu.api.factories.UniversityFactory.stanford;
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
    private GetAppointmentsByUniversityIdQuery getAppointmentsByUniversityIdQuery;

    @InjectMocks
    private AppointmentController appointmentController;

    @Before
    public void setup() {
        setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    public void shouldGetAppointmentUsingId() {
        when(appointmentRepository.findOne(eq(appointment().getId()))).thenReturn(appointment());

        AppointmentResource appointmentResource = appointmentController.get(appointment().getId());

        assertThat(appointmentResource.getAppointment(), is(appointment()));
    }

    @Test(expected = AppointmentNotFoundException.class)
    public void shouldRaiseExceptionIfAppointmentNotFound() {
        when(appointmentRepository.findOne(any())).thenReturn(null);

        appointmentController.get(appointment().getId());
    }

    @Test
    public void shouldListAllAppointments() {
        when(appointmentRepository.findAll()).thenReturn(appointments());

        appointmentController.all();

        verify(resourceBuilder).from(eq(appointments()), any());
    }

    @Test
    public void shouldListAllAppointmentsByUniversity() {
        when(getAppointmentsByUniversityIdQuery.run(stanford().getId(), null)).thenReturn(appointments());

        appointmentController.allByUniversity(stanford().getId(), null);

        verify(resourceBuilder).from(eq(appointments()), any());
    }

    @Test
    public void shouldListAllAppointmentsByUniversityAndStatus() {
        when(getAppointmentsByUniversityIdQuery.run(stanford().getId(), PROPOSED)).thenReturn(proposedAppointments());

        appointmentController.allByUniversity(stanford().getId(), PROPOSED);

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
    public void shouldUDeleteAppointment() {
        when(appointmentRepository.exists(appointment().getId())).thenReturn(true);

        appointmentController.delete(appointment().getId());

        verify(appointmentRepository).delete(appointment().getId());
    }
}