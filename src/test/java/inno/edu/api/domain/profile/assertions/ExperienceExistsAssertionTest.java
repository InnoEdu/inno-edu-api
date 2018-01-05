package inno.edu.api.domain.profile.assertions;

import inno.edu.api.domain.profile.exceptions.ExperienceNotFoundException;
import inno.edu.api.domain.profile.repositories.ExperienceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.feiExperience;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExperienceExistsAssertionTest {
    @Mock
    private ExperienceRepository experienceRepository;

    @InjectMocks
    private ExperienceExistsAssertion experienceExistsAssertion;

    @Test(expected = ExperienceNotFoundException.class)
    public void shouldThrowExceptionIfExperienceDoesNotExist() {
        when(experienceRepository.exists(feiExperience().getId())).thenReturn(false);

        experienceExistsAssertion.run(feiExperience().getId());
    }

    @Test
    public void shouldNotThrowExceptionIfExperienceExists() {
        when(experienceRepository.exists(feiExperience().getId())).thenReturn(true);

        experienceExistsAssertion.run(feiExperience().getId());
    }
}