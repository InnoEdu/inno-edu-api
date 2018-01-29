package inno.edu.api.domain.appointment.root.commands;

import inno.edu.api.domain.appointment.root.assertions.AppointmentExistsAssertion;
import inno.edu.api.domain.appointment.root.models.dtos.mappers.UpdateAppointmentStatusRequestMapper;
import inno.edu.api.domain.appointment.root.queries.GetAppointmentByIdQuery;
import inno.edu.api.domain.appointment.root.repositories.AppointmentRepository;
import inno.edu.api.domain.user.transaction.commands.CreateTransactionForAppointmentCommand;
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

    @Mock
    private UpdateConflictingAppointmentsCommand updateConflictingAppointmentsCommand;

    @Mock
    private CreateTransactionForAppointmentCommand createTransactionForAppointmentCommand;

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
    public void shouldCheckForConflictsIfMentorAccepts() {
        updateAppointmentStatusCommand.run(appointment().getId(), updateAppointmentStatusRequest());

        verify(updateConflictingAppointmentsCommand).run(appointment());
    }

    @Test
    public void shouldCallCommandToCreateTransaction() {
        updateAppointmentStatusCommand.run(appointment().getId(), updateAppointmentStatusRequest());

        verify(createTransactionForAppointmentCommand).run(appointment().getId());
    }

    @Test
    public void shouldRunAllAssertions() {
        updateAppointmentStatusCommand.run(appointment().getId(), updateAppointmentStatusRequest());

        verify(appointmentExistsAssertion).run(appointment().getId());

    }
}