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
import static inno.edu.api.support.ProfileFactory.newAlanProfile;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetAppointmentsByMenteeProfileIdQueryTest {
    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private GetAppointmentsByMenteeProfileIdQuery getAppointmentsByMenteeProfileIdQuery;

    @Before
    public void setUp() {
        when(appointmentRepository.findByMenteeProfileId(newAlanProfile().getId())).thenReturn(appointments());
        when(appointmentRepository.findByMenteeProfileIdAndStatus(newAlanProfile().getId(), PROPOSED)).thenReturn(appointments());
    }

    @Test
    public void shouldCallRepositoryForProfileId() {
        List<Appointment> expected = getAppointmentsByMenteeProfileIdQuery.run(newAlanProfile().getId(), null);

        verify(appointmentRepository).findByMenteeProfileId(newAlanProfile().getId());

        assertThat(expected, is(appointments()));
    }

    @Test
    public void shouldCallRepositoryForProfileIdAndStatus() {
        List<Appointment> expected = getAppointmentsByMenteeProfileIdQuery.run(newAlanProfile().getId(), PROPOSED);

        verify(appointmentRepository).findByMenteeProfileIdAndStatus(newAlanProfile().getId(), PROPOSED);

        assertThat(expected, is(appointments()));
    }
}