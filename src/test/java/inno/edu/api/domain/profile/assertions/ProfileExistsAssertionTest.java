package inno.edu.api.domain.profile.assertions;

import inno.edu.api.domain.profile.exceptions.ProfileNotFoundException;
import inno.edu.api.domain.profile.repositories.ProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.newAlanProfile;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProfileExistsAssertionTest {
    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private ProfileExistsAssertion profileExistsAssertion;

    @Test(expected = ProfileNotFoundException.class)
    public void shouldThrowExceptionIfProfileDoesNotExist() {
        when(profileRepository.exists(newAlanProfile().getId())).thenReturn(false);

        profileExistsAssertion.run(newAlanProfile().getId());
    }

    @Test
    public void shouldNotThrowExceptionIfProfileExists() {
        when(profileRepository.exists(newAlanProfile().getId())).thenReturn(true);

        profileExistsAssertion.run(newAlanProfile().getId());
    }
}