package inno.edu.api.domain.skill.commands;

import inno.edu.api.domain.skill.assertions.SkillExistsAssertion;
import inno.edu.api.domain.skill.repositories.SkillRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.SkillFactory.skill;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeleteSkillCommandTest {
    @Mock
    private SkillExistsAssertion skillExistsAssertion;

    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private DeleteSkillCommand deleteSkillCommand;

    @Test
    public void shouldCallRepositoryToDeleteSkill() {
        deleteSkillCommand.run(skill().getId());

        verify(skillRepository).delete(skill().getId());
    }

    @Test
    public void shouldRunAllAssertions() {
        deleteSkillCommand.run(skill().getId());

        verify(skillExistsAssertion).run(skill().getId());
    }
}