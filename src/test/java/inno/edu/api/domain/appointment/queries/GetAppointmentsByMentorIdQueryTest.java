package inno.edu.api.domain.appointment.queries;

import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import inno.edu.api.domain.user.queries.GetMentorActiveProfileByUserIdQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static inno.edu.api.domain.appointment.models.AppointmentStatus.PROPOSED;
import static inno.edu.api.support.AppointmentFactory.appointments;
import static inno.edu.api.support.UserFactory.fei;
import static inno.edu.api.support.UserFactory.feiProfile;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetAppointmentsByMentorIdQueryTest {
    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private GetMentorActiveProfileByUserIdQuery getMentorActiveProfileByUserIdQuery;

    @InjectMocks
    private GetAppointmentsByMentorIdQuery getAppointmentsByMentorIdQuery;

    @Before
    public void setUp() {
        when(appointmentRepository.findByMentorProfileId(feiProfile().getId())).thenReturn(appointments());
        when(appointmentRepository.findByMentorProfileIdAndStatus(feiProfile().getId(), PROPOSED)).thenReturn(appointments());
        when(getMentorActiveProfileByUserIdQuery.run(fei().getId())).thenReturn(feiProfile());
    }

    @Test
    public void shouldCallRepositoryForMentorId() {
        List<Appointment> expected = getAppointmentsByMentorIdQuery.run(fei().getId(), null);

        verify(appointmentRepository).findByMentorProfileId(feiProfile().getId());

        assertThat(expected, is(appointments()));
    }

    @Test
    public void shouldCallRepositoryForMentorIdAndStatus() {
        List<Appointment> expected = getAppointmentsByMentorIdQuery.run(fei().getId(), PROPOSED);

        verify(appointmentRepository).findByMentorProfileIdAndStatus(feiProfile().getId(), PROPOSED);

        assertThat(expected, is(appointments()));
    }
}