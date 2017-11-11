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
import static inno.edu.api.factories.UniversityFactory.stanford;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetAppointmentsByUniversityIdQueryTest {
    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private GetAppointmentsByUniversityIdQuery getAppointmentsByUniversityIdQuery;

    @Before
    public void setUp() {
        when(appointmentRepository.findAppointmentByUniversityId(stanford().getId())).thenReturn(appointments());
        when(appointmentRepository.findAppointmentByUniversityIdAndStatus(stanford().getId(), PROPOSED)).thenReturn(appointments());
    }

    @Test
    public void shouldCallRepositoryForUniversityId() {
        List<Appointment> expected = getAppointmentsByUniversityIdQuery.run(stanford().getId(), null);

        verify(appointmentRepository).findAppointmentByUniversityId(stanford().getId());

        assertThat(expected, is(appointments()));
    }

    @Test
    public void shouldCallRepositoryForUniversityIdAndStatus() {
        List<Appointment> expected = getAppointmentsByUniversityIdQuery.run(stanford().getId(), PROPOSED);

        verify(appointmentRepository).findAppointmentByUniversityIdAndStatus(stanford().getId(), PROPOSED);

        assertThat(expected, is(appointments()));
    }
}