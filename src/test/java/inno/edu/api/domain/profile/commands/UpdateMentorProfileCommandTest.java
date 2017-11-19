package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.exceptions.ProfileNotFoundException;
import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.ProfileFactory.updatedFeiProfile;
import static java.util.UUID.randomUUID;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateMentorProfileCommandTest {
    @Mock
    private MentorProfileRepository mentorProfileRepository;

    @InjectMocks
    private UpdateMentorProfileCommand updateMentorProfileCommand;

    @Test
    public void shouldReturnUpdatedMentorProfile() {
        when(mentorProfileRepository.findOne(feiProfile().getId())).thenReturn(feiProfile());
        when(mentorProfileRepository.save(feiProfile())).thenReturn(updatedFeiProfile());

        MentorProfile mentorProfile = updateMentorProfileCommand.run(feiProfile().getId(), feiProfile());

        assertThat(mentorProfile, is(updatedFeiProfile()));
    }

    @Test(expected = ProfileNotFoundException.class)
    public void shouldRaiseExceptionIfMentorProfileDoesNotExist() {
        when(mentorProfileRepository.findOne(feiProfile().getId())).thenThrow(new ProfileNotFoundException(feiProfile().getId()));

        updateMentorProfileCommand.run(randomUUID(), feiProfile());
    }
}