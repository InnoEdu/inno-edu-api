package inno.edu.api.domain.skill.commands;

import inno.edu.api.domain.skill.models.dtos.mappers.UpdateSkillRequestMapper;
import inno.edu.api.domain.skill.models.Skill;
import inno.edu.api.domain.skill.queries.GetSkillByIdQuery;
import inno.edu.api.domain.skill.repositories.SkillRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.SkillFactory.skill;
import static inno.edu.api.support.SkillFactory.updateSkillRequest;
import static inno.edu.api.support.SkillFactory.updatedSkill;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateSkillCommandTest {
    @Mock
    private UpdateSkillRequestMapper updateSkillRequestMapper;

    @Mock
    private SkillRepository skillRepository;

    @Mock
    private GetSkillByIdQuery getSkillByIdQuery;

    @InjectMocks
    private UpdateSkillCommand updateSkillCommand;

    @Test
    public void shouldReturnUpdatedSkill() {
        when(getSkillByIdQuery.run(skill().getId())).thenReturn(skill());
        when(skillRepository.save(skill())).thenReturn(updatedSkill());

        Skill skill = updateSkillCommand.run(skill().getId(), updateSkillRequest());

        verify(updateSkillRequestMapper).setSkill(updateSkillRequest(), skill());

        assertThat(skill, is(updatedSkill()));
    }
}