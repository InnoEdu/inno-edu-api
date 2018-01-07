package inno.edu.api.domain.profile.root.queries;

import inno.edu.api.domain.profile.root.models.Profile;
import inno.edu.api.domain.profile.root.repositories.ProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.ProfileFactory.profiles;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetProfilesQueryTest {
    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private GetProfilesQuery getProfilesQuery;

    @Test
    public void shouldReturnProfiles() {
        when(profileRepository.findAll()).thenReturn(profiles());

        List<Profile> profiles = getProfilesQuery.run(feiProfile().getId());

        assertThat(profiles, is(profiles()));
    }
}