package inno.edu.api.domain.appointment.commands;

import inno.edu.api.domain.profile.queries.GetMentorProfileByIdQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static inno.edu.api.support.AppointmentFactory.calculateAppointmentFeeRequest;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static org.hamcrest.number.BigDecimalCloseTo.closeTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CalculateAppointmentFeeCommandTest {
    @Mock
    private GetMentorProfileByIdQuery getMentorProfileByIdQuery;

    @InjectMocks
    private CalculateAppointmentFeeCommand calculateAppointmentFeeCommand;

    @Before
    public void setUp() {
        when(getMentorProfileByIdQuery.run(feiProfile().getId())).thenReturn(feiProfile());
    }

    @Test
    public void shouldCalculateFeeForAppointment() {
        BigDecimal fee = calculateAppointmentFeeCommand.run(calculateAppointmentFeeRequest());
        assertThat(fee, closeTo(feiProfile().getRate(), new BigDecimal(0.001)));
    }
}