package inno.edu.api.domain.availability.assertions;

import inno.edu.api.domain.availability.exceptions.AvailabilityNotFoundException;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.AvailabilityFactory.availability;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AvailabilityExistsAssertionTest {
    @Mock
    private AvailabilityRepository availabilityRepository;

    @InjectMocks
    private AvailabilityExistsAssertion availabilityExistsAssertion;

    @Test(expected = AvailabilityNotFoundException.class)
    public void shouldThrowExceptionIfAvailabilityDoesNotExist() {
        when(availabilityRepository.exists(availability().getId())).thenReturn(false);

        availabilityExistsAssertion.run(availability().getId());
    }

    @Test
    public void shouldNotThrowExceptionIfAvailabilityExists() {
        when(availabilityRepository.exists(availability().getId())).thenReturn(true);

        availabilityExistsAssertion.run(availability().getId());
    }
}