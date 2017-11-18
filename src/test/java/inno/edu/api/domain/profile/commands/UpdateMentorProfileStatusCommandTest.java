package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.exceptions.ProfileNotFoundException;
import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.profile.models.ProfileStatus;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.UserFactory.feiProfile;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateMentorProfileStatusCommandTest {
    @Mock
    private MentorProfileRepository mentorProfileRepository;

    @InjectMocks
    private UpdateMentorProfileStatusCommand updateMentorProfileStatusCommand;

    @Test(expected = ProfileNotFoundException.class)
    public void shouldThrowExceptionIfProfileDoesNotExist() {
        when(mentorProfileRepository.findOne(feiProfile().getId())).thenReturn(null);

        updateMentorProfileStatusCommand.run(feiProfile().getId(), ProfileStatus.ACTIVE);
    }

    @Test
    public void shouldApproveMentorProfile() {
        MentorProfile inactiveProfile = feiProfile()
                .toBuilder()
                .status(ProfileStatus.CREATED)
                .build();

        when(mentorProfileRepository.findOne(feiProfile().getId())).thenReturn(inactiveProfile);

        updateMentorProfileStatusCommand.run(feiProfile().getId(), ProfileStatus.ACTIVE);

        verify(mentorProfileRepository).save(feiProfile());
    }
}