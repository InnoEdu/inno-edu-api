package inno.edu.api.domain.appointment.queries;

import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static inno.edu.api.domain.appointment.models.AppointmentStatus.PROPOSED;
import static inno.edu.api.support.AppointmentFactory.appointments;
import static inno.edu.api.support.UserFactory.alan;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetAppointmentsByMenteeIdQueryTest {
    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private GetAppointmentsByMenteeIdQuery getAppointmentsByMenteeIdQuery;

    @Before
    public void setUp() {
        when(appointmentRepository.findByMenteeId(alan().getId())).thenReturn(appointments());
        when(appointmentRepository.findByMenteeIdAndStatus(alan().getId(), PROPOSED)).thenReturn(appointments());
    }

    @Test
    public void shouldCallRepositoryForMenteeId() {
        List<Appointment> expected = getAppointmentsByMenteeIdQuery.run(alan().getId(), null);

        verify(appointmentRepository).findByMenteeId(alan().getId());

        assertThat(expected, is(appointments()));
    }

    @Test
    public void shouldCallRepositoryForMenteeIdAndStatus() {
        List<Appointment> expected = getAppointmentsByMenteeIdQuery.run(alan().getId(), PROPOSED);

        verify(appointmentRepository).findByMenteeIdAndStatus(alan().getId(), PROPOSED);

        assertThat(expected, is(appointments()));
    }
}