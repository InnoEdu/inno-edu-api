package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.assertions.MenteeProfileExistsAssertion;
import inno.edu.api.domain.profile.repositories.MenteeProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.alanProfile;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeleteMenteeProfileCommandTest {

    @Mock
    private MenteeProfileExistsAssertion menteeProfileExistsAssertion;

    @Mock
    private MenteeProfileRepository menteeProfileRepository;

    @InjectMocks
    private DeleteMenteeProfileCommand deleteMenteeProfileCommand;

    @Test
    public void shouldCallRepositoryToDeleteMenteeProfile() {
        deleteMenteeProfileCommand.run(alanProfile().getId());

        verify(menteeProfileRepository).delete(alanProfile().getId());
    }

    @Test
    public void shouldRunAllAssertions() {
        deleteMenteeProfileCommand.run(alanProfile().getId());

        verify(menteeProfileExistsAssertion).run(alanProfile().getId());
    }
}