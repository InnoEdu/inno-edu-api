package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.exceptions.ProfileNotFoundException;
import inno.edu.api.domain.profile.models.MenteeProfile;
import inno.edu.api.domain.user.repositories.MenteeProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.UserFactory.alanProfile;
import static inno.edu.api.support.UserFactory.updatedAlanProfile;
import static java.util.UUID.randomUUID;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateMenteeProfileCommandTest {
    @Mock
    private MenteeProfileRepository menteeProfileRepository;

    @InjectMocks
    private UpdateMenteeProfileCommand updateMenteeProfileCommand;

    @Test
    public void shouldReturnUpdatedMenteeProfile() {
        when(menteeProfileRepository.findOne(alanProfile().getId())).thenReturn(alanProfile());
        when(menteeProfileRepository.save(alanProfile())).thenReturn(updatedAlanProfile());

        MenteeProfile menteeProfile = updateMenteeProfileCommand.run(alanProfile().getId(), alanProfile());

        assertThat(menteeProfile, is(updatedAlanProfile()));
    }

    @Test(expected = ProfileNotFoundException.class)
    public void shouldRaiseExceptionIfMenteeProfileDoesNotExist() {
        when(menteeProfileRepository.findOne(alanProfile().getId())).thenThrow(new ProfileNotFoundException(alanProfile().getId()));

        updateMenteeProfileCommand.run(randomUUID(), alanProfile());
    }
}