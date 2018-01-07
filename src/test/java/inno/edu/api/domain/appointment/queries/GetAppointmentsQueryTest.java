package inno.edu.api.domain.appointment.queries;

import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static inno.edu.api.support.AppointmentFactory.appointments;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetAppointmentsQueryTest {
    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private GetAppointmentsQuery getAppointmentsQuery;

    @Test
    public void shouldReturnAppointments() {
        when(appointmentRepository.findAll()).thenReturn(appointments());

        List<Appointment> appointments = getAppointmentsQuery.run();

        assertThat(appointments, is(appointments()));
    }
}