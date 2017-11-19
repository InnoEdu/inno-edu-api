package inno.edu.api.domain.availability.queries;

import inno.edu.api.domain.availability.exceptions.AvailabilityNotFoundException;
import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.AvailabilityFactory.availability;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetAvailabilityByIdQueryTest {
    @Mock
    private AvailabilityRepository availabilityRepository;

    @InjectMocks
    private GetAvailabilityByIdQuery getAvailabilityByIdQuery;

    @Test(expected = AvailabilityNotFoundException.class)
    public void shouldThrowExceptionIfAvailabilityDoesNotExist() {
        when(availabilityRepository.findOne(availability().getId())).thenReturn(null);

        getAvailabilityByIdQuery.run(availability().getId());
    }

    @Test
    public void shouldReturnAvailability() {
        when(availabilityRepository.findOne(availability().getId())).thenReturn(availability());

        Availability availability = getAvailabilityByIdQuery.run(availability().getId());

        assertThat(availability, is(availability()));
    }
}