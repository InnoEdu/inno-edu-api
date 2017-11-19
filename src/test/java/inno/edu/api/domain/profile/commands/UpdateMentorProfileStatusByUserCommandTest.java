package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.exceptions.UserProfileNotFoundException;
import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.profile.models.ProfileStatus;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
import inno.edu.api.domain.user.assertions.UserIsMentorAssertion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.domain.profile.models.ProfileStatus.CREATED;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.UserFactory.fei;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateMentorProfileStatusByUserCommandTest {
    @Mock
    private UserIsMentorAssertion userIsMentorAssertion;

    @Mock
    private MentorProfileRepository mentorProfileRepository;

    @InjectMocks
    private UpdateMentorProfileStatusByUserCommand updateMentorProfileStatusByUserCommand;

    @Test(expected = UserProfileNotFoundException.class)
    public void shouldThrowExceptionIfUserProfileDoesNotExist() {
        when(mentorProfileRepository.findOneByMentorIdAndStatus(fei().getId(), CREATED)).thenReturn(null);

        updateMentorProfileStatusByUserCommand.run(fei().getId(), ProfileStatus.ACTIVE);
    }

    @Test
    public void shouldUpdateProfileStatusToActive() {
        MentorProfile inactiveProfile = feiProfile().toBuilder()
                .status(CREATED)
                .build();

        when(mentorProfileRepository.findOneByMentorIdAndStatus(fei().getId(), CREATED)).thenReturn(inactiveProfile);

        updateMentorProfileStatusByUserCommand.run(fei().getId(), ProfileStatus.ACTIVE);

        verify(mentorProfileRepository).save(feiProfile());
    }

    @Test
    public void shouldRunAllAssertions() {
        when(mentorProfileRepository.findOneByMentorIdAndStatus(fei().getId(), CREATED)).thenReturn(feiProfile());

        updateMentorProfileStatusByUserCommand.run(fei().getId(), ProfileStatus.ACTIVE);

        verify(userIsMentorAssertion).run(fei().getId());
    }

}