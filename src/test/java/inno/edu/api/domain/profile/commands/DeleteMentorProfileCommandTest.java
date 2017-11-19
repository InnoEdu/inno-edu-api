package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.assertions.MentorProfileExistsAssertion;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.feiProfile;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeleteMentorProfileCommandTest {

    @Mock
    private MentorProfileExistsAssertion mentorProfileExistsAssertion;

    @Mock
    private MentorProfileRepository mentorProfileRepository;

    @InjectMocks
    private DeleteMentorProfileCommand deleteMentorProfileCommand;

    @Test
    public void shouldCallRepositoryToDeleteMentorProfile() {
        deleteMentorProfileCommand.run(feiProfile().getId());

        verify(mentorProfileRepository).delete(feiProfile().getId());
    }

    @Test
    public void shouldRunAllAssertions() {
        deleteMentorProfileCommand.run(feiProfile().getId());

        verify(mentorProfileExistsAssertion).run(feiProfile().getId());
    }
}