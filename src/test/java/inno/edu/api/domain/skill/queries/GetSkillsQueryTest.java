package inno.edu.api.domain.skill.queries;

import inno.edu.api.domain.skill.models.Skill;
import inno.edu.api.domain.skill.repositories.SkillRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static inno.edu.api.support.SkillFactory.skills;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetSkillsQueryTest {
    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private GetSkillsQuery getSkillsQuery;

    @Test
    public void shouldReturnSkills() {
        when(skillRepository.findAll()).thenReturn(skills());

        List<Skill> skills = getSkillsQuery.run();

        assertThat(skills, is(skills()));
    }
}