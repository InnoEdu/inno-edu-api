package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.assertions.ProfileExistsAssertion;
import inno.edu.api.domain.profile.repositories.ProfileRepository;
import inno.edu.api.support.ProfileFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeleteProfileCommandTest {
    @Mock
    private ProfileExistsAssertion profileExistsAssertion;

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private DeleteProfileCommand deleteProfileCommand;

    @Test
    public void shouldCallRepositoryToDeleteProfile() {
        deleteProfileCommand.run(ProfileFactory.alanProfile().getId());

        verify(profileRepository).delete(ProfileFactory.alanProfile().getId());
    }

    @Test
    public void shouldRunAllAssertions() {
        deleteProfileCommand.run(ProfileFactory.alanProfile().getId());

        verify(profileExistsAssertion).run(ProfileFactory.alanProfile().getId());
    }
}