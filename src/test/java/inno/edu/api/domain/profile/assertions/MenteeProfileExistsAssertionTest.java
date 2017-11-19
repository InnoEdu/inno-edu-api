package inno.edu.api.domain.profile.assertions;

import inno.edu.api.domain.profile.exceptions.ProfileNotFoundException;
import inno.edu.api.domain.profile.repositories.MenteeProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.feiProfile;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MenteeProfileExistsAssertionTest {
    @Mock
    private MenteeProfileRepository profileRepository;

    @InjectMocks
    private MenteeProfileExistsAssertion profileExistsAssertion;

    @Test(expected = ProfileNotFoundException.class)
    public void shouldThrowExceptionIfMenteeProfileDoesNotExist() {
        when(profileRepository.exists(feiProfile().getId())).thenReturn(false);

        profileExistsAssertion.run(feiProfile().getId());
    }

    @Test
    public void shouldNotThrowExceptionIfMenteeProfileExists() {
        when(profileRepository.exists(feiProfile().getId())).thenReturn(true);

        profileExistsAssertion.run(feiProfile().getId());
    }
}