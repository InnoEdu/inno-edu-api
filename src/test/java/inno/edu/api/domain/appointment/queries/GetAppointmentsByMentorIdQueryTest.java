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
import static inno.edu.api.factories.AppointmentFactory.appointments;
import static inno.edu.api.factories.UserFactory.fei;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetAppointmentsByMentorIdQueryTest {
    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private GetAppointmentsByMentorIdQuery getAppointmentsByMentorIdQuery;

    @Before
    public void setUp() {
        when(appointmentRepository.findByMentorId(fei().getId())).thenReturn(appointments());
        when(appointmentRepository.findByMentorIdAndStatus(fei().getId(), PROPOSED)).thenReturn(appointments());
    }

    @Test
    public void shouldCallRepositoryForMentorId() {
        List<Appointment> expected = getAppointmentsByMentorIdQuery.run(fei().getId(), null);

        verify(appointmentRepository).findByMentorId(fei().getId());

        assertThat(expected, is(appointments()));
    }

    @Test
    public void shouldCallRepositoryForMentorIdAndStatus() {
        List<Appointment> expected = getAppointmentsByMentorIdQuery.run(fei().getId(), PROPOSED);

        verify(appointmentRepository).findByMentorIdAndStatus(fei().getId(), PROPOSED);

        assertThat(expected, is(appointments()));
    }
}