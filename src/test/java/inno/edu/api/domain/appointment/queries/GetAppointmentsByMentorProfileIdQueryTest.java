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
import static inno.edu.api.support.ProfileFactory.newFeiProfile;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetAppointmentsByMentorProfileIdQueryTest {
    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private GetAppointmentsByMentorProfileIdQuery getAppointmentsByMentorProfileIdQuery;

    @Before
    public void setUp() {
        when(appointmentRepository.findByMentorProfileId(newFeiProfile().getId())).thenReturn(appointments());
        when(appointmentRepository.findByMentorProfileIdAndStatus(newFeiProfile().getId(), PROPOSED)).thenReturn(appointments());
    }

    @Test
    public void shouldCallRepositoryForProfileId() {
        List<Appointment> expected = getAppointmentsByMentorProfileIdQuery.run(newFeiProfile().getId(), null);

        verify(appointmentRepository).findByMentorProfileId(newFeiProfile().getId());

        assertThat(expected, is(appointments()));
    }

    @Test
    public void shouldCallRepositoryForProfileIdAndStatus() {
        List<Appointment> expected = getAppointmentsByMentorProfileIdQuery.run(newFeiProfile().getId(), PROPOSED);

        verify(appointmentRepository).findByMentorProfileIdAndStatus(newFeiProfile().getId(), PROPOSED);

        assertThat(expected, is(appointments()));
    }
}