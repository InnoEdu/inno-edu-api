package inno.edu.api.domain.appointment.commands;

import inno.edu.api.domain.appointment.commands.dtos.CalculateAppointmentFeeRequest;
import inno.edu.api.domain.common.assertions.DateTimeRangeAssertion;
import inno.edu.api.domain.profile.models.Profile;
import inno.edu.api.domain.profile.queries.GetProfileByIdQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static inno.edu.api.support.AppointmentFactory.calculateAppointmentFeeRequest;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.ProfileFactory.gustavoProfile;
import static java.time.LocalDateTime.of;
import static org.hamcrest.number.BigDecimalCloseTo.closeTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CalculateAppointmentFeeCommandTest {
    @Mock
    private DateTimeRangeAssertion dateTimeRangeAssertion;

    @Mock
    private GetProfileByIdQuery getProfileByIdQuery;

    @InjectMocks
    private CalculateAppointmentFeeCommand calculateAppointmentFeeCommand;

    @Before
    public void setUp() {
        when(getProfileByIdQuery.run(feiProfile().getId())).thenReturn(feiProfile());
    }

    @Test
    public void shouldCalculateFeeForAppointment() {
        BigDecimal fee = calculateAppointmentFeeCommand.run(calculateAppointmentFeeRequest());
        assertThat(fee, closeTo(feiProfile().getRate(), new BigDecimal(0.001)));
    }

    @Test
    public void shouldCalculateFeeForAppointmentWithSmallRate() {
        Profile smallRateProfile = gustavoProfile().toBuilder()
                .rate(new BigDecimal(5))
                .build();

        when(getProfileByIdQuery.run(smallRateProfile.getId())).thenReturn(smallRateProfile);

        CalculateAppointmentFeeRequest request =  CalculateAppointmentFeeRequest.builder()
                .mentorProfileId(smallRateProfile.getId())
                .fromDateTime(of(2017, 11, 10, 9, 0, 1))
                .toDateTime(of(2017, 11, 10, 10, 0, 1))
                .build();

        BigDecimal fee = calculateAppointmentFeeCommand.run(request);
        assertThat(fee, closeTo(smallRateProfile.getRate(), new BigDecimal(0.001)));
    }

    @Test
    public void shouldRunAllAssertions() {
        calculateAppointmentFeeCommand.run(calculateAppointmentFeeRequest());

        verify(dateTimeRangeAssertion).run(calculateAppointmentFeeRequest().getFromDateTime(),
                calculateAppointmentFeeRequest().getToDateTime());
    }
}