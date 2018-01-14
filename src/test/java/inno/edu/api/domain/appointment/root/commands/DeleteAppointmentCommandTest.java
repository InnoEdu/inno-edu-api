package inno.edu.api.domain.appointment.root.commands;

import inno.edu.api.domain.appointment.root.assertions.AppointmentExistsAssertion;
import inno.edu.api.domain.appointment.root.repositories.AppointmentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.AppointmentFactory.appointment;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeleteAppointmentCommandTest {
    @Mock
    private AppointmentExistsAssertion appointmentExistsAssertion;

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private DeleteAppointmentCommand deleteAppointmentCommand;

    @Test
    public void shouldCallRepositoryToDeleteAppointment() {
        deleteAppointmentCommand.run(appointment().getId());

        verify(appointmentRepository).delete(appointment().getId());
    }

    @Test
    public void shouldRunAllAssertions() {
        deleteAppointmentCommand.run(appointment().getId());

        verify(appointmentExistsAssertion).run(appointment().getId());
    }
}