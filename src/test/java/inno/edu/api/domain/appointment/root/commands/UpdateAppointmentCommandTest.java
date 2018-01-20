package inno.edu.api.domain.appointment.root.commands;

import inno.edu.api.domain.appointment.root.models.dtos.mappers.CalculateAppointmentFeeRequestMapper;
import inno.edu.api.domain.appointment.root.models.dtos.mappers.UpdateAppointmentRequestMapper;
import inno.edu.api.domain.appointment.root.models.Appointment;
import inno.edu.api.domain.appointment.root.queries.GetAppointmentByIdQuery;
import inno.edu.api.domain.appointment.root.repositories.AppointmentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.AppointmentFactory.appointment;
import static inno.edu.api.support.AppointmentFactory.calculateAppointmentFeeRequest;
import static inno.edu.api.support.AppointmentFactory.updateAppointmentRequest;
import static inno.edu.api.support.AppointmentFactory.updatedAppointment;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateAppointmentCommandTest {
    @Mock
    private UpdateAppointmentRequestMapper updateAppointmentRequestMapper;

    @Mock
    private CalculateAppointmentFeeRequestMapper calculateAppointmentFeeRequestMapper;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private GetAppointmentByIdQuery getAppointmentByIdQuery;

    @Mock
    private CalculateAppointmentFeeCommand calculateAppointmentFeeCommand;

    @InjectMocks
    private UpdateAppointmentCommand updateAppointmentCommand;

    @Before
    public void setUp() {
        when(calculateAppointmentFeeRequestMapper.toAppointmentFeeRequest(appointment()))
                .thenReturn(calculateAppointmentFeeRequest());

        when(calculateAppointmentFeeCommand.run(calculateAppointmentFeeRequest()))
                .thenReturn(appointment().getFee());
    }

    @Test
    public void shouldReturnUpdatedAppointment() {
        when(getAppointmentByIdQuery.run(appointment().getId())).thenReturn(appointment());
        when(appointmentRepository.save(appointment())).thenReturn(updatedAppointment());

        Appointment appointment = updateAppointmentCommand.run(appointment().getId(), updateAppointmentRequest());

        verify(updateAppointmentRequestMapper).setAppointment(updateAppointmentRequest(), appointment());

        assertThat(appointment, is(updatedAppointment()));
    }
}