package inno.edu.api.domain.availability.commands;

import inno.edu.api.domain.availability.assertions.AvailabilityExistsAssertion;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.AvailabilityFactory.availability;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeleteAvailabilityCommandTest {
    @Mock
    private AvailabilityExistsAssertion availabilityExistsAssertion;

    @Mock
    private AvailabilityRepository availabilityRepository;

    @InjectMocks
    private DeleteAvailabilityCommand deleteAvailabilityCommand;

    @Test
    public void shouldCallRepositoryToDeleteAvailability() {
        deleteAvailabilityCommand.run(availability().getId());

        verify(availabilityRepository).delete(availability().getId());
    }

    @Test
    public void shouldRunAllAssertions() {
        deleteAvailabilityCommand.run(availability().getId());

        verify(availabilityExistsAssertion).run(availability().getId());
    }
}