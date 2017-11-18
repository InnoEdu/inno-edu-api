package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.commands.DeactivateMentorProfilesCommand;
import inno.edu.api.domain.user.exceptions.UserNotFoundException;
import inno.edu.api.domain.user.models.MentorProfile;
import inno.edu.api.domain.user.repositories.MentorProfileRepository;
import inno.edu.api.domain.user.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.domain.user.models.ProfileStatus.INACTIVE;
import static inno.edu.api.support.UserFactory.fei;
import static inno.edu.api.support.UserFactory.feiProfile;
import static inno.edu.api.support.UserFactory.mentorProfiles;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeactivateMentorProfilesCommandTest {
    @Mock
    private MentorProfileRepository mentorProfileRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DeactivateMentorProfilesCommand deactivateMentorProfilesCommand;

    @Test(expected = UserNotFoundException.class)
    public void shouldThrowExceptionIfUserDoesNotExist() {
        when(userRepository.existsByIdAndIsMentorIsTrue(fei().getId())).thenReturn(false);

        deactivateMentorProfilesCommand.run(fei().getId());
    }

    @Test
    public void shouldDeactivateAllProfilesForMentor() {
        when(userRepository.existsByIdAndIsMentorIsTrue(fei().getId())).thenReturn(true);
        when(mentorProfileRepository.findByMentorId(fei().getId())).thenReturn(mentorProfiles());

        deactivateMentorProfilesCommand.run(fei().getId());

        MentorProfile inactiveProfile = feiProfile()
                .toBuilder()
                .status(INACTIVE).build();

        verify(mentorProfileRepository).save(inactiveProfile);
    }
}