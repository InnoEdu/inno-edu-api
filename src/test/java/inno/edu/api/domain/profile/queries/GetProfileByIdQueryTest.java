package inno.edu.api.domain.profile.queries;

import inno.edu.api.domain.profile.exceptions.ProfileNotFoundException;
import inno.edu.api.domain.profile.models.Profile;
import inno.edu.api.domain.profile.repositories.ProfileRepository;
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
public class GetProfileByIdQueryTest {
    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private GetProfileByIdQuery getProfileByIdQuery;

    @Test(expected = ProfileNotFoundException.class)
    public void shouldThrowExceptionIfProfileDoesNotExist() {
        when(profileRepository.findOne(alanProfile().getId())).thenReturn(null);

        getProfileByIdQuery.run(alanProfile().getId());
    }

    @Test
    public void shouldReturnProfile() {
        when(profileRepository.findOne(alanProfile().getId())).thenReturn(alanProfile());

        Profile profile = getProfileByIdQuery.run(alanProfile().getId());

        assertThat(profile, is(alanProfile()));
    }
}