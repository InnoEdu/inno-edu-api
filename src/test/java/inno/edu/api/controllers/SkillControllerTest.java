package inno.edu.api.controllers;

import inno.edu.api.presentation.resources.ResourceBuilder;
import inno.edu.api.domain.skill.models.resources.SkillResource;
import inno.edu.api.domain.skill.commands.CreateSkillCommand;
import inno.edu.api.domain.skill.commands.DeleteSkillCommand;
import inno.edu.api.domain.skill.commands.UpdateSkillCommand;
import inno.edu.api.domain.skill.models.Skill;
import inno.edu.api.domain.skill.queries.GetSkillByIdQuery;
import inno.edu.api.domain.skill.queries.GetSkillsQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletRequestAttributes;

import static inno.edu.api.support.SkillFactory.createSkillRequest;
import static inno.edu.api.support.SkillFactory.skill;
import static inno.edu.api.support.SkillFactory.skills;
import static inno.edu.api.support.SkillFactory.updateSkillRequest;
import static inno.edu.api.support.SkillFactory.updatedSkill;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.web.context.request.RequestContextHolder.setRequestAttributes;

@RunWith(MockitoJUnitRunner.class)
public class SkillControllerTest {
    @Mock
    private ResourceBuilder resourceBuilder;

    @Mock
    private GetSkillsQuery getSkillsQuery;

    @Mock
    private CreateSkillCommand createSkillCommand;

    @Mock
    private UpdateSkillCommand updateSkillCommand;

    @Mock
    private DeleteSkillCommand deleteSkillCommand;

    @Mock
    private GetSkillByIdQuery getSkillByIdQuery;

    @InjectMocks
    private SkillController skillController;

    @Before
    public void setup() {
        setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    public void shouldGetSkillById() {
        when(getSkillByIdQuery.run(eq(skill().getId()))).thenReturn(skill());

        SkillResource skillResource = skillController.get(skill().getId());

        assertThat(skillResource.getSkill(), is(skill()));
    }

    @Test
    public void shouldListSkills() {
        when(getSkillsQuery.run()).thenReturn(skills());

        skillController.all();

        verify(resourceBuilder).wrappedFrom(eq(skills()), any(), eq(SkillResource.class));
    }

    @Test
    public void shouldCreateNewSkill() {
        when(createSkillCommand.run(createSkillRequest())).thenReturn(skill());

        ResponseEntity<Skill> entity = skillController.post(createSkillRequest());

        assertThat(entity.getBody(), is(skill()));
    }

    @Test
    public void shouldUpdateSkill() {
        when(updateSkillCommand.run(skill().getId(), updateSkillRequest())).thenReturn(updatedSkill());

        ResponseEntity<Skill> entity = skillController.put(skill().getId(), updateSkillRequest());

        assertThat(entity.getBody(), is(updatedSkill()));
    }

    @Test
    public void shouldDeleteSkill() {
        skillController.delete(skill().getId());

        verify(deleteSkillCommand).run(skill().getId());
    }
}