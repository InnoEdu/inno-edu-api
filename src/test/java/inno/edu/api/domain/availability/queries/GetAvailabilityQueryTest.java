package inno.edu.api.domain.availability.queries;

import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static inno.edu.api.support.AvailabilityFactory.allAvailability;
import static inno.edu.api.support.AvailabilityFactory.availability;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetAvailabilityQueryTest {
    @Mock
    private AvailabilityRepository availabilityRepository;

    @InjectMocks
    private GetAvailabilityQuery getAvailabilityQuery;

    @Test
    public void shouldReturnAvailability() {
        when(availabilityRepository.findAll()).thenReturn(allAvailability());

        List<Availability> availabilitys = getAvailabilityQuery.run(availability().getId());

        assertThat(availabilitys, is(allAvailability()));
    }
}