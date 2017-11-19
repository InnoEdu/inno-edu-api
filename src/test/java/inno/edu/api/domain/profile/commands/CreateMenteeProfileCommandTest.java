package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.exceptions.MenteeProfileAlreadyCreatedException;
import inno.edu.api.domain.profile.models.MenteeProfile;
import inno.edu.api.domain.profile.repositories.MenteeProfileRepository;
import inno.edu.api.domain.user.assertions.UserExistsAssertion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.UserFactory.alanProfile;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateMenteeProfileCommandTest {
    @Mock
    private UserExistsAssertion userExistsAssertion;

    @Mock
    private MenteeProfileRepository menteeProfileRepository;

    @InjectMocks
    private CreateMenteeProfileCommand createMenteeProfileCommand;

    @Test
    public void shouldCallRepositoryToSaveMenteeProfile() {
        ArgumentCaptor<MenteeProfile> argumentCaptor = forClass(MenteeProfile.class);

        when(menteeProfileRepository.save(argumentCaptor.capture()))
                .thenAnswer((invocation -> invocation.getArguments()[0]));

        MenteeProfile menteeProfile = createMenteeProfileCommand.run(alanProfile());

        assertThat(menteeProfile, is(argumentCaptor.getValue()));
    }

    @Test
    public void shouldGenerateNewIdForMenteeProfile() {
        ArgumentCaptor<MenteeProfile> argumentCaptor = forClass(MenteeProfile.class);

        when(menteeProfileRepository.save(argumentCaptor.capture())).thenReturn(alanProfile());

        createMenteeProfileCommand.run(alanProfile());

        assertThat(argumentCaptor.getValue().getId(), not(alanProfile().getId()));
    }

    @Test(expected = MenteeProfileAlreadyCreatedException.class)
    public void shouldNotAllowMultipleMenteeProfiles() {
        when(menteeProfileRepository.existsByMenteeId(alanProfile().getMenteeId())).thenReturn(true);

        createMenteeProfileCommand.run(alanProfile());
    }

    @Test
    public void shouldRunAllAssertions() {
        createMenteeProfileCommand.run(alanProfile());

        verify(userExistsAssertion).run(alanProfile().getMenteeId());
    }
}