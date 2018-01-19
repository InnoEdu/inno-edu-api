package inno.edu.api.domain.profile.skill.commands;

import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.domain.profile.skill.models.ProfileSkill;
import inno.edu.api.domain.profile.skill.repositories.ProfileSkillRepository;
import inno.edu.api.domain.skill.assertions.SkillExistsAssertion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.ProfileFactory.feiProfileSkill;
import static inno.edu.api.support.SkillFactory.skill;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateProfileSkillCommandTest {
    @Mock
    private ProfileExistsAssertion profileExistsAssertion;

    @Mock
    private SkillExistsAssertion skillExistsAssertion;

    @Mock
    private ProfileSkillRepository profileSkillRepository;

    @InjectMocks
    private CreateProfileSkillCommand createProfileSkillCommand;

    @Test
    public void shouldCreateProfileSkill() {
        when(profileSkillRepository.save(any(ProfileSkill.class)))
                .thenAnswer((answer) -> answer.getArguments()[0]);

        ProfileSkill profileSkill = createProfileSkillCommand.run(feiProfile().getId(), skill().getId());

        assertThat(profileSkill, is(feiProfileSkill()));
    }

    @Test
    public void shouldRunAllAssertions() {
        createProfileSkillCommand.run(feiProfile().getId(), skill().getId());

        verify(profileExistsAssertion).run(feiProfile().getId());
        verify(skillExistsAssertion).run(skill().getId());
    }
}