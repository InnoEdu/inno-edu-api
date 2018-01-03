package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.models.Profile;
import inno.edu.api.domain.profile.models.ProfileStatus;
import inno.edu.api.domain.profile.queries.GetProfileByIdQuery;
import inno.edu.api.domain.profile.repositories.ProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.newAlanProfile;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateProfileStatusCommandTest {
    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private GetProfileByIdQuery getProfileByIdQuery;

    @InjectMocks
    private UpdateProfileStatusCommand updateProfileStatusCommand;

    @Test
    public void shouldApproveMentorProfile() {
        Profile inactiveProfile = newAlanProfile()
                .toBuilder()
                .status(ProfileStatus.ACTIVE)
                .build();

        when(getProfileByIdQuery.run(newAlanProfile().getId())).thenReturn(inactiveProfile);

        updateProfileStatusCommand.run(newAlanProfile().getId(), ProfileStatus.CREATED);

        verify(profileRepository).save(newAlanProfile());
    }
}