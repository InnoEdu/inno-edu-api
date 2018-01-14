package inno.edu.api.domain.appointment.root.commands;

import inno.edu.api.domain.appointment.root.commands.mappers.CalculateAppointmentFeeRequestMapper;
import inno.edu.api.domain.appointment.root.commands.mappers.CreateAppointmentRequestMapper;
import inno.edu.api.domain.appointment.root.models.Appointment;
import inno.edu.api.domain.appointment.root.repositories.AppointmentRepository;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.domain.appointment.root.models.AppointmentStatus.PROPOSED;
import static inno.edu.api.support.AppointmentFactory.appointment;
import static inno.edu.api.support.AppointmentFactory.calculateAppointmentFeeRequest;
import static inno.edu.api.support.AppointmentFactory.createAppointmentRequest;
import static inno.edu.api.support.AppointmentFactory.newAppointment;
import static java.math.BigDecimal.TEN;
import static java.util.UUID.randomUUID;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateAppointmentCommandTest {
    @Mock
    private UUIDGeneratorService uuidGeneratorService;

    @Mock
    private CreateAppointmentRequestMapper createAppointmentRequestMapper;

    @Mock
    private CalculateAppointmentFeeRequestMapper calculateAppointmentFeeRequestMapper;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private ProfileExistsAssertion profileExistsAssertion;

    @Mock
    private CalculateAppointmentFeeCommand calculateAppointmentFeeCommand;

    @InjectMocks
    private CreateAppointmentCommand createAppointmentCommand;

    @Before
    public void setUp() {
        when(uuidGeneratorService.generate()).thenReturn(randomUUID());

        when(createAppointmentRequestMapper.toAppointment(createAppointmentRequest()))
                .thenReturn(newAppointment());
    }

    @Test
    public void shouldSaveNewAppointment() {
        Appointment newAppointmentWithoutFee = newAppointment().toBuilder()
                .id(uuidGeneratorService.generate())
                .status(PROPOSED)
                .build();

        when(calculateAppointmentFeeRequestMapper.toAppointmentFeeRequest(newAppointmentWithoutFee))
                .thenReturn(calculateAppointmentFeeRequest());

        when(calculateAppointmentFeeCommand.run(calculateAppointmentFeeRequest()))
                .thenReturn(TEN);

        Appointment newAppointment = newAppointmentWithoutFee.toBuilder().fee(TEN).build();
        when(appointmentRepository.save(newAppointment)).thenReturn(newAppointment);

        Appointment savedAppointment = createAppointmentCommand.run(createAppointmentRequest());
        assertThat(savedAppointment, is(newAppointment));
    }

    @Test
    public void shouldRunAllAssertions() {
        createAppointmentCommand.run(createAppointmentRequest());

        verify(profileExistsAssertion).run(appointment().getMenteeProfileId());
        verify(profileExistsAssertion).run(appointment().getMentorProfileId());
    }
}