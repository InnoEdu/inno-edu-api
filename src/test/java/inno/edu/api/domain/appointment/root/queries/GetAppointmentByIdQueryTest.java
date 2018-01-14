package inno.edu.api.domain.appointment.root.queries;

import inno.edu.api.domain.appointment.root.exceptions.AppointmentNotFoundException;
import inno.edu.api.domain.appointment.root.models.Appointment;
import inno.edu.api.domain.appointment.root.repositories.AppointmentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.AppointmentFactory.appointment;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetAppointmentByIdQueryTest {
    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private GetAppointmentByIdQuery getAppointmentByIdQuery;

    @Test(expected = AppointmentNotFoundException.class)
    public void shouldThrowExceptionIfAppointmentDoesNotExist() {
        when(appointmentRepository.findOne(appointment().getId())).thenReturn(null);

        getAppointmentByIdQuery.run(appointment().getId());
    }

    @Test
    public void shouldReturnAppointment() {
        when(appointmentRepository.findOne(appointment().getId())).thenReturn(appointment());

        Appointment appointment = getAppointmentByIdQuery.run(appointment().getId());

        assertThat(appointment, is(appointment()));
    }
}