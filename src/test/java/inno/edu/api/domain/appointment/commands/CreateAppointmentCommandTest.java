package inno.edu.api.domain.appointment.commands;

import inno.edu.api.domain.appointment.commands.mappers.CalculateAppointmentFeeRequestMapper;
import inno.edu.api.domain.appointment.commands.mappers.CreateAppointmentRequestMapper;
import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import inno.edu.api.domain.profile.assertions.MenteeProfileExistsAssertion;
import inno.edu.api.domain.profile.assertions.MentorProfileExistsAssertion;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static inno.edu.api.support.AppointmentFactory.appointment;
import static inno.edu.api.support.AppointmentFactory.calculateAppointmentFeeRequest;
import static inno.edu.api.support.AppointmentFactory.createAppointmentRequest;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateAppointmentCommandTest {
    @Mock
    private CreateAppointmentRequestMapper createAppointmentRequestMapper;

    @Mock
    private CalculateAppointmentFeeRequestMapper calculateAppointmentFeeRequestMapper;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private MenteeProfileExistsAssertion menteeProfileExistsAssertion;

    @Mock
    private MentorProfileExistsAssertion mentorProfileExistsAssertion;

    @Mock
    private CalculateAppointmentFeeCommand calculateAppointmentFeeCommand;

    @InjectMocks
    private CreateAppointmentCommand createAppointmentCommand;

    @Before
    public void setUp() {
        when(createAppointmentRequestMapper.toAppointment(createAppointmentRequest()))
                .thenReturn(appointment());

        when(calculateAppointmentFeeRequestMapper.toAppointmentFeeRequest(appointment()))
                .thenReturn(calculateAppointmentFeeRequest());

        when(calculateAppointmentFeeCommand.run(calculateAppointmentFeeRequest()))
                .thenReturn(BigDecimal.ONE);
    }
    @Test
    public void shouldCallRepositoryToSaveAppointment() {
        ArgumentCaptor<Appointment> argumentCaptor = forClass(Appointment.class);

        when(appointmentRepository.save(argumentCaptor.capture()))
                .thenAnswer((invocation -> invocation.getArguments()[0]));

        Appointment appointment = createAppointmentCommand.run(createAppointmentRequest());

        assertThat(appointment, is(argumentCaptor.getValue()));
    }

    @Test
    public void shouldGenerateNewIdForAppointment() {
        ArgumentCaptor<Appointment> argumentCaptor = forClass(Appointment.class);

        when(appointmentRepository.save(argumentCaptor.capture())).thenReturn(appointment());

        createAppointmentCommand.run(createAppointmentRequest());

        assertThat(argumentCaptor.getValue().getId(), not(appointment().getId()));
    }

    @Test
    public void shouldRunAllConstraints() {
        createAppointmentCommand.run(createAppointmentRequest());

        verify(menteeProfileExistsAssertion).run(appointment().getMenteeProfileId());
        verify(mentorProfileExistsAssertion).run(appointment().getMentorProfileId());
    }
}