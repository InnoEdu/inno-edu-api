package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.assertions.ProfileExistsAssertion;
import inno.edu.api.domain.profile.repositories.ProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.alanProfile;
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
        deleteProfileCommand.run(alanProfile().getId());

        verify(profileRepository).delete(alanProfile().getId());
    }

    @Test
    public void shouldRunAllAssertions() {
        deleteProfileCommand.run(alanProfile().getId());

        verify(profileExistsAssertion).run(alanProfile().getId());
    }
}