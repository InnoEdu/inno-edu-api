package inno.edu.api.domain.appointment.commands;

import inno.edu.api.domain.appointment.assertions.AppointmentExistsAssertion;
import inno.edu.api.domain.appointment.commands.mappers.UpdateAppointmentStatusRequestMapper;
import inno.edu.api.domain.appointment.queries.GetAppointmentByIdQuery;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.AppointmentFactory.appointment;
import static inno.edu.api.support.AppointmentFactory.updateAppointmentStatusRequest;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateAppointmentStatusCommandTest {
    @Mock
    private AppointmentExistsAssertion appointmentExistsAssertion;

    @Mock
    private GetAppointmentByIdQuery getAppointmentByIdQuery;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private UpdateAppointmentStatusRequestMapper updateAppointmentStatusRequestMapper;

    @InjectMocks
    private UpdateAppointmentStatusCommand updateAppointmentStatusCommand;

    @Before
    public void setUp() {
        when(getAppointmentByIdQuery.run(appointment().getId())).thenReturn(appointment());
    }

    @Test
    public void shouldUpdateAppointmentStatus() {
        updateAppointmentStatusCommand.run(appointment().getId(), updateAppointmentStatusRequest());

        verify(updateAppointmentStatusRequestMapper).setAppointment(updateAppointmentStatusRequest(), appointment());
    }

    @Test
    public void shouldRunAllAssertions() {
        updateAppointmentStatusCommand.run(appointment().getId(), updateAppointmentStatusRequest());

        verify(appointmentExistsAssertion).run(appointment().getId());

    }
}