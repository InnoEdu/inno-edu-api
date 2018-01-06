package inno.edu.api.domain.profile.interest.commands;

import inno.edu.api.domain.profile.interest.assertions.InterestExistsAssertion;
import inno.edu.api.domain.profile.interest.repositories.InterestRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.feiInterest;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeleteInterestCommandTest {
    @Mock
    private InterestExistsAssertion interestExistsAssertion;

    @Mock
    private InterestRepository interestRepository;

    @InjectMocks
    private DeleteInterestCommand deleteInterestCommand;

    @Test
    public void shouldCallRepositoryToDeleteInterest() {
        deleteInterestCommand.run(feiInterest().getId());

        verify(interestRepository).delete(feiInterest().getId());
    }

    @Test
    public void shouldRunAllAssertions() {
        deleteInterestCommand.run(feiInterest().getId());

        verify(interestExistsAssertion).run(feiInterest().getId());
    }
}