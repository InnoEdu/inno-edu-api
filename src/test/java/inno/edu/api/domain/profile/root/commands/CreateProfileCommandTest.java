package inno.edu.api.domain.profile.root.commands;

import inno.edu.api.domain.profile.root.models.dtos.mappers.CreateProfileRequestMapper;
import inno.edu.api.domain.profile.root.exceptions.ProfileAlreadyCreatedException;
import inno.edu.api.domain.profile.root.models.Profile;
import inno.edu.api.domain.profile.root.repositories.ProfileRepository;
import inno.edu.api.domain.user.root.assertions.UserExistsAssertion;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.alanProfile;
import static inno.edu.api.support.ProfileFactory.createAlanProfileRequest;
import static inno.edu.api.support.ProfileFactory.newAlanProfile;
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

        when(createProfileRequestMapper.toProfile(createAlanProfileRequest()))
                .thenReturn(newAlanProfile(null));
    }

    @Test
    public void shouldSaveNewProfile() {
        Profile newProfile = newAlanProfile(uuidGeneratorService.generate());
        when(profileRepository.save(newProfile)).thenReturn(newProfile);

        Profile savedProfile = createProfileCommand.run(createAlanProfileRequest());
        assertThat(savedProfile, is(newProfile));
    }

    @Test(expected = ProfileAlreadyCreatedException.class)
    public void shouldNotAllowMultipleProfiles() {
        when(profileRepository.existsByUserId(alanProfile().getUserId())).thenReturn(true);

        createProfileCommand.run(createAlanProfileRequest());
    }

    @Test
    public void shouldRunAllAssertions() {
        createProfileCommand.run(createAlanProfileRequest());

        verify(userExistsAssertion).run(alanProfile().getUserId());
    }
}