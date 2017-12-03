package inno.edu.api.domain.availability.queries;

import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import inno.edu.api.domain.profile.queries.GetMentorActiveProfileByUserIdQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static inno.edu.api.support.AvailabilityFactory.feiAvailability;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.UserFactory.fei;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetAvailabilityByMentorIdTest {
    @Mock
    private AvailabilityRepository availabilityRepository;

    @Mock
    private GetMentorActiveProfileByUserIdQuery getMentorActiveProfileByUserIdQuery;

    @InjectMocks
    private GetAvailabilityByMentorId getAvailabilityByMentorId;

    @Test
    public void shouldGetAvailabilityByMentor() {
        when(getMentorActiveProfileByUserIdQuery.run(fei().getId())).thenReturn(feiProfile());
        when(availabilityRepository.findByMentorProfileId(feiProfile().getId())).thenReturn(feiAvailability());

        List<Availability> availability = getAvailabilityByMentorId.run(fei().getId());

        assertThat(availability, is(feiAvailability()));
    }
}