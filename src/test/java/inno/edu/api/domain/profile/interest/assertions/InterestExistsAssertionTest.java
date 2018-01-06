package inno.edu.api.domain.profile.interest.assertions;

import inno.edu.api.domain.profile.interest.exceptions.InterestNotFoundException;
import inno.edu.api.domain.profile.interest.repositories.InterestRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.feiInterest;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InterestExistsAssertionTest {
    @Mock
    private InterestRepository interestRepository;

    @InjectMocks
    private InterestExistsAssertion interestExistsAssertion;

    @Test(expected = InterestNotFoundException.class)
    public void shouldThrowExceptionIfInterestDoesNotExist() {
        when(interestRepository.exists(feiInterest().getId())).thenReturn(false);

        interestExistsAssertion.run(feiInterest().getId());
    }

    @Test
    public void shouldNotThrowExceptionIfInterestExists() {
        when(interestRepository.exists(feiInterest().getId())).thenReturn(true);

        interestExistsAssertion.run(feiInterest().getId());
    }
}