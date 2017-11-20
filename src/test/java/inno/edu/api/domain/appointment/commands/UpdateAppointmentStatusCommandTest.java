package inno.edu.api.domain.appointment.commands;

import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.queries.GetAppointmentByIdQuery;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.domain.appointment.models.AppointmentStatus.ACCEPTED;
import static inno.edu.api.support.AppointmentFactory.appointment;
import static inno.edu.api.support.AppointmentFactory.reason;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateAppointmentStatusCommandTest {
    @Mock
    private GetAppointmentByIdQuery getAppointmentByIdQuery;

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private UpdateAppointmentStatusCommand updateAppointmentStatusCommand;

    @Test
    public void shouldUpdateAppointmentStatus() {
        when(getAppointmentByIdQuery.run(appointment().getId())).thenReturn(appointment());

        updateAppointmentStatusCommand.run(appointment().getId(), reason(), ACCEPTED);

        Appointment acceptedAppointment = appointment().toBuilder()
                .status(ACCEPTED)
                .reason(reason().getReason())
                .build();

        verify(appointmentRepository).save(acceptedAppointment);
    }
}