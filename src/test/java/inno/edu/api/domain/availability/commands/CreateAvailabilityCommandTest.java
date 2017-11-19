package inno.edu.api.domain.availability.commands;

import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import inno.edu.api.domain.profile.assertions.MentorProfileExistsAssertion;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateAvailabilityCommandTest {
    @Mock
    private AvailabilityRepository availabilityRepository;

    @Mock
    private MentorProfileExistsAssertion mentorProfileExistsAssertion;

    @InjectMocks
    private CreateAvailabilityCommand createAvailabilityCommand;

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

    @Test
    public void shouldRunAllAssertions() {
        createAvailabilityCommand.run(availability());

        verify(mentorProfileExistsAssertion).run(availability().getMentorProfileId());
    }
}