package inno.edu.api.domain.appointment.commands;

import inno.edu.api.domain.appointment.exceptions.AppointmentNotFoundException;
import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.factories.AppointmentFactory.appointment;
import static inno.edu.api.factories.AppointmentFactory.updatedAppointment;
import static java.util.UUID.randomUUID;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateAppointmentCommandTest {
    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private UpdateAppointmentCommand updateAppointmentCommand;

    @Test
    public void shouldReturnUpdatedAppointment() {
        when(appointmentRepository.findOne(appointment().getId())).thenReturn(appointment());
        when(appointmentRepository.save(appointment())).thenReturn(updatedAppointment());

        Appointment appointment = updateAppointmentCommand.run(appointment().getId(), appointment());

        assertThat(appointment, is(updatedAppointment()));
    }

    @Test(expected = AppointmentNotFoundException.class)
    public void shouldRaiseExceptionIfAppointmentDoesNotExist() {
        when(appointmentRepository.findOne(appointment().getId())).thenThrow(new AppointmentNotFoundException(appointment().getId()));

        updateAppointmentCommand.run(randomUUID(), appointment());
    }
}