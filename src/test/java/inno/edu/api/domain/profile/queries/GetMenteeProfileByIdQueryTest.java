package inno.edu.api.domain.profile.queries;

import inno.edu.api.domain.profile.exceptions.ProfileNotFoundException;
import inno.edu.api.domain.profile.models.MenteeProfile;
import inno.edu.api.domain.profile.repositories.MenteeProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.alanProfile;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetMenteeProfileByIdQueryTest {
    @Mock
    private MenteeProfileRepository profileRepository;

    @InjectMocks
    private GetMenteeProfileByIdQuery getMenteeProfileByIdQuery;

    @Test(expected = ProfileNotFoundException.class)
    public void shouldThrowExceptionIfMenteeProfileDoesNotExist() {
        when(profileRepository.findOne(alanProfile().getId())).thenReturn(null);

        getMenteeProfileByIdQuery.run(alanProfile().getId());
    }

    @Test
    public void shouldReturnMenteeProfile() {
        when(profileRepository.findOne(alanProfile().getId())).thenReturn(alanProfile());

        MenteeProfile profile = getMenteeProfileByIdQuery.run(alanProfile().getId());

        assertThat(profile, is(alanProfile()));
    }
}