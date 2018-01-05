package inno.edu.api.domain.profile.experience.queries;

import inno.edu.api.domain.profile.experience.exceptions.ExperienceNotFoundException;
import inno.edu.api.domain.profile.experience.models.Experience;
import inno.edu.api.domain.profile.experience.repositories.ExperienceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.feiExperience;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetExperienceByIdQueryTest {
    @Mock
    private ExperienceRepository experienceRepository;

    @InjectMocks
    private GetExperienceByIdQuery getExperienceByIdQuery;

    @Test(expected = ExperienceNotFoundException.class)
    public void shouldThrowExceptionIfExperienceDoesNotExist() {
        when(experienceRepository.findOne(feiExperience().getId())).thenReturn(null);

        getExperienceByIdQuery.run(feiExperience().getId());
    }

    @Test
    public void shouldReturnExperience() {
        when(experienceRepository.findOne(feiExperience().getId())).thenReturn(feiExperience());

        Experience experience = getExperienceByIdQuery.run(feiExperience().getId());

        assertThat(experience, is(feiExperience()));
    }
}