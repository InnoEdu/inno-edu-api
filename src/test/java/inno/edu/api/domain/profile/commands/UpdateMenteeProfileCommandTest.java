package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.models.MenteeProfile;
import inno.edu.api.domain.profile.queries.GetMenteeProfileByIdQuery;
import inno.edu.api.domain.profile.repositories.MenteeProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.alanProfile;
import static inno.edu.api.support.ProfileFactory.updatedAlanProfile;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateMenteeProfileCommandTest {
    @Mock
    private MenteeProfileRepository menteeProfileRepository;

    @Mock
    private GetMenteeProfileByIdQuery getMenteeProfileByIdQuery;

    @InjectMocks
    private UpdateMenteeProfileCommand updateMenteeProfileCommand;

    @Test
    public void shouldReturnUpdatedMenteeProfile() {
        when(getMenteeProfileByIdQuery.run(alanProfile().getId())).thenReturn(alanProfile());
        when(menteeProfileRepository.save(alanProfile())).thenReturn(updatedAlanProfile());

        MenteeProfile menteeProfile = updateMenteeProfileCommand.run(alanProfile().getId(), alanProfile());

        assertThat(menteeProfile, is(updatedAlanProfile()));
    }
}