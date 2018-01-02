package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.commands.mappers.UpdateProfileRequestMapper;
import inno.edu.api.domain.profile.models.Profile;
import inno.edu.api.domain.profile.queries.GetProfileByIdQuery;
import inno.edu.api.domain.profile.repositories.ProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.newAlanProfile;
import static inno.edu.api.support.ProfileFactory.updateNewAlanProfileRequest;
import static inno.edu.api.support.ProfileFactory.updatedNewAlanProfile;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateProfileCommandTest {
    @Mock
    private UpdateProfileRequestMapper updateProfileRequestMapper;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private GetProfileByIdQuery getProfileByIdQuery;

    @InjectMocks
    private UpdateProfileCommand updateProfileCommand;

    @Test
    public void shouldReturnUpdatedProfile() {
        when(getProfileByIdQuery.run(newAlanProfile().getId())).thenReturn(newAlanProfile());
        when(profileRepository.save(newAlanProfile())).thenReturn(updatedNewAlanProfile());

        Profile Profile = updateProfileCommand.run(newAlanProfile().getId(), updateNewAlanProfileRequest());

        verify(updateProfileRequestMapper).setProfile(updateNewAlanProfileRequest(), newAlanProfile());

        assertThat(Profile, is(updatedNewAlanProfile()));
    }
}