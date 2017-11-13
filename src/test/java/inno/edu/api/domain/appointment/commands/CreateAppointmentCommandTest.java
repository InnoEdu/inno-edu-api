package inno.edu.api.domain.appointment.commands;

import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import inno.edu.api.domain.university.exceptions.UniversityNotFoundException;
import inno.edu.api.domain.university.repositories.UniversityRepository;
import inno.edu.api.domain.user.exceptions.ProfileNotFoundException;
import inno.edu.api.domain.user.exceptions.UserNotFoundException;
import inno.edu.api.domain.user.repositories.MentorProfileRepository;
import inno.edu.api.domain.user.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.AppointmentFactory.appointment;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateAppointmentCommandTest {
    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UniversityRepository universityRepository;

    @Mock
    private MentorProfileRepository mentorProfileRepository;

    @InjectMocks
    private CreateAppointmentCommand createAppointmentCommand;

    @Before
    public void setUp() {
        when(userRepository.exists(any())).thenReturn(true);
        when(universityRepository.exists(any())).thenReturn(true);
        when(mentorProfileRepository.exists(any())).thenReturn(true);
    }

    @Test
    public void shouldCallRepositoryToSaveAppointment() {
        ArgumentCaptor<Appointment> argumentCaptor = forClass(Appointment.class);

        when(appointmentRepository.save(argumentCaptor.capture()))
                .thenAnswer((invocation -> invocation.getArguments()[0]));

        Appointment appointment = createAppointmentCommand.run(appointment());

        assertThat(appointment, is(argumentCaptor.getValue()));
    }

    @Test
    public void shouldGenerateNewIdForAppointment() {
        ArgumentCaptor<Appointment> argumentCaptor = forClass(Appointment.class);

        when(appointmentRepository.save(argumentCaptor.capture())).thenReturn(appointment());

        createAppointmentCommand.run(appointment());

        assertThat(argumentCaptor.getValue().getId(), not(appointment().getId()));
    }

    @Test(expected = ProfileNotFoundException.class)
    public void shouldRaiseExceptionIfMentorProfileDoesNotExist() {
        when(mentorProfileRepository.exists(appointment().getMentorProfileId())).thenReturn(false);

        createAppointmentCommand.run(appointment());
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldRaiseExceptionIfMenteeUserDoesNotExist() {
        when(userRepository.exists(appointment().getMenteeId())).thenReturn(false);

        createAppointmentCommand.run(appointment());
    }

    @Test(expected = UniversityNotFoundException.class)
    public void shouldRaiseExceptionIfUniversityDoesNotExist() {
        when(universityRepository.exists(appointment().getUniversityId())).thenReturn(false);

        createAppointmentCommand.run(appointment());
    }
}