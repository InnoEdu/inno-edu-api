package inno.edu.api.domain.appointment.assertions;

import inno.edu.api.domain.appointment.exceptions.AppointmentNotFoundException;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.AppointmentFactory.appointment;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppointmentExistsAssertionTest {
    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentExistsAssertion appointmentExistsAssertion;

    @Test(expected = AppointmentNotFoundException.class)
    public void shouldThrowExceptionIfAppointmentDoesNotExist() {
        when(appointmentRepository.exists(appointment().getId())).thenReturn(false);

        appointmentExistsAssertion.run(appointment().getId());
    }

    @Test
    public void shouldNotThrowExceptionIfAppointmentExists() {
        when(appointmentRepository.exists(appointment().getId())).thenReturn(true);

        appointmentExistsAssertion.run(appointment().getId());
    }

}