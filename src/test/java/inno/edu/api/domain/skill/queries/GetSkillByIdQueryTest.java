package inno.edu.api.domain.skill.queries;

import inno.edu.api.domain.skill.exceptions.SkillNotFoundException;
import inno.edu.api.domain.skill.models.Skill;
import inno.edu.api.domain.skill.repositories.SkillRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.SkillFactory.skill;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetSkillByIdQueryTest {
    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private GetSkillByIdQuery getSkillByIdQuery;

    @Test(expected = SkillNotFoundException.class)
    public void shouldThrowExceptionIfSkillDoesNotExist() {
        when(skillRepository.findOne(skill().getId())).thenReturn(null);

        getSkillByIdQuery.run(skill().getId());
    }

    @Test
    public void shouldReturnSkill() {
        when(skillRepository.findOne(skill().getId())).thenReturn(skill());

        Skill skill = getSkillByIdQuery.run(skill().getId());

        assertThat(skill, is(skill()));
    }
}