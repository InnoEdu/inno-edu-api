package inno.edu.api.domain.profile.interest.queries;

import inno.edu.api.domain.profile.interest.exceptions.InterestNotFoundException;
import inno.edu.api.domain.profile.interest.models.Interest;
import inno.edu.api.domain.profile.interest.repositories.InterestRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.feiInterest;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetInterestByIdQueryTest {
    @Mock
    private InterestRepository interestRepository;

    @InjectMocks
    private GetInterestByIdQuery getInterestByIdQuery;

    @Test(expected = InterestNotFoundException.class)
    public void shouldThrowExceptionIfInterestDoesNotExist() {
        when(interestRepository.findOne(feiInterest().getId())).thenReturn(null);

        getInterestByIdQuery.run(feiInterest().getId());
    }

    @Test
    public void shouldReturnInterest() {
        when(interestRepository.findOne(feiInterest().getId())).thenReturn(feiInterest());

        Interest interest = getInterestByIdQuery.run(feiInterest().getId());

        assertThat(interest, is(feiInterest()));
    }
}