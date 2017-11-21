package inno.edu.api.domain.availability.commands;

import inno.edu.api.domain.availability.commands.mappers.UpdateAvailabilityRequestMapper;
import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.availability.queries.GetAvailabilityByIdQuery;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.AvailabilityFactory.availability;
import static inno.edu.api.support.AvailabilityFactory.updateAvailabilityRequest;
import static inno.edu.api.support.AvailabilityFactory.updatedAvailability;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateAvailabilityCommandTest {
    @Mock
    private UpdateAvailabilityRequestMapper updateAvailabilityRequestMapper;

    @Mock
    private AvailabilityRepository availabilityRepository;

    @Mock
    private GetAvailabilityByIdQuery getAvailabilityByIdQuery;

    @InjectMocks
    private UpdateAvailabilityCommand updateAvailabilityCommand;

    @Test
    public void shouldReturnUpdatedAvailability() {
        when(getAvailabilityByIdQuery.run(availability().getId())).thenReturn(availability());
        when(availabilityRepository.save(availability())).thenReturn(updatedAvailability());

        Availability availability = updateAvailabilityCommand.run(availability().getId(), updateAvailabilityRequest());

        verify(updateAvailabilityRequestMapper).setAvailability(updateAvailabilityRequest(), availability());

        assertThat(availability, is(updatedAvailability()));
    }
}