package inno.edu.api.domain.appointment.commands;

import inno.edu.api.domain.appointment.commands.dtos.CalculateAppointmentFeeRequest;
import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.profile.queries.GetMentorProfileByIdQuery;
import inno.edu.api.infrastructure.annotations.Command;

import java.math.BigDecimal;

import static java.math.RoundingMode.CEILING;
import static java.time.temporal.ChronoUnit.MINUTES;

@Command
public class CalculateAppointmentFeeCommand {
    private static final int SCALE = 5;
    private static final int CURRENCY_SCALE = 2;
    private static final BigDecimal MINUTES_IN_HOUR = new BigDecimal(60);

    private final GetMentorProfileByIdQuery getMentorProfileByIdQuery;

    public CalculateAppointmentFeeCommand(GetMentorProfileByIdQuery getMentorProfileByIdQuery) {
        this.getMentorProfileByIdQuery = getMentorProfileByIdQuery;
    }

    public BigDecimal run(CalculateAppointmentFeeRequest request) {
        MentorProfile mentorProfile = getMentorProfileByIdQuery.run(request.mentorProfileId);

        BigDecimal minutes =
                new BigDecimal(request.getFromDateTime().until(request.getToDateTime(), MINUTES));

        return mentorProfile.getRate()
                .divide(MINUTES_IN_HOUR, SCALE, CEILING)
                .multiply(minutes)
                .setScale(CURRENCY_SCALE, CEILING);
    }
}
