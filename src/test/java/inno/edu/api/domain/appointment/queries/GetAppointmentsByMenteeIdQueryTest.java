package inno.edu.api.domain.appointment.queries;

import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import inno.edu.api.domain.profile.queries.GetProfileByUserIdQuery;
import inno.edu.api.domain.user.assertions.UserIsMenteeAssertion;
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
import static inno.edu.api.support.UserFactory.alan;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetAppointmentsByMenteeIdQueryTest {
    @Mock
    private UserIsMenteeAssertion userIsMenteeAssertion;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private GetProfileByUserIdQuery getMenteeProfileByUserIdQuery;

    @InjectMocks
    private GetAppointmentsByMenteeIdQuery getAppointmentsByMenteeIdQuery;

    @Before
    public void setUp() {
        when(appointmentRepository.findByMenteeProfileId(newAlanProfile().getId())).thenReturn(appointments());
        when(appointmentRepository.findByMenteeProfileIdAndStatus(newAlanProfile().getId(), PROPOSED)).thenReturn(appointments());
        when(getMenteeProfileByUserIdQuery.run(alan().getId())).thenReturn(newAlanProfile());
    }

    @Test
    public void shouldCallRepositoryForMenteeId() {
        List<Appointment> expected = getAppointmentsByMenteeIdQuery.run(alan().getId(), null);

        verify(appointmentRepository).findByMenteeProfileId(newAlanProfile().getId());

        assertThat(expected, is(appointments()));
    }

    @Test
    public void shouldCallRepositoryForMenteeIdAndStatus() {
        List<Appointment> expected = getAppointmentsByMenteeIdQuery.run(alan().getId(), PROPOSED);

        verify(appointmentRepository).findByMenteeProfileIdAndStatus(newAlanProfile().getId(), PROPOSED);

        assertThat(expected, is(appointments()));
    }

    @Test
    public void shouldRunAllAssertions() {
        getAppointmentsByMenteeIdQuery.run(alan().getId(), PROPOSED);

        verify(userIsMenteeAssertion).run(newAlanProfile().getUserId());
    }
}