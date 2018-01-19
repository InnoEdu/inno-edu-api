package inno.edu.api.controllers.profile;

import inno.edu.api.presentation.resources.profile.ExperienceResource;
import inno.edu.api.presentation.resources.ResourceBuilder;
import inno.edu.api.domain.profile.experience.commands.CreateExperienceCommand;
import inno.edu.api.domain.profile.experience.commands.DeleteExperienceCommand;
import inno.edu.api.domain.profile.experience.commands.UpdateExperienceCommand;
import inno.edu.api.domain.profile.experience.models.Experience;
import inno.edu.api.domain.profile.experience.queries.GetExperienceByIdQuery;
import inno.edu.api.domain.profile.experience.queries.GetExperiencesByProfileIdQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletRequestAttributes;

import static inno.edu.api.support.ProfileFactory.createFeiExperienceRequest;
import static inno.edu.api.support.ProfileFactory.feiExperience;
import static inno.edu.api.support.ProfileFactory.feiExperiences;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.ProfileFactory.updateFeiExperienceRequest;
import static inno.edu.api.support.ProfileFactory.updatedFeiExperience;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.web.context.request.RequestContextHolder.setRequestAttributes;

@RunWith(MockitoJUnitRunner.class)
public class ExperienceControllerTest {
    @Mock
    private ResourceBuilder resourceBuilder;

    @Mock
    private GetExperienceByIdQuery getExperienceByIdQuery;

    @Mock
    private GetExperiencesByProfileIdQuery getExperiencesByProfileIdQuery;

    @Mock
    private CreateExperienceCommand createExperienceCommand;

    @Mock
    private UpdateExperienceCommand updateExperienceCommand;

    @Mock
    private DeleteExperienceCommand deleteExperienceCommand;

    @InjectMocks
    private ExperienceController experienceController;

    @Before
    public void setup() {
        setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    public void shouldListExperiencesForProfile() {
        when(getExperiencesByProfileIdQuery.run(feiProfile().getId())).thenReturn(feiExperiences());

        experienceController.all(feiProfile().getId());

        verify(resourceBuilder).wrappedFrom(eq(feiExperiences()), any(), eq(ExperienceResource.class));
    }

    @Test
    public void shouldGetExperienceById() {
        when(getExperienceByIdQuery.run(eq(feiExperience().getId()))).thenReturn(feiExperience());

        ExperienceResource experienceResource = experienceController.get(feiExperience().getId());

        assertThat(experienceResource.getExperience(), is(feiExperience()));
    }

    @Test
    public void shouldCreateNewExperience() {
        when(createExperienceCommand.run(feiProfile().getId(), createFeiExperienceRequest())).thenReturn(feiExperience());

        ResponseEntity<Experience> entity = experienceController.post(feiProfile().getId(), createFeiExperienceRequest());

        assertThat(entity.getBody(), is(feiExperience()));
    }

    @Test
    public void shouldUpdateExperience() {
        when(updateExperienceCommand.run(feiExperience().getId(), updateFeiExperienceRequest())).thenReturn(updatedFeiExperience());

        ResponseEntity<Experience> entity = experienceController.put(feiExperience().getId(), updateFeiExperienceRequest());

        assertThat(entity.getBody(), is(updatedFeiExperience()));
    }

    @Test
    public void shouldDeleteExperience() {
        experienceController.delete(feiExperience().getId());

        verify(deleteExperienceCommand).run(feiExperience().getId());
    }
}