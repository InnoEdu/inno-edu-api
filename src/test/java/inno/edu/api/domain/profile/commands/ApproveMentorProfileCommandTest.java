package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.commands.ApproveMentorProfileCommand;
import inno.edu.api.domain.user.exceptions.ProfileNotFoundException;
import inno.edu.api.domain.user.models.MentorProfile;
import inno.edu.api.domain.user.models.ProfileStatus;
import inno.edu.api.domain.user.repositories.MentorProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.UserFactory.feiProfile;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApproveMentorProfileCommandTest {
    @Mock
    private MentorProfileRepository mentorProfileRepository;

    @InjectMocks
    private ApproveMentorProfileCommand approveMentorProfileCommand;

    @Test(expected = ProfileNotFoundException.class)
    public void shouldThrowExceptionIfProfileDoesNotExist() {
        when(mentorProfileRepository.findOne(feiProfile().getId())).thenReturn(null);

        approveMentorProfileCommand.run(feiProfile().getId());
    }

    @Test
    public void shouldApproveMentorProfile() {
        MentorProfile inactiveProfile = feiProfile()
                .toBuilder()
                .status(ProfileStatus.CREATED)
                .build();

        when(mentorProfileRepository.findOne(feiProfile().getId())).thenReturn(inactiveProfile);

        approveMentorProfileCommand.run(feiProfile().getId());

        verify(mentorProfileRepository).save(feiProfile());
    }
}