package inno.edu.api.domain.profile.interest.queries;

import inno.edu.api.domain.profile.interest.models.Interest;
import inno.edu.api.domain.profile.interest.repositories.InterestRepository;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static inno.edu.api.support.ProfileFactory.feiInterests;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetInterestsByProfileIdQueryTest {
    @Mock
    private InterestRepository interestRepository;

    @Mock
    private ProfileExistsAssertion profileExistsAssertion;

    @InjectMocks
    private GetInterestsByProfileIdQuery getInterestsByProfileIdQuery;

    @Test
    public void shouldGetInterestsByProfile() {
        when(interestRepository.findByProfileId(feiProfile().getId())).thenReturn(feiInterests());

        List<Interest> interests = getInterestsByProfileIdQuery.run(feiProfile().getId());

        assertThat(interests, is(feiInterests()));
    }

    @Test
    public void shouldRunAllAssertions() {
        getInterestsByProfileIdQuery.run(feiProfile().getId());

        verify(profileExistsAssertion).run(feiProfile().getId());
    }
}