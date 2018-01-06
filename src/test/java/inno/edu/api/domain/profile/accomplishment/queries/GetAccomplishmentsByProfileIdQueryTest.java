package inno.edu.api.domain.profile.accomplishment.queries;

import inno.edu.api.domain.profile.accomplishment.models.Accomplishment;
import inno.edu.api.domain.profile.accomplishment.repositories.AccomplishmentRepository;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static inno.edu.api.support.ProfileFactory.feiAccomplishments;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetAccomplishmentsByProfileIdQueryTest {
    @Mock
    private AccomplishmentRepository accomplishmentRepository;

    @Mock
    private ProfileExistsAssertion profileExistsAssertion;

    @InjectMocks
    private GetAccomplishmentsByProfileIdQuery getAccomplishmentsByProfileIdQuery;

    @Test
    public void shouldGetAccomplishmentsByProfile() {
        when(accomplishmentRepository.findByProfileId(feiProfile().getId())).thenReturn(feiAccomplishments());

        List<Accomplishment> accomplishments = getAccomplishmentsByProfileIdQuery.run(feiProfile().getId());

        assertThat(accomplishments, is(feiAccomplishments()));
    }

    @Test
    public void shouldRunAllAssertions() {
        getAccomplishmentsByProfileIdQuery.run(feiProfile().getId());

        verify(profileExistsAssertion).run(feiProfile().getId());
    }
}