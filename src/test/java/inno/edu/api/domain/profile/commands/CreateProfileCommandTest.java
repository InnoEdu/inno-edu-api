package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.commands.mappers.CreateProfileRequestMapper;
import inno.edu.api.domain.profile.exceptions.ProfileAlreadyCreatedException;
import inno.edu.api.domain.profile.models.Profile;
import inno.edu.api.domain.profile.repositories.ProfileRepository;
import inno.edu.api.domain.user.assertions.UserExistsAssertion;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.createNewAlanProfileRequest;
import static inno.edu.api.support.ProfileFactory.newAlanProfile;
import static inno.edu.api.support.ProfileFactory.newNewAlanProfile;
import static java.util.UUID.randomUUID;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateProfileCommandTest {
    @Mock
    private UUIDGeneratorService uuidGeneratorService;

    @Mock
    private CreateProfileRequestMapper createProfileRequestMapper;

    @Mock
    private UserExistsAssertion userExistsAssertion;

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private CreateProfileCommand createProfileCommand;

    @Before
    public void setUp() {
        when(uuidGeneratorService.generate()).thenReturn(randomUUID());

        when(createProfileRequestMapper.toProfile(createNewAlanProfileRequest()))
                .thenReturn(newNewAlanProfile(null));
    }

    @Test
    public void shouldSaveNewProfile() {
        Profile newProfile = newNewAlanProfile(uuidGeneratorService.generate());
        when(profileRepository.save(newProfile)).thenReturn(newProfile);

        Profile savedProfile = createProfileCommand.run(createNewAlanProfileRequest());
        assertThat(savedProfile, is(newProfile));
    }

    @Test(expected = ProfileAlreadyCreatedException.class)
    public void shouldNotAllowMultipleProfiles() {
        when(profileRepository.existsByUserId(newAlanProfile().getUserId())).thenReturn(true);

        createProfileCommand.run(createNewAlanProfileRequest());
    }

    @Test
    public void shouldRunAllAssertions() {
        createProfileCommand.run(createNewAlanProfileRequest());

        verify(userExistsAssertion).run(newAlanProfile().getUserId());
    }
}