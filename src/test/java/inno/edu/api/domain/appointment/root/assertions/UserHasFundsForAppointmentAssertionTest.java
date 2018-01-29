package inno.edu.api.domain.appointment.root.assertions;

import inno.edu.api.domain.profile.root.queries.GetProfileByIdQuery;
import inno.edu.api.domain.user.transaction.assertions.SufficientFundsAssertion;
import inno.edu.api.infrastructure.configuration.properties.ApplicationConfiguration;
import inno.edu.api.infrastructure.configuration.properties.FeatureConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.AppointmentFactory.appointment;
import static inno.edu.api.support.ProfileFactory.alanProfile;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserHasFundsForAppointmentAssertionTest {
    @Mock
    private GetProfileByIdQuery getProfileByIdQuery;

    @Mock
    private SufficientFundsAssertion sufficientFundsAssertion;

    @Mock
    private ApplicationConfiguration applicationConfiguration;

    @InjectMocks
    private MenteeHasFundsForAppointmentAssertion userHasFundsForAppointmentAssertion;

    @Test
    public void shouldIgnoreIfFeatureToggleIsDisabled() {
        when(applicationConfiguration.getFeatures()).thenReturn(new FeatureConfiguration(false));

        userHasFundsForAppointmentAssertion.run(appointment());

        verify(sufficientFundsAssertion, never()).run(any(), any());
    }

    @Test
    public void shouldCallAssertionToCheckUserBalance() {
        when(applicationConfiguration.getFeatures()).thenReturn(new FeatureConfiguration(true));
        when(getProfileByIdQuery.run(appointment().getMenteeProfileId())).thenReturn(alanProfile());

        userHasFundsForAppointmentAssertion.run(appointment());

        verify(sufficientFundsAssertion).run(alanProfile().getUserId(), appointment().getFee());
    }
}