package inno.edu.api.controllers.profile;

import inno.edu.api.controllers.profile.resources.ProfileSkillResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.controllers.resources.SkillResource;
import inno.edu.api.domain.profile.skill.commands.CreateProfileSkillCommand;
import inno.edu.api.domain.profile.skill.commands.DeleteProfileSkillCommand;
import inno.edu.api.domain.profile.skill.models.ProfileSkill;
import inno.edu.api.domain.profile.skill.queries.GetProfileSkillsByProfileIdQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletRequestAttributes;

import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.ProfileFactory.feiProfileSkill;
import static inno.edu.api.support.SkillFactory.skill;
import static inno.edu.api.support.SkillFactory.skills;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.web.context.request.RequestContextHolder.setRequestAttributes;

@RunWith(MockitoJUnitRunner.class)
public class ProfileSkillControllerTest {
    @Mock
    private ResourceBuilder resourceBuilder;

    @Mock
    private GetProfileSkillsByProfileIdQuery getProfileSkillsByProfileIdQuery;

    @Mock
    private CreateProfileSkillCommand createProfileSkillCommand;

    @Mock
    private DeleteProfileSkillCommand deleteProfileSkillCommand;

    @InjectMocks
    private ProfileSkillController profileSkillController;

    @Before
    public void setup() {
        setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    public void shouldListSkills() {
        when(getProfileSkillsByProfileIdQuery.run(feiProfile().getId())).thenReturn(skills());

        profileSkillController.all(feiProfile().getId());

        verify(resourceBuilder).wrappedFrom(eq(skills()), any(), eq(SkillResource.class));
    }

    @Test
    public void shouldCreateSkill() {
        ProfileSkill expectedProfileSkill = feiProfileSkill();

        when(createProfileSkillCommand.run(feiProfile().getId(), skill().getId())).thenReturn(expectedProfileSkill);

        ProfileSkillResource skillResource = profileSkillController.post(
                feiProfile().getId(),
                skill().getId()
        );

        assertThat(skillResource.getProfileSkill(), is(expectedProfileSkill));
    }

    @Test
    public void shouldDeleteSkill() {
        profileSkillController.delete(feiProfile().getId(), skill().getId());

        verify(deleteProfileSkillCommand).run(feiProfile().getId(), skill().getId());
    }

}