package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.commands.mappers.UpdateMentorProfileRequestMapper;
import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.profile.queries.GetMentorProfileByIdQuery;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.ProfileFactory.updateFeiProfileRequest;
import static inno.edu.api.support.ProfileFactory.updatedFeiProfile;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateMentorProfileCommandTest {
    @Mock
    private UpdateMentorProfileRequestMapper updateProfileToProfileMapper;

    @Mock
    private GetMentorProfileByIdQuery getMentorProfileByIdQuery;

    @Mock
    private MentorProfileRepository mentorProfileRepository;

    @InjectMocks
    private UpdateMentorProfileCommand updateMentorProfileCommand;

    @Test
    public void shouldReturnUpdatedMentorProfile() {
        when(getMentorProfileByIdQuery.run(feiProfile().getId())).thenReturn(feiProfile());
        when(mentorProfileRepository.save(feiProfile())).thenReturn(updatedFeiProfile());

        MentorProfile mentorProfile = updateMentorProfileCommand.run(feiProfile().getId(), updateFeiProfileRequest());

        verify(updateProfileToProfileMapper).setMentorProfile(updateFeiProfileRequest(), feiProfile());

        assertThat(mentorProfile, is(updatedFeiProfile()));
    }
}