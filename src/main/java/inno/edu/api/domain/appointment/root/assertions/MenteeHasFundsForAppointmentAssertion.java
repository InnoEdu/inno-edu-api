package inno.edu.api.domain.appointment.root.assertions;

import inno.edu.api.domain.appointment.root.models.Appointment;
import inno.edu.api.domain.profile.root.queries.GetProfileByIdQuery;
import inno.edu.api.domain.user.transaction.assertions.SufficientFundsAssertion;
import inno.edu.api.infrastructure.annotations.Assertion;
import inno.edu.api.infrastructure.configuration.properties.ApplicationConfiguration;

import java.util.UUID;

@Assertion
public class MenteeHasFundsForAppointmentAssertion {
    private final GetProfileByIdQuery getProfileByIdQuery;
    private final SufficientFundsAssertion sufficientFundsAssertion;
    private final ApplicationConfiguration applicationConfiguration;

    public MenteeHasFundsForAppointmentAssertion(GetProfileByIdQuery getProfileByIdQuery, SufficientFundsAssertion sufficientFundsAssertion, ApplicationConfiguration applicationConfiguration) {
        this.getProfileByIdQuery = getProfileByIdQuery;
        this.sufficientFundsAssertion = sufficientFundsAssertion;
        this.applicationConfiguration = applicationConfiguration;
    }

    public void run(Appointment appointment) {
        if (!applicationConfiguration.getFeatures().isUserBalanceValidation()) {
            return;
        }

        UUID userId = getProfileByIdQuery.run(appointment.getMenteeProfileId()).getUserId();
        sufficientFundsAssertion.run(userId, appointment.getFee());
    }
}
