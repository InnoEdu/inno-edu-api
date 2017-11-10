package inno.edu.api.domain.availability.commands;

import inno.edu.api.domain.availability.exceptions.AvailabilityNotFoundException;
import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.factories.AvailabilityFactory.availability;
import static inno.edu.api.factories.AvailabilityFactory.updatedAvailability;
import static java.util.UUID.randomUUID;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateAvailabilityCommandTest {
    @Mock
    private AvailabilityRepository availabilityRepository;

    @InjectMocks
    private UpdateAvailabilityCommand updateAvailabilityCommand;

    @Test
    public void shouldReturnUpdatedAvailability() {
        when(availabilityRepository.findOne(availability().getId())).thenReturn(availability());
        when(availabilityRepository.save(availability())).thenReturn(updatedAvailability());

        Availability availability = updateAvailabilityCommand.run(availability().getId(), availability());

        assertThat(availability, is(updatedAvailability()));
    }

    @Test(expected = AvailabilityNotFoundException.class)
    public void shouldRaiseExceptionIfAvailabilityDoesNotExist() {
        when(availabilityRepository.findOne(availability().getId())).thenThrow(new AvailabilityNotFoundException(availability().getId()));

        updateAvailabilityCommand.run(randomUUID(), availability());
    }
}