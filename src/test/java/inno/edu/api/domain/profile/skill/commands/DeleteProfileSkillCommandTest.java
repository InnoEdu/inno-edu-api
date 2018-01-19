package inno.edu.api.domain.profile.skill.commands;

import inno.edu.api.domain.skill.assertions.SkillExistsAssertion;
import inno.edu.api.domain.skill.commands.DeleteSkillCommand;
import inno.edu.api.domain.profile.skill.models.ProfileSkillPrimaryKey;
import inno.edu.api.domain.profile.skill.repositories.ProfileSkillRepository;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.SkillFactory.skill;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeleteProfileSkillCommandTest {
    @Mock
    private ProfileExistsAssertion profileExistsAssertion;

    @Mock
    private SkillExistsAssertion skillExistsAssertion;

    @Mock
    private ProfileSkillRepository profileSkillRepository;

    @Mock
    private DeleteSkillCommand deleteSkillCommand;

    @InjectMocks
    private DeleteProfileSkillCommand deleteProfileSkillCommand;

    @Test
    public void shouldDeleteSkill() {
        deleteProfileSkillCommand.run(feiProfile().getId(), skill().getId());

        verify(deleteSkillCommand).run(skill().getId());
    }

    @Test
    public void shouldDeleteProfileSkill() {
        deleteProfileSkillCommand.run(feiProfile().getId(), skill().getId());

        ArgumentCaptor<ProfileSkillPrimaryKey> primaryKeyArgumentCaptor = forClass(ProfileSkillPrimaryKey.class);
        verify(profileSkillRepository).delete(primaryKeyArgumentCaptor.capture());

        assertThat(primaryKeyArgumentCaptor.getValue().getProfileId(), is(feiProfile().getId()));
        assertThat(primaryKeyArgumentCaptor.getValue().getSkillId(), is(skill().getId()));
    }

    @Test
    public void shouldRunAllAssertions() {
        deleteProfileSkillCommand.run(feiProfile().getId(), skill().getId());

        verify(profileExistsAssertion).run(feiProfile().getId());
        verify(skillExistsAssertion).run(skill().getId());
    }
}