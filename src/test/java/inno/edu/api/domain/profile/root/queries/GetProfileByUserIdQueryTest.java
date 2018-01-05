package inno.edu.api.domain.profile.root.queries;

import inno.edu.api.domain.profile.root.exceptions.UserProfileNotFoundException;
import inno.edu.api.domain.profile.root.models.Profile;
import inno.edu.api.domain.profile.root.repositories.ProfileRepository;
import inno.edu.api.support.ProfileFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.UserFactory.alan;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetProfileByUserIdQueryTest {
    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private GetProfileByUserIdQuery getProfileByUserIdQuery;

    @Test
    public void shouldGetProfile() {
        when(profileRepository.findOneByUserId(alan().getId())).thenReturn(ProfileFactory.alanProfile());

        Profile profile = getProfileByUserIdQuery.run(alan().getId());

        assertThat(profile, is(ProfileFactory.alanProfile()));
    }

    @Test(expected = UserProfileNotFoundException.class)
    public void shouldRaiseExceptionIsProfileNotFound() {
        when(profileRepository.findOneByUserId(alan().getId())).thenReturn(null);

        getProfileByUserIdQuery.run(alan().getId());
    }
}