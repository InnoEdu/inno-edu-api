package inno.edu.api.domain.profile.experience.queries;

import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.domain.profile.experience.models.Experience;
import inno.edu.api.domain.profile.experience.repositories.ExperienceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static inno.edu.api.support.ProfileFactory.feiExperiences;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetExperiencesByProfileIdQueryTest {
    @Mock
    private ExperienceRepository experienceRepository;

    @Mock
    private ProfileExistsAssertion profileExistsAssertion;

    @InjectMocks
    private GetExperiencesByProfileIdQuery getExperiencesByProfileIdQuery;

    @Test
    public void shouldGetExperiencesByProfile() {
        when(experienceRepository.findByProfileId(feiProfile().getId())).thenReturn(feiExperiences());

        List<Experience> experiences = getExperiencesByProfileIdQuery.run(feiProfile().getId());

        assertThat(experiences, is(feiExperiences()));
    }

    @Test
    public void shouldRunAllAssertions() {
        getExperiencesByProfileIdQuery.run(feiProfile().getId());

        verify(profileExistsAssertion).run(feiProfile().getId());
    }

}