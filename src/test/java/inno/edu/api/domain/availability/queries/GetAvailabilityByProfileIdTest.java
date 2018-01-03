package inno.edu.api.domain.availability.queries;

import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static inno.edu.api.support.AvailabilityFactory.feiAvailability;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetAvailabilityByProfileIdTest {
    @Mock
    private AvailabilityRepository availabilityRepository;

    @InjectMocks
    private GetAvailabilityByProfileId getAvailabilityByProfileId;

    @Test
    public void shouldGetAvailabilityByProfile() {
        when(availabilityRepository.findByMentorProfileId(feiProfile().getId())).thenReturn(feiAvailability());

        List<Availability> availability = getAvailabilityByProfileId.run(feiProfile().getId());

        assertThat(availability, is(feiAvailability()));
    }
}