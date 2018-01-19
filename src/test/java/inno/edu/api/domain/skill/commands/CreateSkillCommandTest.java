package inno.edu.api.domain.skill.commands;

import inno.edu.api.domain.skill.commands.mappers.CreateSkillRequestMapper;
import inno.edu.api.domain.skill.models.Skill;
import inno.edu.api.domain.skill.repositories.SkillRepository;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.SkillFactory.createSkillRequest;
import static inno.edu.api.support.SkillFactory.newSkill;
import static java.util.UUID.randomUUID;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateSkillCommandTest {
    @Mock
    private UUIDGeneratorService uuidGeneratorService;

    @Mock
    private CreateSkillRequestMapper createSkillRequestMapper;

    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private CreateSkillCommand createSkillCommand;

    @Before
    public void setUp() {
        when(uuidGeneratorService.generate()).thenReturn(randomUUID());
        when(createSkillRequestMapper.toSkill(createSkillRequest())).thenReturn(newSkill(null));
    }

    @Test
    public void shouldCallRepositoryToSaveSkill() {
        Skill newSkill = newSkill(uuidGeneratorService.generate());

        when(skillRepository.save(newSkill)).thenReturn(newSkill);

        Skill savedSkill = createSkillCommand.run(createSkillRequest());
        assertThat(savedSkill, is(newSkill));
    }
}