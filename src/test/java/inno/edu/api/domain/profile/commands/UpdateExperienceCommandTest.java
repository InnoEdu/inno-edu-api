package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.commands.mappers.UpdateExperienceRequestMapper;
import inno.edu.api.domain.profile.models.Experience;
import inno.edu.api.domain.profile.queries.GetExperienceByIdQuery;
import inno.edu.api.domain.profile.repositories.ExperienceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.feiExperience;
import static inno.edu.api.support.ProfileFactory.updateFeiExperienceRequest;
import static inno.edu.api.support.ProfileFactory.updatedFeiExperience;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateExperienceCommandTest {
    @Mock
    private UpdateExperienceRequestMapper updateExperienceRequestMapper;

    @Mock
    private ExperienceRepository experienceRepository;

    @Mock
    private GetExperienceByIdQuery getExperienceByIdQuery;

    @InjectMocks
    private UpdateExperienceCommand updateExperienceCommand;

    @Test
    public void shouldReturnUpdatedExperience() {
        when(getExperienceByIdQuery.run(feiExperience().getId())).thenReturn(feiExperience());
        when(experienceRepository.save(feiExperience())).thenReturn(updatedFeiExperience());

        Experience Experience = updateExperienceCommand.run(feiExperience().getId(), updateFeiExperienceRequest());

        verify(updateExperienceRequestMapper).setExperience(updateFeiExperienceRequest(), feiExperience());

        assertThat(Experience, is(updatedFeiExperience()));
    }
}