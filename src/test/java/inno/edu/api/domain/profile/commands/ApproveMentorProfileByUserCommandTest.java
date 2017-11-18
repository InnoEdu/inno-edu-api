package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.user.exceptions.UserIsNotMentorException;
import inno.edu.api.domain.user.exceptions.UserNotFoundException;
import inno.edu.api.domain.profile.exceptions.UserProfileNotFoundException;
import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
import inno.edu.api.domain.user.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.domain.profile.models.ProfileStatus.CREATED;
import static inno.edu.api.support.UserFactory.alan;
import static inno.edu.api.support.UserFactory.fei;
import static inno.edu.api.support.UserFactory.feiProfile;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApproveMentorProfileByUserCommandTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private MentorProfileRepository mentorProfileRepository;

    @InjectMocks
    private ApproveMentorProfileByUserCommand approveMentorProfileByUserCommand;

    @Test(expected = UserIsNotMentorException.class)
    public void shouldThrowExceptionIfUserIsNotMentor() {
        when(userRepository.findOne(alan().getId())).thenReturn(alan());

        approveMentorProfileByUserCommand.run(alan().getId());
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldThrowExceptionIfUserDoesNotExist() {
        when(userRepository.findOne(alan().getId())).thenReturn(null);

        approveMentorProfileByUserCommand.run(alan().getId());
    }

    @Test(expected = UserProfileNotFoundException.class)
    public void shouldThrowExceptionIfUserProfileDoesNotExist() {
        when(userRepository.findOne(fei().getId())).thenReturn(fei());
        when(mentorProfileRepository.findOneByMentorIdAndStatus(fei().getId(), CREATED)).thenReturn(null);

        approveMentorProfileByUserCommand.run(fei().getId());
    }

    @Test
    public void shouldUpdateProfileStatusToActive() {
        MentorProfile inactiveProfile = feiProfile().toBuilder()
                .status(CREATED)
                .build();

        when(userRepository.findOne(fei().getId())).thenReturn(fei());
        when(mentorProfileRepository.findOneByMentorIdAndStatus(fei().getId(), CREATED)).thenReturn(inactiveProfile);

        approveMentorProfileByUserCommand.run(fei().getId());

        verify(mentorProfileRepository).save(feiProfile());
    }
}