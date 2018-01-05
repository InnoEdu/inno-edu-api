package inno.edu.api.domain.profile.commands;


import inno.edu.api.domain.profile.assertions.ExperienceExistsAssertion;
import inno.edu.api.domain.profile.repositories.ExperienceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.feiExperience;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeleteExperienceCommandTest {
    @Mock
    private ExperienceExistsAssertion experienceExistsAssertion;

    @Mock
    private ExperienceRepository experienceRepository;

    @InjectMocks
    private DeleteExperienceCommand deleteExperienceCommand;

    @Test
    public void shouldCallRepositoryToDeleteExperience() {
        deleteExperienceCommand.run(feiExperience().getId());

        verify(experienceRepository).delete(feiExperience().getId());
    }

    @Test
    public void shouldRunAllAssertions() {
        deleteExperienceCommand.run(feiExperience().getId());

        verify(experienceExistsAssertion).run(feiExperience().getId());
    }

}