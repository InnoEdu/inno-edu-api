package inno.edu.api.domain.availability.commands;

import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import inno.edu.api.domain.profile.exceptions.ProfileNotFoundException;
import inno.edu.api.domain.user.repositories.MentorProfileRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.AvailabilityFactory.availability;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateAvailabilityCommandTest {
    @Mock
    private AvailabilityRepository availabilityRepository;

    @Mock
    private MentorProfileRepository mentorProfileRepository;

    @InjectMocks
    private CreateAvailabilityCommand createAvailabilityCommand;

    @Before
    public void setUp() {
        when(mentorProfileRepository.exists(any())).thenReturn(true);
    }

    @Test
    public void shouldCallRepositoryToSaveAvailability() {
        ArgumentCaptor<Availability> argumentCaptor = forClass(Availability.class);

        when(availabilityRepository.save(argumentCaptor.capture()))
                .thenAnswer((invocation -> invocation.getArguments()[0]));

        Availability availability = createAvailabilityCommand.run(availability());

        assertThat(availability, is(argumentCaptor.getValue()));
    }

    @Test
    public void shouldGenerateNewIdForAvailability() {
        ArgumentCaptor<Availability> argumentCaptor = forClass(Availability.class);

        when(availabilityRepository.save(argumentCaptor.capture())).thenReturn(availability());

        createAvailabilityCommand.run(availability());

        assertThat(argumentCaptor.getValue().getId(), not(availability().getId()));
    }

    @Test(expected = ProfileNotFoundException.class)
    public void shouldRaiseExceptionIfProfileDoesNotExist() {
        when(mentorProfileRepository.exists(availability().getMentorProfileId())).thenReturn(false);

        createAvailabilityCommand.run(availability());
    }
}