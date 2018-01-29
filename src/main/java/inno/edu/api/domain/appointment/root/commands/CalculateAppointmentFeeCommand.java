package inno.edu.api.domain.appointment.root.commands;

import inno.edu.api.domain.appointment.root.models.dtos.CalculateAppointmentFeeRequest;
import inno.edu.api.domain.common.assertions.DateTimeRangeAssertion;
import inno.edu.api.domain.profile.root.models.Profile;
import inno.edu.api.domain.profile.root.queries.GetProfileByIdQuery;
import inno.edu.api.infrastructure.annotations.Command;

import java.math.BigDecimal;

import static java.math.RoundingMode.CEILING;
import static java.math.RoundingMode.HALF_EVEN;
import static java.time.temporal.ChronoUnit.MINUTES;

@Command
public class CalculateAppointmentFeeCommand {
    private static final int SCALE = 5;
    private static final int CURRENCY_SCALE = 2;
    private static final BigDecimal MINUTES_IN_HOUR = new BigDecimal(60);

    private final DateTimeRangeAssertion dateTimeRangeAssertion;
    private final GetProfileByIdQuery getProfileByIdQuery;

    public CalculateAppointmentFeeCommand(DateTimeRangeAssertion dateTimeRangeAssertion, GetProfileByIdQuery getProfileByIdQuery) {
        this.dateTimeRangeAssertion = dateTimeRangeAssertion;
        this.getProfileByIdQuery = getProfileByIdQuery;
    }

    public BigDecimal run(CalculateAppointmentFeeRequest request) {
        dateTimeRangeAssertion.run(request.getFromDateTime(), request.getToDateTime());

        Profile mentorProfile = getProfileByIdQuery.run(request.getMentorProfileId());

        BigDecimal minutes = new BigDecimal(request.getFromDateTime().until(request.getToDateTime(), MINUTES));

        BigDecimal divide = mentorProfile.getRate().divide(MINUTES_IN_HOUR, SCALE, CEILING);
        BigDecimal multiply = divide.multiply(minutes);

        return multiply.setScale(CURRENCY_SCALE, HALF_EVEN);
    }
}
